package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dao.ExerciseTestCaseRepository;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.ContestExerciseAttempt.AttemptInfoDTO;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseSubmissionRequest;
import com.codecampushubt.NCKH2024TQQD.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.codecampushubt.NCKH2024TQQD.entity.*;
import com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices.CodingExerciseService;
import com.codecampushubt.NCKH2024TQQD.service.CodingSubmissionServices.CodingSubmissionService;
import com.codecampushubt.NCKH2024TQQD.service.ContestExerciseAttemptServices.ContestExerciseAttemptService;
import com.codecampushubt.NCKH2024TQQD.service.EssayExerciseServices.EssayExerciseService;
import com.codecampushubt.NCKH2024TQQD.service.EssaySubmissionServices.EssaySubmissionService;
import com.codecampushubt.NCKH2024TQQD.service.JudgeServices.JudgeServiceClient;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import com.codecampushubt.NCKH2024TQQD.service.UserServices.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/judge")
public class RestJudge {
    private static final Logger log = LoggerFactory.getLogger(RestJudge.class);

    @Value("${GEMINI_API_KEY}")
    private String GEMINI_API_KEY;

    private final JudgeServiceClient judgeServiceClient;
    private final ExerciseTestCaseRepository exerciseTestCaseRepository;
    private final UserService userService;
    private final CodingExerciseService codingExerciseService;
    private final CodingSubmissionService codingSubmissionService;
    private final WebClient webClient;
    private final EssayExerciseService essayExerciseService;
    private final EssaySubmissionService essaySubmissionService;
    private final ContestExerciseAttemptService contestExerciseAttemptService;
    private final LessonService lessonService;


    @Autowired
    public RestJudge(JudgeServiceClient judgeServiceClient,
                     ExerciseTestCaseRepository exerciseTestCaseRepository,
                     UserService userService,
                     CodingExerciseService codingExerciseService,
                     CodingSubmissionService codingSubmissionService,
                     WebClient webClient,
                     EssayExerciseService essayExerciseService,
                     EssaySubmissionService essaySubmissionService,
                     ContestExerciseAttemptService contestExerciseAttemptService,
                     LessonService lessonService) {
        this.judgeServiceClient = judgeServiceClient;
        this.exerciseTestCaseRepository = exerciseTestCaseRepository;
        this.userService = userService;
        this.codingExerciseService = codingExerciseService;
        this.codingSubmissionService = codingSubmissionService;
        this.webClient = webClient;
        this.essayExerciseService = essayExerciseService;
        this.essaySubmissionService = essaySubmissionService;
        this.contestExerciseAttemptService = contestExerciseAttemptService;
        this.lessonService = lessonService;
    }

    /**
     * POST /api/judge/run — Chạy thử code (đồng bộ).
     * Gọi Judge Service /judge/coding/run, trả kết quả ngay cho client.
     */
    @PostMapping("/run")
    public JudgeRunResponseDTO handleRunCode(@RequestBody JudgeRequestDTO request){
        Set<ExerciseTestCasesDTO> exerciseTestCases = exerciseTestCaseRepository
                .getExerciseTestCasesDTOByExerciseID(request.getExerciseID());

        return judgeServiceClient.runCode(request.getSourceCode(), request.getLanguage(), exerciseTestCases);
    }

