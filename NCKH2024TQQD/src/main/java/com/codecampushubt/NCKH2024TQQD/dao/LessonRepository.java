package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<CourseLesson, Long> {

    @Query("""
    SELECT new com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO(
    cl.lessonID, cl.module.id, cl.title, cl.description, cl.type, cl.content,
    cl.videoUrl, cl.duration, cl.orderIndex, cl.isPreview, cl.isPublished, cl.slug)
    FROM CourseLesson cl
    WHERE cl.module.id = :moduleID
    """)
    List<LessonShowDTO> getLessonShowDTO(@Param("moduleID") Long moduleID);



    boolean existsBySlug(String slug);




}
