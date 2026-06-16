package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.entity.*;
import com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices.CodingExerciseService;
import com.codecampushubt.NCKH2024TQQD.service.CodingSubmissionServices.CodingSubmissionService;
import com.codecampushubt.NCKH2024TQQD.service.ContestExerciseAttemptServices.ContestExerciseAttemptService;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import com.codecampushubt.NCKH2024TQQD.service.UserServices.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Controller nhận callback kết quả chấm từ Judge Service.
 * Judge Service gọi endpoint này khi chấm xong 1 task.
 *
 * Endpoint: POST /api/internal/judge/callback
 * Auth: API Key (Bearer token)
 */
@RestController
@RequestMapping("/api/internal/judge")
public class JudgeCallbackController {

    private static final Logger log = LoggerFactory.getLogger(JudgeCallbackController.class);

    @Value("${judge.service.api-key:jdg-codecampus-hubt-2024-internal-secret-key}")
    private String expectedApiKey;

    private final CodingSubmissionService codingSubmissionService;
    private final CodingExerciseService codingExerciseService;
    private final ContestExerciseAttemptService contestExerciseAttemptService;
    private final LessonService lessonService;
    private final UserService userService;

    @Autowired
    public JudgeCallbackController(CodingSubmissionService codingSubmissionService,
                                   CodingExerciseService codingExerciseService,
                                   ContestExerciseAttemptService contestExerciseAttemptService,
                                   LessonService lessonService,
                                   UserService userService) {
        this.codingSubmissionService = codingSubmissionService;
        this.codingExerciseService = codingExerciseService;
        this.contestExerciseAttemptService = contestExerciseAttemptService;
        this.lessonService = lessonService;
        this.userService = userService;
    }

    /**
     * POST /api/internal/judge/callback
     *
     * Nhận kết quả chấm từ Judge Service và cập nhật DB.
     *
     * Request body (CodingJudgeResult):
     * {
     *   "taskId": "uuid",
     *   "submissionId": 123,
     *   "type": "coding",
     *   "status": "accepted" | "wrong_answer" | "compilation_error" | "runtime_error",
     *   "score": 80,
     *   "testCasesPassed": 8,
     *   "totalTestCases": 10,
     *   "output": "...",
     *   "feedback": "...",
     *   "executionTimeMs": 1200,
     *   "memoryUsedKb": 15360
     * }
     */
    @PostMapping("/callback")
    public ResponseEntity<?> handleJudgeCallback(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                                  @RequestBody Map<String, Object> result) {
        // 1. Validate API Key
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Missing Authorization header"));
        }
        String providedKey = authHeader.substring(7);
        if (!expectedApiKey.equals(providedKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Invalid API key"));
        }

        // 2. Parse result
        String taskId = (String) result.get("taskId");
        String type = (String) result.getOrDefault("type", "coding");
        String status = (String) result.get("status");
        Number submissionIdNum = (Number) result.get("submissionId");
        Number scoreNum = (Number) result.get("score");
        Number testCasesPassedNum = (Number) result.get("testCasesPassed");
        Number totalTestCasesNum = (Number) result.get("totalTestCases");
        Number executionTimeMsNum = (Number) result.get("executionTimeMs");
        Number memoryUsedKbNum = (Number) result.get("memoryUsedKb");

        log.info("Received callback | TaskId: {} | Type: {} | Status: {} | Score: {}",
                taskId, type, status, scoreNum);

        if ("coding".equals(type)) {
            return handleCodingCallback(submissionIdNum, status, scoreNum,
                    testCasesPassedNum, totalTestCasesNum, executionTimeMsNum, memoryUsedKbNum);
        }

        // Essay callback sẽ được implement ở Phase sau
        return ResponseEntity.ok(Map.of("received", true));
    }

    /**
     * Xử lý callback cho coding submission.
     * Tìm submission PENDING trong DB và update với kết quả từ Judge Service.
     */
    private ResponseEntity<?> handleCodingCallback(Number submissionIdNum, String status,
                                                    Number scoreNum, Number testCasesPassedNum,
                                                    Number totalTestCasesNum, Number executionTimeMsNum,
                                                    Number memoryUsedKbNum) {
        if (submissionIdNum == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing submissionId"));
        }

        Long submissionId = submissionIdNum.longValue();

        try {
            // Tìm submission trong DB
            CodingSubmission submission = codingSubmissionService.findById(submissionId);
            if (submission == null) {
                log.warn("Submission {} not found for callback", submissionId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Submission not found"));
            }

            // Cập nhật kết quả
            submission.setStatus(status);
            submission.setScore(scoreNum != null ? scoreNum.intValue() : 0);
            submission.setTestCasesPassed(testCasesPassedNum != null ? testCasesPassedNum.intValue() : 0);
            submission.setTotalTestCases(totalTestCasesNum != null ? totalTestCasesNum.intValue() : 0);
            submission.setExecutionTime(executionTimeMsNum != null ? executionTimeMsNum.intValue() : 0);
            submission.setMemoryUsed(memoryUsedKbNum != null ? memoryUsedKbNum.intValue() : 0);

            codingSubmissionService.save(submission);

            // Cập nhật ContestExerciseAttempt nếu là contest exercise
            Long exerciseId = submission.getExercise().getExerciseID();
            Long userId = submission.getUser().getUserId();

            if (codingExerciseService.isExerciseInContestLesson(exerciseId)) {
                Long lessonId = codingExerciseService.getLessonIDByExerciseID(exerciseId);

                ContestExerciseAttempt exerciseAttempt = new ContestExerciseAttempt();
                exerciseAttempt.setExerciseID(exerciseId);
                CourseLesson lesson = lessonService.findById(lessonId)
                        .orElseThrow(() -> new RuntimeException("Lesson not found"));
                exerciseAttempt.setLesson(lesson);

                User user = new User();
                user.setUserID(userId);
                exerciseAttempt.setUser(user);
                exerciseAttempt.setSubmittedAt(LocalDateTime.now());
                exerciseAttempt.setExerciseType("coding");
                exerciseAttempt.setAttemptNumber(1);
                exerciseAttempt.setScore(scoreNum != null ? scoreNum.doubleValue() : 0.0);

                contestExerciseAttemptService.save(exerciseAttempt);
            }

            log.info("Callback processed: submissionId={} status={} score={}",
                    submissionId, status, scoreNum);

            return ResponseEntity.ok(Map.of("received", true, "submissionId", submissionId));

        } catch (Exception e) {
            log.error("Error processing callback for submission {}: {}", submissionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
