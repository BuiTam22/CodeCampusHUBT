package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.CodingSubmission.CodingSubmissionShow;
import com.codecampushubt.NCKH2024TQQD.entity.CodingSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission, Long> {
    @Query("""
            SELECT new com.codecampushubt.NCKH2024TQQD.dto.CodingSubmission.CodingSubmissionShow
            (cb.user.userName, cb.code, cb.language, cb.status, cb.testCasesPassed, cb.totalTestCases, cb.score)
            FROM CodingSubmission cb
            WHERE cb.user.userName = :userName
            """)
    List<CodingSubmissionShow> getCodingSubmissionShowByUserName(@Param("userName") String theUserName);
}
