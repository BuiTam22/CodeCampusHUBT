package com.codecampushubt.NCKH2024TQQD.service.EssayExerciseServices;


import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseListShowDTO;

import java.util.List;

public interface EssayExerciseService {
    List<EssayExerciseListShowDTO> getEssayExerciseListShowDTOByLessonSlug(String theSlug);
}
