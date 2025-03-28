package com.codecampushubt.NCKH2024TQQD.service.RoleService;


import com.codecampushubt.NCKH2024TQQD.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();
    Optional<Role> findById(int theId);
    Role save(Role theRole);
    void deleteByid(int theId);
}
