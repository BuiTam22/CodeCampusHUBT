package com.codecampushubt.NCKH2024TQQD.controller.Client.Problem;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/problem")
public class ProblemShow {
//    @GetMapping("/{slug}")
    @GetMapping("")
    public  String showProblem(Model model, HttpServletRequest request){
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/problem/problem";
    }
    
}
