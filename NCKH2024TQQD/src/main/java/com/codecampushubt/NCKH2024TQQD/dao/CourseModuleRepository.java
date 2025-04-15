package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseModuleDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {

    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseModuleDTO(cm.id, cm.title, cm.description, cm.orderIndex, cm.duration, cm.isPublished) " +
            "FROM CourseModule cm " +
            "WHERE cm.course.slug = :slug")
    List<CourseModuleDTO> getCourseModuleByCourseSlug(@Param("slug") String theSlug);

    boolean existsBySlug(String slug);

}
