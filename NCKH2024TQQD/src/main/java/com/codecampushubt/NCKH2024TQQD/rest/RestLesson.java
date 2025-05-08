package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.ContestManagementShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.ContestShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
public class RestLesson {
    private final LessonService lessonService;

    @Autowired
    public RestLesson(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/find-by-id/{id}")
    public List<LessonShowDTO> getLessonShowDTO(@PathVariable("id") Long theID){
        return lessonService.getLessonShowDTO(theID);
    }

    @GetMapping("/contest/{moduleID}")
    private List<ContestShowDTO> getLessonShowDTOByIsContest(@PathVariable("moduleID") Long moduleID){
        return lessonService.getContestShowDTOByIsContest(moduleID);
    }

    @GetMapping("/show-lesson-contest/{moduleID}")
    List<ContestManagementShowDTO> getContestManagementShowDTO(@PathVariable("moduleID") Long moduleID, String userName){
        userName = UserContext.getUsername();
        return lessonService.getContestManagementShowDTO(moduleID, userName);
    }

}

