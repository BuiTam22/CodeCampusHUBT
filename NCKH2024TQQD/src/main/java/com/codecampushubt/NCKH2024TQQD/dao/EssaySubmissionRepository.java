package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.ExerciseSubmissionDTO;
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
                (SELECT COUNT(e2) FROM EssayExercise e2 WHERE e2.lesson = l),
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

    /**
     * Lấy danh sách essay exercise đã nộp của user trong một lesson cụ thể.
     * Chỉ lấy submission mới nhất cho mỗi exercise.
     */
    @Query("""
            SELECT new com.codecampushubt.NCKH2024TQQD.dto.UserDTO.ExerciseSubmissionDTO(
                es.exercise.title,
                es.exercise.slug,
                'essay',
                es.score,
                'submitted',
                es.submittedAt
            )
            FROM EssaySubmission es
            JOIN es.exercise e
            JOIN e.lesson l
            WHERE es.user.userName = :userName
              AND l.slug = :lessonSlug
              AND es.submittedAt = (
                  SELECT MAX(es2.submittedAt)
                  FROM EssaySubmission es2
                  WHERE es2.user.userName = :userName
                    AND es2.exercise.exerciseID = es.exercise.exerciseID
              )
            ORDER BY es.submittedAt DESC
            """)
    List<ExerciseSubmissionDTO> getEssayExercisesByLessonSlugAndUser(
            @Param("userName") String userName,
            @Param("lessonSlug") String lessonSlug);
}

