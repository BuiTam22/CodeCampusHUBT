package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.entity.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ArrayList<EssayAnswerAnalysis> hello() {

        return (ArrayList<EssayAnswerAnalysis>) entityManager.createQuery("SELECT u FROM Exam u", EssayAnswerAnalysis.class).getResultList();
    }

    @PostMapping("/hello1")
    public void hello1(@RequestBody UserProfile theUser){

    }

}
