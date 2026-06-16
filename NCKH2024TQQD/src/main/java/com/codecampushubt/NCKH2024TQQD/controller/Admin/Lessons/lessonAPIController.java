package com.codecampushubt.NCKH2024TQQD.controller.Admin.Lessons;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dao.LessonRepository;
import com.codecampushubt.NCKH2024TQQD.dto.CourseModule.CourseModuleFILLDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.CreateLessonsDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTOA;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/api/lesson")
public class lessonAPIController {
    private final LessonRepository lessonRepository;
    private final LessonService lessonService;
    @Lazy
    public lessonAPIController(LessonService lessonService, LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
        this.lessonService = lessonService;
    }

    @GetMapping("/show")
    public ResponseEntity<List<LessonShowDTOA>> showLessons() {
        List<LessonShowDTOA> showlesson = lessonService.getLessonShowDTOA();
        return ResponseEntity.ok(showlesson);
    }

    @GetMapping("/add")
    public ResponseEntity<List<CourseModuleFILLDTO>> addLesson() {
        String username = UserContext.getUsername();
        List<CourseModuleFILLDTO> courseName = lessonRepository.findModulesByInstructorUserName(username);
        return ResponseEntity.ok(courseName);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addLesson(@RequestBody CreateLessonsDTO createLessonsDTO) {
        System.out.println(createLessonsDTO);
        lessonService.addLesson(createLessonsDTO);
        return ResponseEntity.ok("Thêm Thành Công ");
    }

    @GetMapping("/showUpdate/{lessonId}")
    public ResponseEntity<?> showUpdateLesson(@PathVariable Long lessonId) {
        try {
            Optional<CourseLesson> opt = lessonService.findById(lessonId);
            if (opt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài học");
            }
            CourseLesson lesson = opt.get();
            Map<String, Object> data = Map.of(
                "lessonId", lesson.getLessonID(),
                "title", lesson.getTitle() != null ? lesson.getTitle() : "",
                "description", lesson.getDescription() != null ? lesson.getDescription() : "",
                "type", lesson.getType() != null ? lesson.getType() : "",
                "duration", lesson.getDuration() != null ? lesson.getDuration() : 0,
                "content", lesson.getContent() != null ? lesson.getContent() : ""
            );
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update/{lessonId}")
    public ResponseEntity<?> updateLesson(@PathVariable Long lessonId, @RequestBody Map<String, Object> dto) {
        try {
            Optional<CourseLesson> opt = lessonService.findById(lessonId);
            if (opt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài học");
            }
            CourseLesson lesson = opt.get();

            if (dto.containsKey("title")) lesson.setTitle((String) dto.get("title"));
            if (dto.containsKey("description")) lesson.setDescription((String) dto.get("description"));
            if (dto.containsKey("type")) lesson.setType((String) dto.get("type"));
            if (dto.containsKey("content")) lesson.setContent((String) dto.get("content"));
            if (dto.containsKey("duration") && dto.get("duration") != null) {
                lesson.setDuration(Integer.parseInt(dto.get("duration").toString()));
            }

            lessonService.save(lesson);
            return ResponseEntity.ok(Map.of("message", "Cập nhật bài học thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {
        try {
            Optional<CourseLesson> opt = lessonService.findById(lessonId);
            if (opt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài học");
            }
            lessonRepository.deleteById(lessonId);
            return ResponseEntity.ok(Map.of("message", "Xóa bài học thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
