package com.codecampushubt.NCKH2024TQQD.service.LessonServices;

import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.*;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;


import java.util.List;
import java.util.Optional;

public interface LessonService {
    List<LessonShowDTO> getLessonShowDTO(Long theID);
    List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(Long moduleID, String search);
    List<ContestShowDTO> getContestShowDTOByIsContest(Long moduleID);
    List<ContestShowDTO> getEssayContestShowDTOByIsContest(Long moduleID);
    List<LessonShowDTOA> getLessonShowDTOA();
    CourseLesson addLesson(CreateLessonsDTO dto);
    List<ContestManagementShowDTO> getContestManagementShowDTO(Long moduleID, String userName);
    CourseLesson save(CourseLesson theLesson);
    EditLessonDTO getEditLessonDTO(Long moduleID, String theSlug);
    Optional<CourseLesson> findById(Long id);
    Long findLessonIdBySlug(String slug);

    // Lấy top N lesson có orderIndex cao nhất cho trang chủ
    List<HomeLessonDTO> getTopLessonsForHome(int limit);
}

