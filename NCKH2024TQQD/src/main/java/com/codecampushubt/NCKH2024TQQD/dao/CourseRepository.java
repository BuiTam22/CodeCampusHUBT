package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // khong can CRUD co ban nua
    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowDTO(" +
            "c.courseID, c.title, c.slug, c.description, c.shortDescription, " +
            "c.instructor.userName, c.rating, c.price, c.discountPrice, c.imageUrl) " +
            "FROM Course c")
    List<CourseShowDTO> getCourseShowDTO();

}
