package com.codecampushubt.NCKH2024TQQD.service.CourseServices;

import com.codecampushubt.NCKH2024TQQD.dao.CourseModuleRepository;
import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseModuleDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Course;
import com.codecampushubt.NCKH2024TQQD.dao.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseModuleRepository courseModuleRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository theCourseRepository, CourseModuleRepository courseModuleRepository) {
        this.courseRepository = theCourseRepository;
        this.courseModuleRepository = courseModuleRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(long theId) {
        Optional<Course> result = courseRepository.findById(theId);
        return result.orElseThrow(() -> new RuntimeException("Course not found with id = " + theId));
    }

    @Override
    @Transactional
    public Course save(Course theCourse) {
        return courseRepository.save(theCourse);
    }

    @Override
    @Transactional
    public void deleteByid(long theId) {
        courseRepository.deleteById((long) theId);
    }

    @Override
    public List<CourseShowDTO> getCourseShowDTO() {
        return courseRepository.getCourseShowDTO();
    }

    @Override
    public List<CourseModuleDTO> getCourseModuleByCourseSlug(String theSlug) {
        return courseModuleRepository.getCourseModuleByCourseSlug(theSlug);
    }
}
