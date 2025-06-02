package com.codecampushubt.NCKH2024TQQD.service.LessonSubmissionServices;

import com.codecampushubt.NCKH2024TQQD.dao.LessonSubmissionRepository;
import com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.LessonSubmissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonSubmissionImpl implements LessonSubmissionService{
    private final LessonSubmissionRepository lessonSubmissionRepository;

    @Autowired
    public LessonSubmissionImpl(LessonSubmissionRepository lessonSubmissionRepository) {
        this.lessonSubmissionRepository = lessonSubmissionRepository;
    }

    @Override
    public List<LessonSubmissionDTO> getLessonSubmissionsByLessonId(Long lessonID) {
        return lessonSubmissionRepository.getLessonSubmissionsByLessonId(lessonID);
    }
}
