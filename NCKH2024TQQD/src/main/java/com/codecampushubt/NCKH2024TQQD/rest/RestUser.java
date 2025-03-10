package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.entity.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequestMapping("/api")
@RestController()
public class RestUser {
    private EntityManager entityManager;

    @Autowired
    public RestUser(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @GetMapping("/hello")
    public ArrayList<Comments> hello() {
        return (ArrayList<Comments>) entityManager.createQuery("SELECT u FROM Comments u", Comments.class).getResultList();
    }

}
