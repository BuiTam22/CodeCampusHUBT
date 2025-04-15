package com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices;

import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDTO;

import java.util.List;

public interface CodingExerciseService {
    List<CodingExerciseDTO> getCodingExerciseDTOByLessonSlug(String theSlug);
}
