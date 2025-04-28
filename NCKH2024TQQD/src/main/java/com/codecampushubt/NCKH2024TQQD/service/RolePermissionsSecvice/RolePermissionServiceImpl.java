package com.codecampushubt.NCKH2024TQQD.service.RolePermissionsSecvice;

import com.codecampushubt.NCKH2024TQQD.dao.PermissionRepository;
import com.codecampushubt.NCKH2024TQQD.dao.RolePermissionRepository;
import com.codecampushubt.NCKH2024TQQD.dao.RoleRepository;
import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.PermissionAssignDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Permission;
import com.codecampushubt.NCKH2024TQQD.entity.Role;
import com.codecampushubt.NCKH2024TQQD.entity.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl implements RolePermissionService{
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public List<PermissionAssignDTO> getAllRolePermissions() {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();

        return rolePermissions.stream().map(rp -> {
            Role role = roleRepository.findById(rp.getRole().getRoleID())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            Permission permission = permissionRepository.findById(rp.getPermission().getPermissionID())
                    .orElseThrow(() -> new RuntimeException("Permission not found"));
            return new PermissionAssignDTO(role.getRoleName(), permission.getPermissionName());
        }).collect(Collectors.toList());
    }
}
