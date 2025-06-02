package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.LessonSubmissionDTO;
import com.codecampushubt.NCKH2024TQQD.entity.ContestExerciseAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonSubmissionRepository extends JpaRepository<ContestExerciseAttempt, Long> {

    // LẤY RA ĐIỂM CỦA CẢ LESSON VỚI AttemptNumber = 1 với các submission
    @Query("""
    SELECT new com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.LessonSubmissionDTO(
        cl.title,
        u.userName,
        MAX(cea.submittedAt),
        SUM(cea.score),
        'Done'
    )
    FROM ContestExerciseAttempt cea
    JOIN cea.user u
    JOIN cea.lesson cl
    WHERE cea.attemptNumber = 1 AND cea.lesson.lessonID = :lessonId
    GROUP BY cl.title, u.userName
""")
    List<LessonSubmissionDTO> getLessonSubmissionsByLessonId(@Param("lessonId") Long lessonId);

}
