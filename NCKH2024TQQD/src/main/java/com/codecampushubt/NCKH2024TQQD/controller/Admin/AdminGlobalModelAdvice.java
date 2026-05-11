package com.codecampushubt.NCKH2024TQQD.controller.Admin;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

/**
 * Automatically injects the current username into the model
 * for all Admin controllers, so sidebar/header fragments can use ${username}.
 */
@ControllerAdvice(basePackages = "com.codecampushubt.NCKH2024TQQD.controller.Admin")
public class AdminGlobalModelAdvice {

    @ModelAttribute
    public void addUsername(Model model) {
        String username = UserContext.getUsername();
        model.addAttribute("username", username != null ? username : "Admin");
    }
}
