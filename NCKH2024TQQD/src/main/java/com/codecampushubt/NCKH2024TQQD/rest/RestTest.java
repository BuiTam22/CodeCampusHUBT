package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.entity.ExerciseTestCase;
import com.codecampushubt.NCKH2024TQQD.entity.Permission;
import com.codecampushubt.NCKH2024TQQD.entity.RolePermission;
import com.codecampushubt.NCKH2024TQQD.entity.UserRole;
import com.codecampushubt.NCKH2024TQQD.service.JWTServices.JwtService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/test")
@RestController()
public class RestTest {
    private EntityManager entityManager;
    private JwtService jwtService;

    @Autowired
    public RestTest(EntityManager entityManager, JwtService jwtService) {
        this.entityManager = entityManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/ok")
    public List<UserRole> findList(){
        return entityManager.createQuery("SELECT u FROM UserRole u", UserRole.class).getResultList();
    }

    @GetMapping("/ok1")
    public List<RolePermission> findList1(){
        return entityManager.createQuery("SELECT u FROM RolePermission u", RolePermission.class).getResultList();
    }

    @GetMapping("/ok2")
    public List<ExerciseTestCase> findList2(){
        return entityManager.createQuery("SELECT u FROM ExerciseTestCase u", ExerciseTestCase.class).getResultList();
    }

    @GetMapping("/ok3")
    public String getUsernameformtoken(String theToken){
        theToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTc0MzI2MjM3MSwiZXhwIjoxNzQzMzQ4NzcxfQ.0kQDq6ztgDlgFIAwZvA7kQKgfuoyPg-sZq7saCXUL8s";
       return jwtService.extractUsername(theToken);
    }

    @GetMapping("/ok4")
    public boolean checktoken(String theToken){
        theToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTc0MzI2MTk0OSwiZXhwIjoxNzQzMzQ4MzQ5fQ.e6ueNJflrWZVcaQRri12ctyc1_P5cINc0Kio8o7BWJY";
        return jwtService.validateToken(theToken);
    }
}
