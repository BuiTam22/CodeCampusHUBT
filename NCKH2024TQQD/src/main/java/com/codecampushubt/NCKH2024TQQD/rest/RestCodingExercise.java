package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.dao.ExerciseTestCaseRepository;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.ManageCodingExerciseRequestDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CodingExercise;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;
import com.codecampushubt.NCKH2024TQQD.entity.ExerciseTestCase;
import com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices.CodingExerciseService;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import com.github.slugify.Slugify;
import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/coding-exercise")
public class RestCodingExercise {
    private final CodingExerciseService codingExerciseService;
    private final LessonService lessonService;
    private final ExerciseTestCaseRepository exerciseTestCaseRepository;

    @Autowired
    public RestCodingExercise(CodingExerciseService codingExerciseService,
                              LessonService lessonService,
                              ExerciseTestCaseRepository exerciseTestCaseRepository) {
        this.codingExerciseService = codingExerciseService;
        this.lessonService = lessonService;
        this.exerciseTestCaseRepository = exerciseTestCaseRepository;
    }


    @GetMapping("/find-by-lesson-id/{theSlug}")
    public List<CodingExerciseDTO> getCodingExerciseDTOByLessonSlug(@PathVariable("theSlug") String theSlug){
        return codingExerciseService.getCodingExerciseDTOByLessonSlug(theSlug   );
    }

    @GetMapping("/find-by-slug/{theSlug}")
    public CodingExerciseDetailDTO getCodingExerciseDetailDTOByExerciseSlug(@PathVariable("theSlug") String theSlug){
        return codingExerciseService.getCodingExerciseDetailDTOByExerciseSlug(theSlug);
    }

    @PostMapping("/management/{lessonSlug}")
    public ResponseEntity<?> createCodingExercise(@PathVariable("lessonSlug") String lessonSlug,
                                                  @RequestBody ManageCodingExerciseRequestDTO requestDTO) {
        Optional<CourseLesson> lessonOptional = getOwnedLesson(lessonSlug);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(403).body(Map.of("status", "error", "message", "Forbidden"));
        }

        if (requestDTO.getTitle() == null || requestDTO.getTitle().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Title is required"));
        }

        LocalDateTime now = LocalDateTime.now();
        CodingExercise exercise = new CodingExercise();
        exercise.setLesson(lessonOptional.get());
        exercise.setTitle(requestDTO.getTitle().trim());
        exercise.setDescription(requestDTO.getDescription());
        exercise.setProgrammingLanguage(requestDTO.getProgrammingLanguage());
        exercise.setDifficulty(requestDTO.getDifficulty() == null || requestDTO.getDifficulty().isBlank() ? "medium" : requestDTO.getDifficulty().trim());
        exercise.setPoints(requestDTO.getPoints() == null ? 0 : requestDTO.getPoints());
        exercise.setSolutionCode(requestDTO.getSolutionCode());
        exercise.setTimeLimit(requestDTO.getTimeLimit() != null ? requestDTO.getTimeLimit() : 1000);
        exercise.setMemoryLimit(requestDTO.getMemoryLimit() != null ? requestDTO.getMemoryLimit() : 256);
        exercise.setInputFormat(requestDTO.getInputFormat());
        exercise.setOutputFormat(requestDTO.getOutputFormat());
        exercise.setConstraintName(requestDTO.getConstraintName());
        exercise.setCreatedAt(now);
        exercise.setUpdatedAt(now);
        exercise.setSlug(generateSlug(requestDTO.getTitle()));
        exercise.setSlug(generateSlug(requestDTO.getTitle()));
        CodingExercise saved = codingExerciseService.save(exercise);

        // Tạo ExerciseTestCases nếu có
        saveTestCases(saved, requestDTO.getTestCases());

