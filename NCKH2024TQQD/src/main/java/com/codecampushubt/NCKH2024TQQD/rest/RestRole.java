package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.entity.Role;
import com.codecampushubt.NCKH2024TQQD.service.RoleService.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/role")
@RestController()
public class RestRole {
    private RoleService roleService;

    @Autowired
    public RestRole(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/find-all")
    public List<Role> findAll(){
        return roleService.findAll();
    }
}
