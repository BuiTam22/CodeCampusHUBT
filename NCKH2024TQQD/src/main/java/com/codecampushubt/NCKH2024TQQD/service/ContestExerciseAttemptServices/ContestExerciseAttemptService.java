package com.codecampushubt.NCKH2024TQQD.service.ContestExerciseAttemptServices;

import com.codecampushubt.NCKH2024TQQD.dto.ContestExerciseAttempt.AttemptInfoDTO;
import org.springframework.data.repository.query.Param;

public interface ContestExerciseAttemptService {
    AttemptInfoDTO getAttemptInfoDTOByuserIDAndExerciseID(Long userID, Long exerciseID);
}
