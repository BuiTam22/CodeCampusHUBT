package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.entity.*;
import com.codecampushubt.NCKH2024TQQD.service.CourseService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/user")
@RestController()
public class RestUser {
    private EntityManager entityManager;

    @Autowired
    public RestUser(EntityManager entityManager) {
        this.entityManager = entityManager;

    }


    @GetMapping("/hello")
    public ArrayList<User> hello() {

        return (ArrayList<User>) entityManager.createQuery("SELECT u FROM User u WHERE u.id = 1", User.class).getResultList();
    }


}
