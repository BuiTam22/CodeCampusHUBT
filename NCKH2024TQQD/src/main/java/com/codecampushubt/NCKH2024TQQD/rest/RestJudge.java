package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.dao.ExerciseTestCaseRepository;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingSubmission.CodingSubmissionResponseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CodingExercise;
import com.codecampushubt.NCKH2024TQQD.entity.CodingSubmission;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices.CodingExerciseService;
import com.codecampushubt.NCKH2024TQQD.service.CodingSubmissionServices.CodingSubmissionService;
import com.codecampushubt.NCKH2024TQQD.service.JudgeServices.JudgeService;
import com.codecampushubt.NCKH2024TQQD.service.UserServices.UserService;
import com.ibm.icu.text.UFieldPosition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api/judge")
public class RestJudge {
    private final JudgeService judgeService;
    private final ExerciseTestCaseRepository exerciseTestCaseRepository;
    private final UserService userService;
    private final CodingExerciseService codingExerciseService;
    private final CodingSubmissionService codingSubmissionService;


    @Autowired
    public RestJudge(JudgeService judgeService, ExerciseTestCaseRepository exerciseTestCaseRepository, UserService userService, CodingExerciseService codingExerciseService, CodingSubmissionService codingSubmissionService) {
        this.judgeService = judgeService;
        this.exerciseTestCaseRepository = exerciseTestCaseRepository;
        this.userService = userService;
        this.codingExerciseService = codingExerciseService;
        this.codingSubmissionService = codingSubmissionService;
    }

    @PostMapping("/run")
    public JudgeRunResponseDTO handleRunCode(@RequestBody JudgeRequestDTO request){
        Set<ExerciseTestCasesDTO> exerciseTestCases = exerciseTestCaseRepository.getExerciseTestCasesDTOByExerciseID(request.getExerciseID());
        return  judgeService.runUserCode(request, exerciseTestCases);
    }

    @PostMapping("submit")
    public CodingSubmissionResponseDTO handleSubmitCode(@RequestBody JudgeRequestDTO request){
        Set<ExerciseTestCasesDTO> exerciseTestCases = exerciseTestCaseRepository.getExerciseTestCasesDTOByExerciseID(request.getExerciseID());
        // lấy ra submission để lưu vào DB và trả ra cho client
        CodingSubmissionResponseDTO submission = judgeService.submitUserCode(request, exerciseTestCases);
        submission.setExerciseID(request.getExerciseID());

        // Lưu Submission vào DB
        User user = userService.getUserEntityByID(submission.getUserID());
        CodingExercise codingExercise = codingExerciseService.getExerciseEntityByID(submission.getExerciseID());
        CodingSubmission codingSubmission = new CodingSubmission();

        codingSubmission.setCode(submission.getCode());
        codingSubmission.setLanguage(submission.getLanguage());
        codingSubmission.setStatus(submission.getStatus());
        codingSubmission.setTestCasesPassed(submission.getTestCasesPassed());
        codingSubmission.setTotalTestCases(submission.getTotalTestCases());
        codingSubmission.setScore(submission.getScore());
        codingSubmission.setExercise(codingExercise);
        codingSubmission.setUser(user);
        codingSubmission.setExecutionTime(1);
        codingSubmission.setMemoryUsed(10);
        codingSubmission.setSubmittedAt(LocalDateTime.now());
        CodingSubmission newSubmission =  codingSubmissionService.save(codingSubmission);

        return submission;
    }

}
