package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.entity.Notification;
import com.codecampushubt.NCKH2024TQQD.entity.NotificationDelivery;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import com.codecampushubt.NCKH2024TQQD.entity.UserRanking;
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
    public ArrayList<User> hello() {
        return (ArrayList<User>) entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

}
