package com.codecampushubt.NCKH2024TQQD.service.CodingSubmissionServices;

import com.codecampushubt.NCKH2024TQQD.dao.CodingSubmissionRepository;
import com.codecampushubt.NCKH2024TQQD.entity.CodingSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodingSubmissionServiceImpl implements CodingSubmissionService{
    private final CodingSubmissionRepository codingSubmissionRepository;

    @Autowired
    public CodingSubmissionServiceImpl(CodingSubmissionRepository codingSubmissionRepository) {
        this.codingSubmissionRepository = codingSubmissionRepository;
    }

    @Override
    public CodingSubmission save(CodingSubmission codingSubmission) {

        return codingSubmissionRepository.save(codingSubmission);
    }
}
