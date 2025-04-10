package com.codecampushubt.NCKH2024TQQD.service.CourseServices;

import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course findById(long theId);
    Course save(Course theCourse);
    void deleteByid(long theId);
    List<CourseShowDTO> getCourseShowDTO();
}
