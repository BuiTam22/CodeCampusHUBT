package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.Constant.Constants;
import com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.LessonSubmissionDTO;
import com.codecampushubt.NCKH2024TQQD.service.LessonSubmissionServices.LessonSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lesson-submission")
public class RestLessonSubmission {
    private final LessonSubmissionService lessonSubmissionService;

    @Autowired
    public RestLessonSubmission(LessonSubmissionService lessonSubmissionService) {
        this.lessonSubmissionService = lessonSubmissionService;
    }

    @GetMapping("")
    public List<LessonSubmissionDTO> getLessonSubmissionsByLessonIDAndUserNameAndAttemptNumber(Long lessonID){
        return lessonSubmissionService.getLessonSubmissionsByLessonId(Constants.ID_MODULE_COMMON);
    }

}
