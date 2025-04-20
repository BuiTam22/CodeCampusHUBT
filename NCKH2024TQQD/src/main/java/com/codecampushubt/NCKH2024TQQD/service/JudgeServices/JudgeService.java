package com.codecampushubt.NCKH2024TQQD.service.JudgeServices;

import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;

import java.util.Set;

public interface JudgeService {
    public JudgeRunResponseDTO runUserCode(JudgeRequestDTO request, Set<ExerciseTestCasesDTO> exerciseTestCases);
}
