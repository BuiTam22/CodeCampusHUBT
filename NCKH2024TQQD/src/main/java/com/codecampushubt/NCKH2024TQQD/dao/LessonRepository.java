package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.ContestShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<CourseLesson, Long> {
    // lấy ra lesson luyện tập
    @Query("""
        SELECT new com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO(
        cl.lessonID, cl.module.id, cl.title, cl.description, cl.type, cl.content,
        cl.image, cl.duration, cl.orderIndex, cl.isPreview, cl.isPublished, cl.slug)
        FROM CourseLesson cl
        WHERE cl.module.id = :moduleID AND cl.isContest = false
    """)
    List<LessonShowDTO> getLessonShowDTO(@Param("moduleID") Long moduleID);

    // tìm kiếm lesson theo slug
    @Query("""
        SELECT new com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO(
        cl.lessonID, cl.module.id, cl.title, cl.description, cl.type, cl.content,
        cl.image, cl.duration, cl.orderIndex, cl.isPreview, cl.isPublished, cl.slug)
        FROM CourseLesson cl
        WHERE cl.module.id = :moduleID AND cl.slug LIKE %:slug% AND cl.isContest = false
    """)
    List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(@Param("moduleID") Long moduleID, @Param("slug") String theSlug);

    //Lấy ra những lesson là contest
    @Query("""
        SELECT new com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.ContestShowDTO(
        cl.lessonID, cl.title, cl.description, cl.type,
        cl.duration, cl.image, cl.isPreview, cl.slug, cl.contestStartTime, cl.contestEndTime)
        FROM CourseLesson cl
        WHERE cl.module.id = :moduleID AND cl.isContest = true
    """)
    List<ContestShowDTO> getContestShowDTOByIsContest(@Param("moduleID") Long moduleID);

    boolean existsBySlug(String slug);
}
