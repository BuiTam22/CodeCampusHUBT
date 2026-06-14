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
            @RequestBody Map<String, Object> body) {

        Double finalScore = null;
        if (body.get("finalScore") != null) {
            finalScore = Double.valueOf(body.get("finalScore").toString());
        }

        String teacherFeedback = (String) body.get("teacherFeedback");

        if (finalScore == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Điểm đánh giá (finalScore) là bắt buộc"));
        }

        essaySubmissionService.updateTeacherReviewBySubmissionId(submissionId, finalScore, teacherFeedback);
        return ResponseEntity.ok(Map.of("success", true, "finalScore", finalScore, "teacherFeedback", teacherFeedback));
    }

}