        return ResponseEntity.ok(Map.of("status", "success", "exerciseId", saved.getExerciseID()));
    }

    @Transactional
    @PutMapping("/management/{exerciseID}")
    public ResponseEntity<?> updateCodingExercise(@PathVariable("exerciseID") Long exerciseID,
                                                  @RequestBody ManageCodingExerciseRequestDTO requestDTO) {
        CodingExercise exercise = codingExerciseService.getExerciseEntityByID(exerciseID);
        if (exercise == null || exercise.getLesson() == null || exercise.getLesson().getCreator() == null
                || !UserContext.getUsername().equals(exercise.getLesson().getCreator().getuserName())) {
            return ResponseEntity.status(403).body(Map.of("status", "error", "message", "Forbidden"));
        }

        if (requestDTO.getTitle() != null && !requestDTO.getTitle().isBlank()) {
            exercise.setTitle(requestDTO.getTitle().trim());
        }
        exercise.setDescription(requestDTO.getDescription());
        exercise.setProgrammingLanguage(requestDTO.getProgrammingLanguage());
        if (requestDTO.getDifficulty() != null && !requestDTO.getDifficulty().isBlank()) {
            exercise.setDifficulty(requestDTO.getDifficulty().trim());
        }
        if (requestDTO.getPoints() != null) {
            exercise.setPoints(requestDTO.getPoints());
        }
        exercise.setSolutionCode(requestDTO.getSolutionCode());
        if (requestDTO.getTimeLimit() != null) {
            exercise.setTimeLimit(requestDTO.getTimeLimit());
        }
        if (requestDTO.getMemoryLimit() != null) {
            exercise.setMemoryLimit(requestDTO.getMemoryLimit());
        }
        exercise.setInputFormat(requestDTO.getInputFormat());
        exercise.setOutputFormat(requestDTO.getOutputFormat());
        exercise.setConstraintName(requestDTO.getConstraintName());
        exercise.setUpdatedAt(LocalDateTime.now());
        codingExerciseService.save(exercise);

        // Cập nhật test cases: xóa cũ rồi tạo mới
        if (requestDTO.getTestCases() != null) {
            exerciseTestCaseRepository.deleteByExerciseID(exerciseID);
            saveTestCases(exercise, requestDTO.getTestCases());
        }

        return ResponseEntity.ok(Map.of("status", "success"));
    }

    @GetMapping("/management/test-cases/{exerciseID}")
    public ResponseEntity<?> getTestCasesByExerciseID(@PathVariable("exerciseID") Long exerciseID) {
        return ResponseEntity.ok(exerciseTestCaseRepository.getExerciseTestCasesDTOByExerciseID(exerciseID));
    }

    @GetMapping("/management/detail/{exerciseID}")
    public ResponseEntity<?> getExerciseDetail(@PathVariable("exerciseID") Long exerciseID) {
        CodingExercise exercise = codingExerciseService.getExerciseEntityByID(exerciseID);
        if (exercise == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
                "solutionCode", exercise.getSolutionCode() != null ? exercise.getSolutionCode() : "",
                "timeLimit", exercise.getTimeLimit() != null ? exercise.getTimeLimit() : 1000,
                "memoryLimit", exercise.getMemoryLimit() != null ? exercise.getMemoryLimit() : 256,
                "inputFormat", exercise.getInputFormat() != null ? exercise.getInputFormat() : "",
                "outputFormat", exercise.getOutputFormat() != null ? exercise.getOutputFormat() : "",
                "constraintName", exercise.getConstraintName() != null ? exercise.getConstraintName() : ""
        ));
    }

    @DeleteMapping("/management/{exerciseID}")
    public ResponseEntity<?> deleteCodingExercise(@PathVariable("exerciseID") Long exerciseID) {
        CodingExercise exercise = codingExerciseService.getExerciseEntityByID(exerciseID);
        if (exercise == null || exercise.getLesson() == null || exercise.getLesson().getCreator() == null
                || !UserContext.getUsername().equals(exercise.getLesson().getCreator().getuserName())) {
            return ResponseEntity.status(403).body(Map.of("status", "error", "message", "Forbidden"));
        }
        codingExerciseService.deleteById(exerciseID);
        return ResponseEntity.ok(Map.of("status", "success"));
    }

    private Optional<CourseLesson> getOwnedLesson(String lessonSlug) {
        Long lessonID = lessonService.findLessonIdBySlug(lessonSlug);
        if (lessonID == null) {
            return Optional.empty();
        }
        Optional<CourseLesson> lessonOptional = lessonService.findById(lessonID);
        if (lessonOptional.isEmpty()) {
            return Optional.empty();
        }
        CourseLesson lesson = lessonOptional.get();
        if (lesson.getCreator() == null || !UserContext.getUsername().equals(lesson.getCreator().getuserName())) {
            return Optional.empty();
        }
        return lessonOptional;
    }

    private String generateSlug(String title) {
        return new Slugify().slugify(title + "-" + System.currentTimeMillis());
    }

    private void saveTestCases(CodingExercise exercise, List<ManageCodingExerciseRequestDTO.TestCaseRequest> testCases) {
        if (testCases == null || testCases.isEmpty()) return;
        for (ManageCodingExerciseRequestDTO.TestCaseRequest tc : testCases) {
            ExerciseTestCase testCase = new ExerciseTestCase(
                    exercise,
                    tc.getInput(),
                    tc.getExpectedOutput(),
                    tc.getIsPublic() != null ? tc.getIsPublic() : false,
                    tc.getScore() != null ? tc.getScore() : 0
            );
            exerciseTestCaseRepository.save(testCase);
        }
    }
}

