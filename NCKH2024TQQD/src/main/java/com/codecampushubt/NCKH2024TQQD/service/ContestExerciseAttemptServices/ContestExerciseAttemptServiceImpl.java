package com.codecampushubt.NCKH2024TQQD.service.ContestExerciseAttemptServices;

import com.codecampushubt.NCKH2024TQQD.dao.ContestExerciseAttemptRepository;
import com.codecampushubt.NCKH2024TQQD.dto.ContestExerciseAttempt.AttemptInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContestExerciseAttemptServiceImpl implements ContestExerciseAttemptService{
    private final ContestExerciseAttemptRepository contestExerciseAttemptRepository;

    @Autowired
    public ContestExerciseAttemptServiceImpl(ContestExerciseAttemptRepository contestExerciseAttemptRepository) {
        this.contestExerciseAttemptRepository = contestExerciseAttemptRepository;
    }

    @Override
    public AttemptInfoDTO getAttemptInfoDTOByuserIDAndExerciseID(Long userID, Long exerciseID) {
        return contestExerciseAttemptRepository.getAttemptInfoDTOByuserIDAndExerciseID(userID, exerciseID);
    }
}
