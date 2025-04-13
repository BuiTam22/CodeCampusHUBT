package com.codecampushubt.NCKH2024TQQD.controller.Client.Lesson;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/practice")
public class LessonCommon {

    @GetMapping("/show")
    public String returnLessonCommon(){
        return "ClientTemplates/lesson/show-common";
    }
}
