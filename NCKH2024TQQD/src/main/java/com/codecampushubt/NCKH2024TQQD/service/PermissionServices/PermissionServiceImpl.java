package com.codecampushubt.NCKH2024TQQD.service.PermissionServices;

import com.codecampushubt.NCKH2024TQQD.dao.PermissionRepository;
import com.codecampushubt.NCKH2024TQQD.dao.RolePermissionRepository;
import com.codecampushubt.NCKH2024TQQD.dao.RoleRepository;
import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.PermissionAssignDTO;
import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.PermissionNameDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Permission;
import com.codecampushubt.NCKH2024TQQD.entity.Role;
import com.codecampushubt.NCKH2024TQQD.entity.RolePermission;
import com.codecampushubt.NCKH2024TQQD.entity.RolePermissionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService{
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository,RoleRepository roleRepository,RolePermissionRepository rolePermissionRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public List<String> getPermissionNameDTO(String userName) {
        return permissionRepository.getPermissionNameDTO(userName);
    }

    @Override
    public void assignPermission(PermissionAssignDTO dto) {
        String roleName = dto.getRoleName().toUpperCase();

        if (!roleName.equals("ADMIN") && !roleName.equals("STUDENT") && !roleName.equals("TEACHER")) {
            throw new IllegalArgumentException("Invalid role name: " + roleName);
        }

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with name: " + roleName));
        Integer roleId = role.getRoleID();

        Permission permission = permissionRepository.findByPermissionName(dto.getPermissionName())
                .orElseGet(() -> {
                    Permission newPermission = new Permission();
                    newPermission.setPermissionName(dto.getPermissionName());
                    return permissionRepository.save(newPermission);
                });
        Integer permissionId = permission.getPermissionID();

        RolePermissionId rolePermissionId = new RolePermissionId(roleId, permissionId);

        if (!rolePermissionRepository.existsById(rolePermissionId)) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setId(rolePermissionId);
            rolePermissionRepository.save(rolePermission);
        } else {
            throw new IllegalStateException("Permission already assigned to role.");
        }
    }











}
