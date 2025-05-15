package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseDetailShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseListShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.EssayExerciseServices.EssayExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/essay-exercise")
public class RestEssayExercise {
    private final EssayExerciseService essayExerciseService;

    @Autowired
    public RestEssayExercise(EssayExerciseService essayExerciseService) {
        this.essayExerciseService = essayExerciseService;
    }

    @GetMapping("/{lessonSlug}")
    public List<EssayExerciseListShowDTO> getEssayExerciseListShowDTOByLessonSlug(@PathVariable("lessonSlug") String theSlug){
        return essayExerciseService.getEssayExerciseListShowDTOByLessonSlug(theSlug);
    }

    @GetMapping("/problem/{exerciseSlug}")
    public EssayExerciseDetailShowDTO getEssayExerciseDetailShowDTOBySlug(@PathVariable("exerciseSlug")String theSlug){
        return essayExerciseService.getEssayExerciseDetailShowDTOBySlug(theSlug);
    }
}
