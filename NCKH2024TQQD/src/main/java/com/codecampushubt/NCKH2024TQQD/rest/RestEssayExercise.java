package com.codecampushubt.NCKH2024TQQD.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.ManageEssayExerciseRequestDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;
import com.codecampushubt.NCKH2024TQQD.entity.EssayExercise;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseDetailShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseListShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.EssayExerciseServices.EssayExerciseService;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/essay-exercise")
public class RestEssayExercise {


    private final EssayExerciseService essayExerciseService;
    private final LessonService lessonService;

    @Autowired
    public RestEssayExercise(EssayExerciseService essayExerciseService, LessonService lessonService) {
        this.essayExerciseService = essayExerciseService;
        this.lessonService = lessonService;
    }

    @GetMapping("/{lessonSlug}")
    public List<EssayExerciseListShowDTO> getEssayExerciseListShowDTOByLessonSlug(@PathVariable("lessonSlug") String theSlug) {
        return essayExerciseService.getEssayExerciseListShowDTOByLessonSlug(theSlug);
    }

    @GetMapping("/problem/{exerciseSlug}")
    public EssayExerciseDetailShowDTO getEssayExerciseDetailShowDTOBySlug(@PathVariable("exerciseSlug") String theSlug) {
        return essayExerciseService.getEssayExerciseDetailShowDTOBySlug(theSlug);
    }

    @PostMapping("/management/{lessonSlug}")
    public ResponseEntity<?> createEssayExercise(@PathVariable("lessonSlug") String lessonSlug,
                                                 @RequestBody ManageEssayExerciseRequestDTO requestDTO) {
        Optional<CourseLesson> lessonOptional = getOwnedLesson(lessonSlug);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(403).body(Map.of("status", "error", "message", "Forbidden"));
        }

        if (requestDTO.getTitle() == null || requestDTO.getTitle().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Title is required"));
        }

        LocalDateTime now = LocalDateTime.now();
        EssayExercise exercise = new EssayExercise();
        exercise.setLesson(lessonOptional.get());
        exercise.setTitle(requestDTO.getTitle().trim());
        exercise.setDescription(requestDTO.getDescription());
        exercise.setSubjectName(requestDTO.getSubjectName());
        exercise.setExpectedAnswer(requestDTO.getExpectedAnswer());
        exercise.setTimeLimit(requestDTO.getTimeLimit());
        exercise.setDifficulty(requestDTO.getDifficulty() == null || requestDTO.getDifficulty().isBlank() ? "medium" : requestDTO.getDifficulty().trim());
        exercise.setPoints(requestDTO.getPoints() == null ? 0 : requestDTO.getPoints());
        exercise.setCreatedAt(now);
        exercise.setUpdatedAt(now);
        exercise.setSlug(generateSlug(requestDTO.getTitle()));
        EssayExercise saved = essayExerciseService.save(exercise);
        return ResponseEntity.ok(Map.of("status", "success", "exerciseId", saved.getExerciseID()));
    }

    @PutMapping("/management/{exerciseID}")
    public ResponseEntity<?> updateEssayExercise(@PathVariable("exerciseID") Long exerciseID,
                                                 @RequestBody ManageEssayExerciseRequestDTO requestDTO) {
        Optional<EssayExercise> exerciseOptional = essayExerciseService.findById(exerciseID);
        if (exerciseOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Exercise not found"));
        }
        EssayExercise exercise = exerciseOptional.get();
        if (exercise.getLesson() == null || exercise.getLesson().getCreator() == null
                || !UserContext.getUsername().equals(exercise.getLesson().getCreator().getuserName())) {
            return ResponseEntity.status(403).body(Map.of("status", "error", "message", "Forbidden"));
        }

        if (requestDTO.getTitle() != null && !requestDTO.getTitle().isBlank()) {
            exercise.setTitle(requestDTO.getTitle().trim());
        }
        exercise.setDescription(requestDTO.getDescription());
        exercise.setSubjectName(requestDTO.getSubjectName());
        exercise.setExpectedAnswer(requestDTO.getExpectedAnswer());
        exercise.setTimeLimit(requestDTO.getTimeLimit());
        if (requestDTO.getDifficulty() != null && !requestDTO.getDifficulty().isBlank()) {
            exercise.setDifficulty(requestDTO.getDifficulty().trim());
        }
        if (requestDTO.getPoints() != null) {
            exercise.setPoints(requestDTO.getPoints());
        }
        exercise.setUpdatedAt(LocalDateTime.now());
        essayExerciseService.save(exercise);
        return ResponseEntity.ok(Map.of("status", "success"));
    }

    @DeleteMapping("/management/{exerciseID}")
    public ResponseEntity<?> deleteEssayExercise(@PathVariable("exerciseID") Long exerciseID) {
        Optional<EssayExercise> exerciseOptional = essayExerciseService.findById(exerciseID);
        if (exerciseOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Exercise not found"));
        }
        EssayExercise exercise = exerciseOptional.get();
        if (exercise.getLesson() == null || exercise.getLesson().getCreator() == null
                || !UserContext.getUsername().equals(exercise.getLesson().getCreator().getuserName())) {
            return ResponseEntity.status(403).body(Map.of("status", "error", "message", "Forbidden"));
        }
        essayExerciseService.deleteById(exerciseID);
        return ResponseEntity.ok(Map.of("status", "success"));
    }

    @GetMapping("/management/detail/{exerciseID}")
    public ResponseEntity<?> getEssayExerciseDetail(@PathVariable("exerciseID") Long exerciseID) {
        Optional<EssayExercise> exerciseOptional = essayExerciseService.findById(exerciseID);
        if (exerciseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        EssayExercise exercise = exerciseOptional.get();
        return ResponseEntity.ok(Map.of(
                "expectedAnswer", exercise.getExpectedAnswer() != null ? exercise.getExpectedAnswer() : "",
                "timeLimit", exercise.getTimeLimit() != null ? exercise.getTimeLimit() : 0
        ));
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

}