    /**
     * POST /api/judge/submit — Submit code để chấm (ASYNC).
     *
     * Flow mới:
     * 1. Validate (contest check)
     * 2. Tạo CodingSubmission PENDING trong DB
     * 3. Gọi async Judge Service /judge/coding/submit
     * 4. Trả 202 Accepted {submissionId, taskId} cho client
     * 5. Judge Service chấm xong → callback về /api/internal/judge/callback → update DB
     * 6. Client polling GET /api/judge/result/{submissionId}
     */
    @PostMapping("/submit")
    public ResponseEntity<?> handleSubmitCode(@RequestBody JudgeRequestDTO request){

        // 1. Kiểm tra contest - chặn submit lần 2
        if (codingExerciseService.isExerciseInContestLesson(request.getExerciseID())) {
            AttemptInfoDTO existingAttempt = contestExerciseAttemptService.getAttemptInfoDTOByuserIDAndExerciseID(
                    UserContext.getUserID(), request.getExerciseID(), "coding");
            if (existingAttempt != null && existingAttempt.getAttemptNumber() != null && existingAttempt.getAttemptNumber() > 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "already_submitted", "message", "Bạn đã nộp bài tập này rồi. Mỗi bài tập trong cuộc thi chỉ được nộp 1 lần."));
            }
        }

        // 2. Tạo submission PENDING trong DB (giải phóng Tomcat thread nhanh)
        Long userId = UserContext.getUserID();
        String userName = UserContext.getUsername();
        User userEntity = userService.getUserEntityByID(userId);
        CodingExercise codingExercise = codingExerciseService.getExerciseEntityByID(request.getExerciseID());

        CodingSubmission codingSubmission = new CodingSubmission();
        codingSubmission.setCode(request.getSourceCode());
        codingSubmission.setLanguage(request.getLanguage());
        codingSubmission.setStatus("pending");  // ← PENDING, chờ Judge Service callback
        codingSubmission.setTestCasesPassed(0);
        codingSubmission.setTotalTestCases(0);
        codingSubmission.setScore(0);
        codingSubmission.setExercise(codingExercise);
        codingSubmission.setUser(userEntity);
        codingSubmission.setExecutionTime(0);
        codingSubmission.setMemoryUsed(0);
        codingSubmission.setSubmittedAt(LocalDateTime.now());
        CodingSubmission savedSubmission = codingSubmissionService.save(codingSubmission);

        // 3. Gọi async Judge Service
        String taskId = UUID.randomUUID().toString();
        Set<ExerciseTestCasesDTO> exerciseTestCases = exerciseTestCaseRepository
                .getExerciseTestCasesDTOByExerciseID(request.getExerciseID());

        Map<String, Object> judgeResponse = judgeServiceClient.submitCode(
                taskId, savedSubmission.getSubmissionID(),
                request.getSourceCode(), request.getLanguage(),
                request.getExerciseID(), userId, userName, exerciseTestCases);

        // 4. Trả 202 Accepted cho client (~50ms total)
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("submissionId", savedSubmission.getSubmissionID());
        response.put("taskId", taskId);
        response.put("status", "pending");

        if (judgeResponse != null) {
            response.put("queuePosition", judgeResponse.get("queuePosition"));
        }

        log.info("Submit accepted: submissionId={} taskId={} exerciseId={} user={}",
                savedSubmission.getSubmissionID(), taskId, request.getExerciseID(), userName);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    /**
     * GET /api/judge/result/{submissionId} — Polling kết quả.
     * Client gọi endpoint này mỗi 1-2s để kiểm tra kết quả.
     */
    @GetMapping("/result/{submissionId}")
    public ResponseEntity<?> getSubmissionResult(@PathVariable Long submissionId) {
        CodingSubmission submission = codingSubmissionService.findById(submissionId);
        if (submission == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("submissionId", submission.getSubmissionID());
        result.put("status", submission.getStatus());
        result.put("score", submission.getScore());
        result.put("testCasesPassed", submission.getTestCasesPassed());
        result.put("totalTestCases", submission.getTotalTestCases());
        result.put("executionTime", submission.getExecutionTime());
        result.put("memoryUsed", submission.getMemoryUsed());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/essay/submit")
    public ResponseEntity<?> submitEssayExercise(@RequestBody EssayExerciseSubmissionRequest request) {
        // Chặn submit lần 2 TRƯỚC KHI gọi Gemini API
        AttemptInfoDTO existingAttempt = contestExerciseAttemptService.getAttemptInfoDTOByuserIDAndExerciseID(
                UserContext.getUserID(), request.getExerciseID(), "essay");
        if (existingAttempt != null && existingAttempt.getAttemptNumber() != null && existingAttempt.getAttemptNumber() > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "already_submitted", "message", "Bạn đã nộp bài tập này rồi. Mỗi bài tập trong cuộc thi chỉ được nộp 1 lần."));
        }

        AttemptInfoDTO attempInfo = new AttemptInfoDTO();
        attempInfo.setAttemptNumber(0);
        attempInfo.setExerciseType("essay");
        attempInfo.setLessonID(essayExerciseService.getLessonIDByExerciseID(request.getExerciseID()));

        ContestExerciseAttempt exerciseAttempt = new ContestExerciseAttempt();
        exerciseAttempt.setExerciseID(request.getExerciseID());
        CourseLesson lesson = lessonService.findById(attempInfo.getlessonID())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        exerciseAttempt.setLesson(lesson);

        User user = new User();
        user.setUserID(UserContext.getUserID());
        exerciseAttempt.setUser(user);
        exerciseAttempt.setSubmittedAt(LocalDateTime.now());
        exerciseAttempt.setExerciseType(attempInfo.getExerciseType());
        Integer currentAttempt = attempInfo.getAttemptNumber() == null ? 0 : attempInfo.getAttemptNumber();
        exerciseAttempt.setAttemptNumber(currentAttempt + 1);



        String expectedAnswer = essayExerciseService.getExpectedAnswerOfEssayExerciseByExerciseID(request.getExerciseID());

        String prompt = "So sánh bài làm sinh viên với đáp án dưới đây. Hãy đưa ra nhận xét chi tiết và chấm điểm (thang điểm 10). Trả về kết quả dưới dạng JSON: {\"feedback\": \"...\", \"score\": số thực từ 0 đến 10}\n\n"
                + "Đáp án:\n" + expectedAnswer + "\n\n"
                + "Bài làm của sinh viên:\n" + request.getContent();

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

// Thêm hậu tố "-preview" theo đúng danh sách API trả về
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite-preview:generateContent?key=" + GEMINI_API_KEY;        try {
            Mono<Map> responseMono = webClient.post()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class);

            Map response = responseMono.block();
            List candidates = (List) response.get("candidates");
            if (candidates == null || candidates.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không có kết quả từ Gemini.");
            }

            Map contentMap = (Map) ((Map) candidates.get(0)).get("content");
            List parts = (List) contentMap.get("parts");
            String rawText = (String) ((Map) parts.get(0)).get("text");

            // Trích JSON bằng regex hoặc chuyển sang parser an toàn hơn
            Pattern jsonPattern = Pattern.compile("\\{.*?\\}", Pattern.DOTALL);
            Matcher matcher = jsonPattern.matcher(rawText);

            EssaySubmission submission = new EssaySubmission();
            EssayExercise exercise = new EssayExercise();
            exercise.setExerciseID(request.getExerciseID());
            User userSubmit = new User();
            userSubmit.setUserID(UserContext.getUserID());
            submission.setExercise(exercise);
            submission.setUser(userSubmit);
            submission.setAnswerText(request.getContent());
            submission.setSubmittedAt(LocalDateTime.now());


            if (matcher.find()) {
                String json = matcher.group();
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                Map<String, Object> result = mapper.readValue(json, Map.class);
                submission.setFeedback((String) result.get("feedback"));
                submission.setScore(Double.valueOf(result.get("score").toString()));
                exerciseAttempt.setScore(Double.valueOf(result.get("score").toString()));

                // Lưu vào DB
                essaySubmissionService.save(submission);
                contestExerciseAttemptService.save(exerciseAttempt);

                return ResponseEntity.ok(result);
            } else {
                submission.setFeedback(rawText);
                submission.setScore(0.0);
                essaySubmissionService.save(submission);
                contestExerciseAttemptService.save(exerciseAttempt);
                return ResponseEntity.ok(Map.of("feedback", rawText, "score", 0));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã có lỗi xảy ra khi gọi Gemini API.");
        }
    }

}
