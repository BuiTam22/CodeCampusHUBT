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

    /**
     * Lấy tất cả các lần nộp của user cho một exercise cụ thể (theo slug),
     * sắp xếp mới nhất lên đầu. Dùng cho trang "Đã nộp" của essay exercise.
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
            WHERE es.user.userName = :userName
              AND es.exercise.slug = :exerciseSlug
            ORDER BY es.submittedAt DESC
            """)
    List<ExerciseSubmissionDTO> getSubmissionsByUserAndExerciseSlug(
            @Param("userName") String userName,
            @Param("exerciseSlug") String exerciseSlug);

    @Query("""
        SELECT new com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.EssayScoreDetailDTO(
            es.submissionID,
            e.title,
            u.userName,
            es.answerText,
            es.feedback,
            es.score,
            es.submittedAt,
            es.finalScore,
            es.teacherFeedBack
        )
        FROM EssaySubmission es
        JOIN es.exercise e
        JOIN e.lesson l
        JOIN es.user u
        WHERE e.lesson.lessonID = :lessonId
        ORDER BY u.userName, es.submittedAt DESC
    """)
    List<com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.EssayScoreDetailDTO> getEssayScoreDetailsByLessonId(@Param("lessonId") Long lessonId);

    /**
     * Lấy submission MỚI NHẤT của mỗi user trong một lesson.
     * Mỗi user chỉ xuất hiện 1 lần (unique by userName).
     * Dùng cho trang chấm điểm: 1 user = 1 lần chấm duy nhất.
     */
    @Query("""
        SELECT new com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.EssayScoreDetailDTO(
            es.submissionID,
            e.title,
            u.userName,
            es.answerText,
            es.feedback,
            es.score,
            es.submittedAt,
            es.finalScore,
            es.teacherFeedBack
        )
        FROM EssaySubmission es
        JOIN es.exercise e
        JOIN e.lesson l
        JOIN es.user u
        WHERE e.lesson.lessonID = :lessonId
          AND es.submittedAt = (
              SELECT MAX(es2.submittedAt)
              FROM EssaySubmission es2
              JOIN es2.exercise e2
              WHERE es2.user.userName = u.userName
                AND e2.lesson.lessonID = :lessonId
          )
        ORDER BY u.userName ASC
    """)
    List<com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.EssayScoreDetailDTO> getLatestEssayScoreDetailPerUserByLessonId(@Param("lessonId") Long lessonId);

    @Modifying
    @Query("UPDATE EssaySubmission es SET es.finalScore = :finalScore, es.teacherFeedBack = :teacherFeedback WHERE es.submissionID = :submissionId")
    void updateTeacherReviewBySubmissionId(@Param("submissionId") Long submissionId, @Param("finalScore") Double finalScore, @Param("teacherFeedback") String teacherFeedback);
}
