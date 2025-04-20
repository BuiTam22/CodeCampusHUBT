package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.dao.ExerciseTestCaseRepository;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.codecampushubt.NCKH2024TQQD.service.JudgeServices.JudgeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/judge")
public class RestJudge {
    private final JudgeService judgeService;
    private final ExerciseTestCaseRepository exerciseTestCaseRepository;

    @Autowired
    public RestJudge(JudgeService judgeService, ExerciseTestCaseRepository exerciseTestCaseRepository) {
        this.judgeService = judgeService;
        this.exerciseTestCaseRepository = exerciseTestCaseRepository;
    }

    @PostMapping("/run")
    public JudgeRunResponseDTO handleRunCode(@RequestBody JudgeRequestDTO request){
        Set<ExerciseTestCasesDTO> exerciseTestCases = exerciseTestCaseRepository.getExerciseTestCasesDTOByExerciseID(request.getExerciseID());
        return  judgeService.runUserCode(request, exerciseTestCases);
    }

}
