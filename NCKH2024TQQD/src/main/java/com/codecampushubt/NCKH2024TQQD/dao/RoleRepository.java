package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
}
