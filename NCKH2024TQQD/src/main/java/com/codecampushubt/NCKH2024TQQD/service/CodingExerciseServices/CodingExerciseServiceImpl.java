package com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices;

import com.codecampushubt.NCKH2024TQQD.dao.CodingExerciseRepository;
import com.codecampushubt.NCKH2024TQQD.dao.CodingSubmissionRepository;
import com.codecampushubt.NCKH2024TQQD.dao.ContestExerciseAttemptRepository;
import com.codecampushubt.NCKH2024TQQD.dao.ExerciseTestCaseRepository;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.codecampushubt.NCKH2024TQQD.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CodingExercise;
import com.codecampushubt.NCKH2024TQQD.entity.ExerciseTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CodingExerciseServiceImpl implements CodingExerciseService {
    private final CodingExerciseRepository codingExerciseRepository;
    private final ExerciseTestCaseRepository exerciseTestCaseRepository;
    private final CodingSubmissionRepository codingSubmissionRepository;
    private final ContestExerciseAttemptRepository contestExerciseAttemptRepository;

    @Autowired
    public CodingExerciseServiceImpl(CodingExerciseRepository codingExerciseRepository,
                                     ExerciseTestCaseRepository exerciseTestCaseRepository,
                                     CodingSubmissionRepository codingSubmissionRepository,
                                     ContestExerciseAttemptRepository contestExerciseAttemptRepository) {
        this.codingExerciseRepository = codingExerciseRepository;
        this.exerciseTestCaseRepository = exerciseTestCaseRepository;
        this.codingSubmissionRepository = codingSubmissionRepository;
        this.contestExerciseAttemptRepository = contestExerciseAttemptRepository;
    }

    @Override
    public List<CodingExerciseDTO> getCodingExerciseDTOByLessonSlug(String theSlug) {
        return codingExerciseRepository.getCodingExerciseDTOByLessonSlug(theSlug);
    }

    @Override
        public CodingExerciseDetailDTO getCodingExerciseDetailDTOByExerciseSlug(String theSlug) {
            CodingExerciseDetailDTO codingExerciseDetailDTO = codingExerciseRepository.getCodingExerciseDetailDTOByExerciseSlug(theSlug);
            Set<ExerciseTestCasesDTO> exerciseTestCases = exerciseTestCaseRepository.getExerciseTestCasesDTOByExerciseID(codingExerciseDetailDTO.getExerciseID());
            codingExerciseDetailDTO.setExerciseTestCases(exerciseTestCases);
            return codingExerciseDetailDTO;
    }

    @Override
    public CodingExercise getExerciseEntityByID(Long exerciseID) {
        return codingExerciseRepository.getExerciseEntityByID(exerciseID);
    }

    @Override
    public boolean isExerciseInContestLesson(Long exerciseID) {
        return codingExerciseRepository.isExerciseInContestLesson(exerciseID);
    }

    @Override
    public Long getLessonIDByExerciseID(Long exerciseID) {
        return codingExerciseRepository.getLessonIDByExerciseID(exerciseID);
    }

    @Override
    public CodingExercise save(CodingExercise codingExercise) {
        return codingExerciseRepository.save(codingExercise);
    }

    @Override
    @Transactional
    public void deleteById(Long exerciseID) {
        // Xóa các bản ghi liên quan trước để tránh lỗi FK constraint
        codingSubmissionRepository.deleteByExerciseID(exerciseID);
        contestExerciseAttemptRepository.deleteByExerciseID(exerciseID);
        // Cascade sẽ tự xóa ExerciseTestCases nhờ CascadeType.ALL trên CodingExercise
        codingExerciseRepository.deleteById(exerciseID);
    }

}

