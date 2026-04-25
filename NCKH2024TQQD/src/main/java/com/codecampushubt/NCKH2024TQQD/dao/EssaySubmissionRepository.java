package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.entity.EssaySubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EssaySubmissionRepository extends JpaRepository<EssaySubmission, Long> {

    @Modifying
    @Query("DELETE FROM EssaySubmission es WHERE es.exercise.exerciseID = :exerciseID")
    void deleteByExerciseID(@Param("exerciseID") Long exerciseID);
}
