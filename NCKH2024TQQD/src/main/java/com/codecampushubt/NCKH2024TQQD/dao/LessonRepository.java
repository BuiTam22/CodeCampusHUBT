package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<CourseLesson, Long> {

    @Query("""
        SELECT new com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO(
        cl.lessonID, cl.module.id, cl.title, cl.description, cl.type, cl.content,
        cl.videoUrl, cl.duration, cl.orderIndex, cl.isPreview, cl.isPublished, cl.slug)
        FROM CourseLesson cl
        WHERE cl.module.id = :moduleID
    """)
    List<LessonShowDTO> getLessonShowDTO(@Param("moduleID") Long moduleID);

    @Query("""
        SELECT new com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO(
        cl.lessonID, cl.module.id, cl.title, cl.description, cl.type, cl.content,
        cl.videoUrl, cl.duration, cl.orderIndex, cl.isPreview, cl.isPublished, cl.slug)
        FROM CourseLesson cl
        WHERE cl.module.id = :moduleID AND cl.slug LIKE %:slug%
    """)
    List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(@Param("moduleID") Long moduleID, @Param("slug") String theSlug);


    boolean existsBySlug(String slug);




}
