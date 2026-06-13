package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.Constant.Constants;
import com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.LessonSubmissionDTO;
import com.codecampushubt.NCKH2024TQQD.service.LessonSubmissionServices.LessonSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.codecampushubt.NCKH2024TQQD.service.EssaySubmissionServices.EssaySubmissionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lesson-submission")
public class RestLessonSubmission {
    private final LessonSubmissionService lessonSubmissionService;
    private final EssaySubmissionService essaySubmissionService;

    @Autowired
    public RestLessonSubmission(LessonSubmissionService lessonSubmissionService, EssaySubmissionService essaySubmissionService) {
        this.lessonSubmissionService = lessonSubmissionService;
        this.essaySubmissionService = essaySubmissionService;
    }

    @GetMapping("")
    public List<LessonSubmissionDTO> getLessonSubmissionsByLessonIDAndUserNameAndAttemptNumber(Long lessonID){
        return lessonSubmissionService.getLessonSubmissionsByLessonId(Constants.ID_MODULE_COMMON);
    }

    @PatchMapping("/essay/{submissionId}/score")
    public ResponseEntity<?> updateEssayScore(
            @PathVariable Long submissionId,
            @RequestBody Map<String, Double> body) {
        Double newScore = body.get("score");
        if (newScore == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Score is required"));
        }
        essaySubmissionService.updateScoreBySubmissionId(submissionId, newScore);
        return ResponseEntity.ok(Map.of("success", true, "newScore", newScore));
    }

}
