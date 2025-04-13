package com.codecampushubt.NCKH2024TQQD.service.LessonServices;

import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;

import java.util.List;

public interface LessonService {
    List<LessonShowDTO> getLessonShowDTO(Long theID);
}
