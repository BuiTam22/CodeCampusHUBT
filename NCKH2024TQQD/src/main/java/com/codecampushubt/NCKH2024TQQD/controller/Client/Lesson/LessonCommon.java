package com.codecampushubt.NCKH2024TQQD.controller.Client.Lesson;

import com.codecampushubt.NCKH2024TQQD.Constant.Constants;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/practice")
public class LessonCommon {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonCommon.class);
    private final LessonService lessonService;

    @Autowired
    public LessonCommon(LessonService lessonService) {
        this.lessonService = lessonService;
    }


    @GetMapping("")
    public String returnLessonCommon(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String difficulty,
            Model model, HttpServletRequest request){


        List<LessonShowDTO> lessons = new ArrayList<>();

        if(search!=null || status!=null || difficulty!=null){
            // lấy moduleID là 1 cố định vì đây là lấy ra module common chứa các lessoncommon
            // truyền vào Param search
            lessons = lessonService.getLessonShowDTOByModuleIDAndSlug(Constants.ID_MODULE_COMMON, search);
        }else{
            // lấy moduleID là 1 cố định vì đây là lấy ra module common chứa các lessoncommon
            lessons = lessonService.getLessonShowDTO(Constants.ID_MODULE_COMMON);
        }

        LOGGER.info("lessons: {}", lessons);
        model.addAttribute("lessons" , lessons);
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/lesson/show-common";
    }
}
