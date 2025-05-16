package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseDetailShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseListShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseSubmissionRequest;
import com.codecampushubt.NCKH2024TQQD.service.EssayExerciseServices.EssayExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/essay-exercise")
public class RestEssayExercise {
    @Value("${GEMINI_API_KEY}")
    private String GEMINI_API_KEY;

    private final EssayExerciseService essayExerciseService;

    private final WebClient webClient;

    @Autowired
    public RestEssayExercise(EssayExerciseService essayExerciseService) {
        this.essayExerciseService = essayExerciseService;
        this.webClient = WebClient.create();
    }

    @GetMapping("/{lessonSlug}")
    public List<EssayExerciseListShowDTO> getEssayExerciseListShowDTOByLessonSlug(@PathVariable("lessonSlug") String theSlug) {
        return essayExerciseService.getEssayExerciseListShowDTOByLessonSlug(theSlug);
    }

    @GetMapping("/problem/{exerciseSlug}")
    public EssayExerciseDetailShowDTO getEssayExerciseDetailShowDTOBySlug(@PathVariable("exerciseSlug") String theSlug) {
        return essayExerciseService.getEssayExerciseDetailShowDTOBySlug(theSlug);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitEssayExercise(@RequestBody EssayExerciseSubmissionRequest request) {
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

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-pro:generateContent?key=" + GEMINI_API_KEY;

        try {
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

            if (matcher.find()) {
                String json = matcher.group();
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                Map<String, Object> result = mapper.readValue(json, Map.class);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.ok(Map.of("feedback", rawText, "score", 0));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã có lỗi xảy ra khi gọi Gemini API.");
        }
    }
}
