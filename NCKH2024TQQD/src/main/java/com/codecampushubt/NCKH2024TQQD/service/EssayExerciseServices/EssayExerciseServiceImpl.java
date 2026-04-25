package com.codecampushubt.NCKH2024TQQD.service.EssayExerciseServices;

import com.codecampushubt.NCKH2024TQQD.dao.ContestExerciseAttemptRepository;
import com.codecampushubt.NCKH2024TQQD.dao.EssayExerciseRepository;
import com.codecampushubt.NCKH2024TQQD.dao.EssaySubmissionRepository;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseDetailShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseListShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.EssayExercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EssayExerciseServiceImpl implements EssayExerciseService{
    private final EssayExerciseRepository essayExerciseRepository;
    private final EssaySubmissionRepository essaySubmissionRepository;
    private final ContestExerciseAttemptRepository contestExerciseAttemptRepository;

    @Autowired
    public EssayExerciseServiceImpl(EssayExerciseRepository essayExerciseRepository,
                                    EssaySubmissionRepository essaySubmissionRepository,
                                    ContestExerciseAttemptRepository contestExerciseAttemptRepository) {
        this.essayExerciseRepository = essayExerciseRepository;
        this.essaySubmissionRepository = essaySubmissionRepository;
        this.contestExerciseAttemptRepository = contestExerciseAttemptRepository;
    }

    @Override
    public List<EssayExerciseListShowDTO> getEssayExerciseListShowDTOByLessonSlug(String theSlug) {
        return essayExerciseRepository.getEssayExerciseListShowDTOByLessonSlug(theSlug);
    }

    @Override
    public EssayExerciseDetailShowDTO getEssayExerciseDetailShowDTOBySlug(String theSlug) {
        return essayExerciseRepository.getEssayExerciseDetailShowDTOBySlug(theSlug);
    }

    @Override
    public String getExpectedAnswerOfEssayExerciseByExerciseID(Long theID) {
        return essayExerciseRepository.getExpectedAnswerOfEssayExerciseByExerciseID(theID);
    }

    @Override
    public Long getLessonIDByExerciseID(Long exerciseID) {
        return essayExerciseRepository.getLessonIDByExerciseID(exerciseID);
    }

    @Override
    public Optional<EssayExercise> findById(Long exerciseID) {
        return essayExerciseRepository.findById(exerciseID);
    }

    @Override
    public EssayExercise save(EssayExercise essayExercise) {
        return essayExerciseRepository.save(essayExercise);
    }

    @Override
    @Transactional
    public void deleteById(Long exerciseID) {
        // Xóa các bản ghi liên quan trước để tránh lỗi FK constraint
        essaySubmissionRepository.deleteByExerciseID(exerciseID);
        contestExerciseAttemptRepository.deleteByExerciseID(exerciseID);
        essayExerciseRepository.deleteById(exerciseID);
    }
}
