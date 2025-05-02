package com.codecampushubt.NCKH2024TQQD.controller.Client.Contest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contest")
public class ContestController {
    @GetMapping("")
    public String showContest(Model model, HttpServletRequest request){
        model.addAttribute("activePage", request.getRequestURI());
        return  "ClientTemplates/contest/contest";
    }
}
