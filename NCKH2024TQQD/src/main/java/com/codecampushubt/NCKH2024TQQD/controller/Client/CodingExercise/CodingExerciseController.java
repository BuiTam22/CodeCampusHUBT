package com.codecampushubt.NCKH2024TQQD.controller.Client.CodingExercise;

import ch.qos.logback.core.model.processor.PhaseIndicator;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices.CodingExerciseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/practice/lesson")
public class CodingExerciseController {

    private final CodingExerciseService codingExerciseService;

    @Autowired
    public CodingExerciseController(CodingExerciseService codingExerciseService) {
        this.codingExerciseService = codingExerciseService;
    }

    @GetMapping("/{lesson-slug}")
    public String showCodingExerciseByLessonSlug(@PathVariable("lesson-slug") String theSlug, Model model, HttpServletRequest request){
        List<CodingExerciseDTO> exercises = codingExerciseService.getCodingExerciseDTOByLessonSlug(theSlug);
        exercises = exercises != null ? exercises : new ArrayList<>();
        model.addAttribute("exercises", exercises);
        model.addAttribute("activePage", request.getRequestURI());
        return  "ClientTemplates/coding-exercise/show";
    }

    @GetMapping("/problem/{slug}")
    public String showExerciseDetailBySlug(@PathVariable("slug") String theSlug, Model model, HttpServletRequest request){
        CodingExerciseDetailDTO exercise = codingExerciseService.getCodingExerciseDetailDTOByExerciseSlug(theSlug);
        model.addAttribute("exercise", exercise);
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/coding-exercise/problem";

    }
}