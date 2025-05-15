package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseListShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.EssayExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EssayExerciseRepository extends JpaRepository<EssayExercise, Long> {
    @Query("""
            SELECT new com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseListShowDTO
            (ee.exerciseID, ee.lesson.title, ee.title, ee.description, ee.subjectName, ee.difficulty, ee.points, ee.slug)
            FROM EssayExercise ee
            WHERE ee.lesson.slug = :theSlug
            """)
    List<EssayExerciseListShowDTO> getEssayExerciseListShowDTOByLessonSlug(@Param("theSlug") String theSlug);
}
