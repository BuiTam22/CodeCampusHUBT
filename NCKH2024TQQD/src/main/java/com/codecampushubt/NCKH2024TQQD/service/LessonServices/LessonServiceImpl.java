package com.codecampushubt.NCKH2024TQQD.service.LessonServices;

import com.codecampushubt.NCKH2024TQQD.Constant.Constant;
import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dao.CourseModuleRepository;
import com.codecampushubt.NCKH2024TQQD.dao.CourseRepository;
import com.codecampushubt.NCKH2024TQQD.dao.LessonRepository;
import com.codecampushubt.NCKH2024TQQD.dao.UserRepository;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.*;
import com.codecampushubt.NCKH2024TQQD.entity.Course;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;
import com.codecampushubt.NCKH2024TQQD.entity.CourseModule;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService{
    private final LessonRepository lessonRepository;
    private final CourseModuleRepository courseModuleRepository;
    private final UserRepository userRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository , CourseModuleRepository courseModuleRepository, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.courseModuleRepository = courseModuleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<LessonShowDTO> getLessonShowDTO(Long theID) {
        return lessonRepository.getLessonShowDTO(theID);
    }

    @Override
    public List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(Long moduleID, String search) {
        String processed = search.replace("++", "-plus-plus");
        String newSlug = new Slugify().slugify(processed);
        return lessonRepository.getLessonShowDTOByModuleIDAndSlug(moduleID, newSlug);
    }

    @Override
    public List<ContestShowDTO> getContestShowDTOByIsContest(Long moduleID) {
        return lessonRepository.getContestShowDTOByIsContest(moduleID);
    }

    @Override
    public List<ContestShowDTO> getEssayContestShowDTOByIsContest(Long moduleID) {
        return lessonRepository.getEssayContestShowDTOByIsContest(Constant.ID_MODULE_COMMON);
    }

    @Override
    @Transactional
    public CourseLesson save(CourseLesson theLesson){
        String baseSlug = new Slugify().slugify(theLesson.getTitle());
        String uniqueSlug = generateUniqueSlug(baseSlug);
        theLesson.setSlug(uniqueSlug);
        return lessonRepository.save(theLesson);
    }

    @Override
    public EditLessonDTO getEditLessonDTO(Long moduleID, String theSlug) {
        return lessonRepository.getEditLessonDTO(moduleID, theSlug);
    }

    @Override
    @Transactional
    public CourseLesson updateContestLesson(UpdateLessonClientDTO dto) {
        CourseLesson lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        User current = userRepository.findByUserName(UserContext.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!lesson.getCreator().getUserId().equals(current.getUserId())) {
            throw new RuntimeException("Forbidden");
        }
        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setDuration(dto.getDuration());
        lesson.setType(dto.getType());
        lesson.setIsContest(dto.getIsContest());
        lesson.setContestStartTime(dto.getContestStartTime());
        lesson.setContestEndTime(dto.getContestEndTime());
        return lessonRepository.save(lesson);
    }

    @Override
    public Optional<CourseLesson> findById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public Long findLessonIdBySlug(String slug) {
        return lessonRepository.findLessonIdBySlug(slug);
    }

    public String generateUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;
        while (lessonRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }

    @Override
    public List<LessonShowDTOA> getLessonShowDTOA(){
        String userName = UserContext.getUsername();
        List<String> roleName = lessonRepository.findRoleNameByUserName(userName);
        Long UserID = lessonRepository.findUserIdByUsername(userName);
        if (roleName.contains("ADMIN")){
            return lessonRepository.findLessonByRoleName("ADMIN");

        }else {
            return lessonRepository.findLessonByUserID(UserID);
        }



    }

    @Override
    public CourseLesson addLesson(CreateLessonsDTO dto ){
//        System.out.println(dto);
        Slugify slugify = new Slugify();
        String Slug = slugify.slugify(dto.getTitle());
//        System.out.println(dto.getCourseName());
        String courseName = dto.getCourseName();
        CourseModule module = courseModuleRepository.findBySlug(courseName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy module"));
//        System.out.println(module);
//        khởi tạo
        CourseLesson courseLesson = new CourseLesson();
        courseLesson.setModule(module);
        courseLesson.setTitle(dto.getTitle());
        courseLesson.setDescription(dto.getDescription());
        courseLesson.setType(dto.getType());
        courseLesson.setContent(dto.getContent());
        courseLesson.setImage(dto.getImage());
        courseLesson.setDuration(dto.getDuration());
        courseLesson.setSlug(Slug);
        courseLesson.setOrderIndex(dto.getOrderIndex());
        Optional<User> user = userRepository.findByUserName(UserContext.getUsername());
        courseLesson.setCreator(user.get());
        courseLesson.setOrderIndex(1);

        lessonRepository.save(courseLesson);

        return courseLesson;
    }

    @Override
    public List<ContestManagementShowDTO> getContestManagementShowDTO(Long moduleID, String userName) {
        return lessonRepository.getContestManagementShowDTO(moduleID, userName);
    }

    @Override
    public List<HomeLessonDTO> getTopLessonsForHome(int limit) {
        return lessonRepository.findTopLessonsByOrderIndex(PageRequest.of(0, limit));
    }

}
