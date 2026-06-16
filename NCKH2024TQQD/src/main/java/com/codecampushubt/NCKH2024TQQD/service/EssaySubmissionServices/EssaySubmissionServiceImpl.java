package com.codecampushubt.NCKH2024TQQD.service.EssaySubmissionServices;

import com.codecampushubt.NCKH2024TQQD.dao.EssaySubmissionRepository;
import com.codecampushubt.NCKH2024TQQD.entity.EssaySubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.EssayScoreDetailDTO;

@Service
public class EssaySubmissionServiceImpl implements EssaySubmissionService{
    private final EssaySubmissionRepository essaySubmissionRepository;

    @Autowired
    public EssaySubmissionServiceImpl(EssaySubmissionRepository essaySubmissionRepository) {
        this.essaySubmissionRepository = essaySubmissionRepository;
    }

    @Override
    public EssaySubmission save(EssaySubmission submission) {
        return essaySubmissionRepository.save(submission);
    }

    @Override
    public List<EssayScoreDetailDTO> getEssayScoreDetailsByLessonId(Long lessonId) {
        return essaySubmissionRepository.getEssayScoreDetailsByLessonId(lessonId);
    }

    @Override
    public List<EssayScoreDetailDTO> getLatestEssayScoreDetailPerUserByLessonId(Long lessonId) {
        return essaySubmissionRepository.getLatestEssayScoreDetailPerUserByLessonId(lessonId);
    }

    @Override
    @Transactional
    public void updateTeacherReviewBySubmissionId(Long submissionId, Double finalScore, String teacherFeedback) {
        EssaySubmission submission = essaySubmissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found: " + submissionId));
        submission.setFinalScore(finalScore);
        submission.setTeacherFeedBack(teacherFeedback);
        essaySubmissionRepository.save(submission);
    }
}
