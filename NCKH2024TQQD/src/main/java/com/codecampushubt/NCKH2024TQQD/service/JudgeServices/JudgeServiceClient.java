package com.codecampushubt.NCKH2024TQQD.service.JudgeServices;

import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;

/**
 * HTTP Client gọi Judge Service microservice.
 * Thay thế việc gọi trực tiếp JudgeServiceImpl (Docker execution trên monolith).
 *
 * Flow:
 * - /run  → gọi đồng bộ Judge Service, trả kết quả ngay
 * - /submit → gọi async Judge Service, trả taskId cho client polling
 */
@Service
public class JudgeServiceClient {

    private static final Logger log = LoggerFactory.getLogger(JudgeServiceClient.class);

    private final WebClient webClient;

    @Value("${judge.service.url:http://localhost:8081}")
    private String judgeServiceUrl;

    @Value("${judge.service.api-key:jdg-codecampus-hubt-2024-internal-secret-key}")
    private String apiKey;

    @Value("${judge.service.callback-url:http://localhost:3000/api/internal/judge/callback}")
    private String callbackUrl;

    public JudgeServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Gọi Judge Service /judge/coding/run (đồng bộ).
     * Timeout 15s. Trả kết quả run trực tiếp cho client.
     */
    public JudgeRunResponseDTO runCode(String sourceCode, String language, Set<ExerciseTestCasesDTO> testCases) {
        try {
            // Build request body theo contract của Judge Service
            List<Map<String, Object>> testCaseList = new ArrayList<>();
            for (ExerciseTestCasesDTO tc : testCases) {
                Map<String, Object> tcMap = new LinkedHashMap<>();
                tcMap.put("input", tc.getInput());
                tcMap.put("expectedOutput", tc.getExpectedOutput());
                tcMap.put("isPublic", tc.getPublic());
                tcMap.put("score", tc.getScore());
                testCaseList.add(tcMap);
            }

            Map<String, Object> requestBody = new LinkedHashMap<>();
            requestBody.put("sourceCode", sourceCode);
            requestBody.put("language", language);
            requestBody.put("testCases", testCaseList);

            Map response = webClient.post()
                    .uri(judgeServiceUrl + "/judge/coding/run")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(15))
                    .block();

            if (response != null) {
                return new JudgeRunResponseDTO(
                        (String) response.getOrDefault("output", ""),
                        (String) response.getOrDefault("status", "ERROR"),
                        (String) response.getOrDefault("message", "")
                );
            }
            return new JudgeRunResponseDTO("", "ERROR", "No response from Judge Service");

        } catch (WebClientResponseException e) {
            log.error("Judge Service run error (HTTP {}): {}", e.getStatusCode(), e.getResponseBodyAsString());
            return new JudgeRunResponseDTO("", "ERROR", "Judge Service error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Judge Service run error: {}", e.getMessage());
            return new JudgeRunResponseDTO("", "ERROR", "Judge Service unavailable: " + e.getMessage());
        }
    }

    /**
     * Gọi Judge Service /judge/coding/submit (async).
     * Trả về Map {taskId, status, queuePosition} ngay lập tức.
     *
     * @return response map từ Judge Service hoặc null nếu lỗi
     */
    @Async
    public Map<String, Object> submitCode(String taskId, Long submissionId, String sourceCode,
                                           String language, Long exerciseId, Long userId,
                                           String username, Set<ExerciseTestCasesDTO> testCases) {
        try {
            List<Map<String, Object>> testCaseList = new ArrayList<>();
            for (ExerciseTestCasesDTO tc : testCases) {
                Map<String, Object> tcMap = new LinkedHashMap<>();
                tcMap.put("input", tc.getInput());
                tcMap.put("expectedOutput", tc.getExpectedOutput());
                tcMap.put("isPublic", tc.getPublic());
                tcMap.put("score", tc.getScore());
                testCaseList.add(tcMap);
            }

            Map<String, Object> requestBody = new LinkedHashMap<>();
            requestBody.put("taskId", taskId);
            requestBody.put("submissionId", submissionId);
            requestBody.put("sourceCode", sourceCode);
            requestBody.put("language", language);
            requestBody.put("exerciseId", exerciseId);
            requestBody.put("userId", userId);
            requestBody.put("username", username);
            requestBody.put("testCases", testCaseList);
            requestBody.put("callbackUrl", callbackUrl);

            Map response = webClient.post()
                    .uri(judgeServiceUrl + "/judge/coding/submit")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            log.info("Submit to Judge Service: taskId={} submissionId={}", taskId, submissionId);
            return response;

        } catch (Exception e) {
            log.error("Judge Service submit error: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Check status task tại Judge Service
     */
    public Map<String, Object> getTaskStatus(String taskId) {
        try {
            return webClient.get()
                    .uri(judgeServiceUrl + "/judge/status/" + taskId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
        } catch (Exception e) {
            log.error("Judge Service status check error: {}", e.getMessage());
            return null;
        }
    }
}
