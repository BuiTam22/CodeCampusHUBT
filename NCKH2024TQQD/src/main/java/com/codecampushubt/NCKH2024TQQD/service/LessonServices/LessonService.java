package com.codecampushubt.NCKH2024TQQD.service.LessonServices;

import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonService {
    List<LessonShowDTO> getLessonShowDTO(Long theID);
    List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(Long moduleID, String search);
    List<LessonShowDTO> getLessonShowDTOByIsContest(Long moduleID);

}
