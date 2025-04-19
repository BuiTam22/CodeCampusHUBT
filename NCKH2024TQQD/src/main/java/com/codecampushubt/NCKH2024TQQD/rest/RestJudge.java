package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.codecampushubt.NCKH2024TQQD.service.JudgeServices.JudgeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/judge")
public class RestJudge {
    private final JudgeService judgeService;

    @Autowired
    public RestJudge(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    @PostMapping("/run")
    public JudgeRunResponseDTO handleRunCode(@RequestBody JudgeRequestDTO request){
//        String sourcseCode = request.getSourceCode();
//        String langguage = request.getLanguage();
//        Long exerciseID = request.getExerciseID();
//
//        String output = "";
//        String status = "";
//        String message = "";
//        JudgeRunResponseDTO resuilt = new JudgeRunResponseDTO(output, status, message);
//        return resuilt;
        return  judgeService.runUserCode(request);

    }

}
