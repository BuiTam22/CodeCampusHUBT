package com.codecampushubt.NCKH2024TQQD.service.EssaySubmissionServices;

import com.codecampushubt.NCKH2024TQQD.entity.EssaySubmission;

import java.util.List;
import com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.EssayScoreDetailDTO;

public interface EssaySubmissionService {
    EssaySubmission save(EssaySubmission submission);

    List<EssayScoreDetailDTO> getEssayScoreDetailsByLessonId(Long lessonId);

    /** Trả về 1 record duy nhất mỗi user (submission mới nhất) cho lesson. */
    List<EssayScoreDetailDTO> getLatestEssayScoreDetailPerUserByLessonId(Long lessonId);

    void updateTeacherReviewBySubmissionId(Long submissionId, Double finalScore, String teacherFeedback);
}
