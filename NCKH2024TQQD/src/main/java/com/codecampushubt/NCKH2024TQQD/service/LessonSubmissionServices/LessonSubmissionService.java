package com.codecampushubt.NCKH2024TQQD.service.LessonSubmissionServices;

import com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.LessonSubmissionDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonSubmissionService {
    List<LessonSubmissionDTO> getLessonSubmissionsByLessonId(Long lessonID);

}
