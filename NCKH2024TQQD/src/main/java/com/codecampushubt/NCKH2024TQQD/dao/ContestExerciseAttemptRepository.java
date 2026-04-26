package com.codecampushubt.NCKH2024TQQD.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codecampushubt.NCKH2024TQQD.dto.ContestExerciseAttempt.AttemptInfoDTO;
import com.codecampushubt.NCKH2024TQQD.entity.ContestExerciseAttempt;

import java.util.List;

@Repository
public interface ContestExerciseAttemptRepository extends JpaRepository<ContestExerciseAttempt, Long> {
    // LẤY RA SỐ LẦN LÀM BÀI (Attempt) CỦA USER
    @Query("""
    SELECT DISTINCT new com.codecampushubt.NCKH2024TQQD.dto.ContestExerciseAttempt.AttemptInfoDTO
    (cea.lesson.lessonID, cea.exerciseType, cea.attemptNumber)
    FROM ContestExerciseAttempt cea
    WHERE cea.user.userID = :userID 
      AND cea.exerciseID = :exerciseID
      AND cea.exerciseType = :exerciseType
      AND cea.attemptNumber = (
          SELECT MAX(c2.attemptNumber)
          FROM ContestExerciseAttempt c2
          WHERE c2.user.userID = :userID AND c2.exerciseID = :exerciseID
      )
    """)
    AttemptInfoDTO getAttemptInfoDTOByuserIDAndExerciseID(@Param("userID") Long userID, @Param("exerciseID") Long exerciseID, @Param("exerciseType") String exerciseType);

    @Modifying
    @Query("DELETE FROM ContestExerciseAttempt cea WHERE cea.exerciseID = :exerciseID")
    void deleteByExerciseID(@Param("exerciseID") Long exerciseID);

    /**
     * Lấy danh sách (exerciseID, attemptNumber lớn nhất) của tất cả bài
     * mà user đã attempt trong một exerciseType cụ thể.
     * Dùng để build Map<Long, Integer> trong controller.
     */
    @Query("""
    SELECT cea.exerciseID, MAX(cea.attemptNumber)
    FROM ContestExerciseAttempt cea
    WHERE cea.user.userID = :userID
      AND cea.exerciseType = :exerciseType
    GROUP BY cea.exerciseID
    """)
    List<Object[]> getAttemptSummaryByUserAndType(
            @Param("userID") Long userID,
            @Param("exerciseType") String exerciseType);
}


