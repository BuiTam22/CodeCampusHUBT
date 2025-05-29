package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CodingExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodingExerciseRepository extends JpaRepository<CodingExercise, Long> {

    /**
     * Retrieves a list of CodingExerciseDTO objects based on the lesson slug.
     *
     * @param theSlug The slug of the lesson to retrieve coding exercises for.
     * @return A list of CodingExerciseDTO objects containing exercise details.
     */
    @Query("""
            SELECT new com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDTO
            (ce.exerciseID, ce.lesson.title, ce.title, ce.description, ce.programmingLanguage, ce.difficulty, ce.points, ce.slug)
            FROM CodingExercise ce
            WHERE ce.lesson.slug = :theSlug
            """)
    List<CodingExerciseDTO> getCodingExerciseDTOByLessonSlug(@Param("theSlug") String theSlug);

    /**
     * Retrieves a CodingExerciseDetailDTO object based on the given exercise slug.
     *
     * @param theSlug The slug of the exercise to retrieve details for.
     * @return The CodingExerciseDetailDTO object containing the exercise details.
     */
    @Query("""
            SELECT new com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDetailDTO
            (ce.exerciseID, null, ce.title, ce.description, ce.programmingLanguage, ce.initialCode,
            ce.timeLimit, ce.memoryLimit, ce.difficulty, ce.points, ce.slug, ce.inputFormat, ce.outputFormat,
            ce.constraintName)
            FROM CodingExercise ce
            WHERE ce.slug = :theSlug
            """)
    CodingExerciseDetailDTO getCodingExerciseDetailDTOByExerciseSlug(@Param("theSlug") String theSlug);

    /**
     * Retrieves a CodingExercise entity by its exercise ID.
     *
     * @param exerciseID The ID of the exercise entity to retrieve.
     * @return The CodingExercise entity with the specified exercise ID.
     */
    @Query("SELECT ce FROM CodingExercise ce WHERE ce.exerciseID = :exerciseID")
    CodingExercise getExerciseEntityByID(@Param("exerciseID") Long exerciseID);

    // Kiểm tra xem CodingExercise có phải nằm trong contest không
    @Query("""
        SELECT ce.lesson.isContest 
        FROM CodingExercise ce 
        WHERE ce.exerciseID = :exerciseID
        """)
    boolean isExerciseInContestLesson(@Param("exerciseID") Long exerciseID);

    // LẤY RA lessonID từ coding exerciseID
    @Query("""
        SELECT ce.lesson.lessonID 
        FROM CodingExercise ce 
        WHERE ce.exerciseID = :exerciseID
    """)
    Long getLessonIDByExerciseID(@Param("exerciseID") Long exerciseID);

}
