package com.codecampushubt.NCKH2024TQQD.controller.Admin.Course;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dao.UserRepository;
import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowithRolenameDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Course;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import com.codecampushubt.NCKH2024TQQD.service.CourseServices.CourseService;
import com.github.slugify.Slugify;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/api/course")
public class courseApiController {
    private final CourseService courseService;
    private final UserRepository userRepository;

    public courseApiController(CourseService courseService, UserRepository userRepository) {
        this.courseService = courseService;
        this.userRepository = userRepository;
    }

    @GetMapping("/show")
    public ResponseEntity<List<CourseShowithRolenameDTO>> showCoursesBasedOnRole(){
        List<CourseShowithRolenameDTO> course = courseService.getAllCoursesBasedOnUserRole();
        return ResponseEntity.ok(course);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody Map<String, Object> dto) {
        try {
            String title = (String) dto.get("title");
            if (title == null || title.isBlank()) {
                return ResponseEntity.badRequest().body("Tên khóa học không được để trống");
            }

            // Tìm user hiện tại làm instructor
            String username = UserContext.getUsername();
            Optional<User> userOpt = userRepository.findByUserName(username);
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Không tìm thấy người dùng hiện tại");
            }

            Course course = new Course();
            course.setTitle(title);
            course.setDescription((String) dto.getOrDefault("description", ""));

            // Tạo slug
            String baseSlug = new Slugify().slugify(title);
            course.setSlug(courseService.generateUniqueSlug(baseSlug));

            // Giá
            if (dto.get("price") != null) {
                course.setPrice(new BigDecimal(dto.get("price").toString()));
            }
            if (dto.get("discountPrice") != null && !dto.get("discountPrice").toString().isEmpty()) {
                course.setDiscountPrice(new BigDecimal(dto.get("discountPrice").toString()));
            }

            course.setInstructor(userOpt.get());
            course.setStatus("draft");
            course.setPublished(false);
            course.setCreatedAt(LocalDateTime.now());
            course.setUpdatedAt(LocalDateTime.now());

            courseService.save(course);
            return ResponseEntity.ok(Map.of("message", "Thêm khóa học thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/showUpdate/{courseId}")
    public ResponseEntity<?> showUpdateCourse(@PathVariable Long courseId) {
        try {
            Course course = courseService.findById(courseId);
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khóa học");
            }
            // Trả về các trường cần thiết
            Map<String, Object> data = Map.of(
                "courseID", course.getCourseID(),
                "title", course.getTitle() != null ? course.getTitle() : "",
                "description", course.getDescription() != null ? course.getDescription() : "",
                "price", course.getPrice(),
                "discountPrice", course.getDiscountPrice() != null ? course.getDiscountPrice() : 0,
                "status", course.getStatus() != null ? course.getStatus() : "draft"
            );
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody Map<String, Object> dto) {
        try {
            Course course = courseService.findById(courseId);
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khóa học");
            }

            if (dto.containsKey("title")) course.setTitle((String) dto.get("title"));
            if (dto.containsKey("description")) course.setDescription((String) dto.get("description"));
            if (dto.containsKey("price")) {
                course.setPrice(new java.math.BigDecimal(dto.get("price").toString()));
            }
            if (dto.containsKey("discountPrice")) {
                Object dp = dto.get("discountPrice");
                course.setDiscountPrice(dp != null ? new java.math.BigDecimal(dp.toString()) : null);
            }
            course.setUpdatedAt(LocalDateTime.now());

            courseService.save(course);
            return ResponseEntity.ok(Map.of("message", "Cập nhật khóa học thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        try {
            Course course = courseService.findById(courseId);
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khóa học");
            }
            // Soft delete - đặt deletedAt
            course.setDeletedAt(LocalDateTime.now());
            courseService.save(course);
            return ResponseEntity.ok(Map.of("message", "Xóa khóa học thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
