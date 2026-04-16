package com.codecampushubt.NCKH2024TQQD.controller.Client.Blog;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @GetMapping
    public String showHome(Model model, HttpServletRequest request){
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/blog/blog";
    }
}
