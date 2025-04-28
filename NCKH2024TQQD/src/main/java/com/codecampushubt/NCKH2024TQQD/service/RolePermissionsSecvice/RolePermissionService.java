package com.codecampushubt.NCKH2024TQQD.service.RolePermissionsSecvice;

import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.PermissionAssignDTO;

import java.util.List;

public interface RolePermissionService {
    List<PermissionAssignDTO> getAllRolePermissions();
}
