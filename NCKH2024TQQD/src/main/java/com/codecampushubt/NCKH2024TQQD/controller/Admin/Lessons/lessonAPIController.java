package com.codecampushubt.NCKH2024TQQD.controller.Admin.Lessons;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dao.LessonRepository;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.CreateLessonsDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTOA;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("/admin/api/lesson")

public class lessonAPIController {
    private final LessonRepository lessonRepository;
    private final LessonService lessonService;
    public lessonAPIController(LessonService lessonService , LessonRepository lessonRepository) {
        this.lessonService = lessonService;
        this.lessonRepository = lessonRepository;
    }
    @GetMapping("/show")
    public ResponseEntity<List<LessonShowDTOA>> showLessons() {
        List<LessonShowDTOA> showlesson = lessonService.getLessonShowDTOA();
        return ResponseEntity.ok(showlesson);
    }
    @GetMapping("/add")
    public ResponseEntity<List<String>> addLesson() {
        String username = UserContext.getUsername();
        List<String> courseName = lessonRepository.findCourseNamesByInstructorUsername(username);
        return ResponseEntity.ok(courseName);
    }
}
