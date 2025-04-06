package com.codecampushubt.NCKH2024TQQD.controller.Client.Problem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/problem")
public class ProblemShow {
//    @GetMapping("/{slug}")
    @GetMapping("")
    public  String showProblem(){
        return "Client/problem/problem";
    }
    
}
