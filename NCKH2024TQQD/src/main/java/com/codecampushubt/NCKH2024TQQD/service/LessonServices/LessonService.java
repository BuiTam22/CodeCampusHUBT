package com.codecampushubt.NCKH2024TQQD.service.LessonServices;

import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.ContestShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTOA;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonService {
    List<LessonShowDTO> getLessonShowDTO(Long theID);
    List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(Long moduleID, String search);
    List<ContestShowDTO> getContestShowDTOByIsContest(Long moduleID);
    List<LessonShowDTOA> getLessonShowDTOA();

}
