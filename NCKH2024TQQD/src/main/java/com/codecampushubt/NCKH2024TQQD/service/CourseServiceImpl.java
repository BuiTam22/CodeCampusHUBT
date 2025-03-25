package com.codecampushubt.NCKH2024TQQD.service;

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

    @Autowired
    public CourseServiceImpl(CourseRepository theCourseRepository) {
        this.courseRepository = theCourseRepository;
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
}
