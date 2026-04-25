package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.LessonProgressDTO;
import com.codecampushubt.NCKH2024TQQD.entity.EssaySubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EssaySubmissionRepository extends JpaRepository<EssaySubmission, Long> {

    @Modifying
    @Query("DELETE FROM EssaySubmission es WHERE es.exercise.exerciseID = :exerciseID")
    void deleteByExerciseID(@Param("exerciseID") Long exerciseID);

    @Query("""
            SELECT new com.codecampushubt.NCKH2024TQQD.dto.UserDTO.LessonProgressDTO(
                l.title, l.slug, l.type,
                0L,
                COUNT(DISTINCT es.exercise.exerciseID),
                COALESCE(SUM(es.score), 0.0)
            )
            FROM EssaySubmission es
            JOIN es.exercise e
            JOIN e.lesson l
            WHERE es.user.userName = :userName
            GROUP BY l.lessonID, l.title, l.slug, l.type
            """)
    List<LessonProgressDTO> getLessonProgressByUserName(@Param("userName") String userName);
}
