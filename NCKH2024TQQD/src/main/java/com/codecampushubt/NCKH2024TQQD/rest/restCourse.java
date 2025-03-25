    package com.codecampushubt.NCKH2024TQQD.rest;

    import com.codecampushubt.NCKH2024TQQD.entity.*;
    import com.codecampushubt.NCKH2024TQQD.service.CourseService;
    import jakarta.persistence.EntityManager;
    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.ArrayList;
    import java.util.List;

    @RequestMapping("/api/course")
    @RestController()
    public class RestCourse {

        private CourseService courseService;
        private EntityManager entityManager;

        @Autowired
        public RestCourse(CourseService courseService, EntityManager entityManager) {
            this.courseService = courseService;
            this.entityManager = entityManager;
        }

        @GetMapping("/")
        public List<Course> findAll(){
            return courseService.findAll();
        }

        @GetMapping("/findById/{id}")
        public Course findById(@PathVariable("id") long id){
            return courseService.findById(id);
        }

        @PostMapping("/add")
        @Transactional
        public Course addCourse(@RequestBody Course theCourse){
            // thêm Instructor (User) tạo Course
            ArrayList<User> theUser = (ArrayList<User>) entityManager.createQuery("SELECT u FROM User u WHERE u.id = 1", User.class).getResultList();
            theCourse.setInstructor(theUser.get(0));
            Course dbCourse = courseService.save(theCourse);
            return dbCourse;
        }

        @DeleteMapping("/delete/{id}")
        @Transactional
        public void deleteCourse(@PathVariable("id") long theId){
            courseService.deleteByid(theId);
        }
    }
