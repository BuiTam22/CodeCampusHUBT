package com.codecampushubt.NCKH2024TQQD.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController()
public class RestUser {
    @GetMapping("/hello")
    public String hello(){
        return "ok";
    }
}
