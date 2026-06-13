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
    @Transactional
    public void updateScoreBySubmissionId(Long submissionId, Double score) {
        essaySubmissionRepository.updateScoreBySubmissionId(submissionId, score);
    }
}
