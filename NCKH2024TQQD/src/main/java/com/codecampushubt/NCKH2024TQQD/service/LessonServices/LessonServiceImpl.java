package com.codecampushubt.NCKH2024TQQD.service.LessonServices;

import com.codecampushubt.NCKH2024TQQD.dao.LessonRepository;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService{
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<LessonShowDTO> getLessonShowDTO(Long theID) {
        return lessonRepository.getLessonShowDTO(theID);
    }

    @Override
    public List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(Long moduleID, String search) {
        String processed = search.replace("++", "-plus-plus");
        String newSlug = new Slugify().slugify(processed);
        return lessonRepository.getLessonShowDTOByModuleIDAndSlug(moduleID, newSlug);
    }

    @Override
    public List<LessonShowDTO> getLessonShowDTOByIsContest(Long moduleID) {
        return lessonRepository.getLessonShowDTOByIsContest(moduleID);
    }
}
