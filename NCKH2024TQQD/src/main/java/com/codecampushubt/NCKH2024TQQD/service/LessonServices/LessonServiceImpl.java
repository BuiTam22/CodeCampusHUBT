package com.codecampushubt.NCKH2024TQQD.service.LessonServices;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dao.LessonReponsitoryA;
import com.codecampushubt.NCKH2024TQQD.dao.LessonRepository;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.CourseLessonShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService{
    private final LessonRepository lessonRepository;
    private final LessonReponsitoryA lessonReponsitoryA;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository,LessonReponsitoryA lessonReponsitoryA) {
        this.lessonRepository = lessonRepository;
        this.lessonReponsitoryA = lessonReponsitoryA;
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
    public List<LessonShowDTO> getLessonShowDTOByIsContest(Long moduleID) {
        return lessonRepository.getLessonShowDTOByIsContest(moduleID);
    }
    @Override
    public List<CourseLessonShowDTO> getAllCourseLessonShowDTOonroleNameORid() {
        String userName = UserContext.getUsername();
        System.out.println("tên của bạn là "+userName);
        List<String> rolename = lessonReponsitoryA.findRoleNameByUserName(userName);
        System.out.println("quyền của bạn là "+rolename);
        Long userid = lessonReponsitoryA.finduseridByUsername(userName);
        System.out.println("id của bạn là "+userid);
        if (rolename.contains("ADMIN")){
            return lessonReponsitoryA.findLessonByRoleName("ADMIN");

        }else {
            return lessonReponsitoryA.findLessonByInstructorId(userid);
        }


    }
}
