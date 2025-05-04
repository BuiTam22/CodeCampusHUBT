package com.codecampushubt.NCKH2024TQQD.controller.Admin.Lessons;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dao.LessonReponsitoryA;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.CourseLessonShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/api/lesson")
public class LessonApiController {
    private final LessonReponsitoryA lessonReponsitoryA;
    private final LessonService lessonService;
    public LessonApiController(LessonReponsitoryA lessonReponsitoryA, LessonService lessonService) {
        this.lessonReponsitoryA = lessonReponsitoryA;
        this.lessonService = lessonService;
    }

    @GetMapping("/show")
    public ResponseEntity<List<CourseLessonShowDTO>> getLessonShowDTOByIsContest() {
        List<CourseLessonShowDTO> showlesson = lessonService.getAllCourseLessonShowDTOonroleNameORid();
//        List<CourseLessonShowDTO> showb =
        return ResponseEntity.ok(showlesson);

    }

}
