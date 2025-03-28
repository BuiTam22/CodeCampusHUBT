package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.entity.Permission;
import com.codecampushubt.NCKH2024TQQD.entity.RolePermission;
import com.codecampushubt.NCKH2024TQQD.entity.UserRole;
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

    @Autowired
    public RestTest(EntityManager entityManager) {
        this.entityManager = entityManager;
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
    public List<Permission> findList2(){
        return entityManager.createQuery("SELECT u FROM Permission u", Permission.class).getResultList();
    }
}
