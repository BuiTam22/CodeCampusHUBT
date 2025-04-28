package com.codecampushubt.NCKH2024TQQD.controller.Admin.Role;

import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.PermissionAssignDTO;
import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.UpdatePermissionsDTO;
import com.codecampushubt.NCKH2024TQQD.service.PermissionServices.PermissionService;
import com.codecampushubt.NCKH2024TQQD.service.RolePermissionsSecvice.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/role")
public class RoleAPIController {
    private final PermissionService permissionService;
    public RoleAPIController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("/show")
    public ResponseEntity<List<PermissionAssignDTO>> getPermissionsByRole() {
        List<PermissionAssignDTO> permissions = rolePermissionService.getAllRolePermissions();
        return ResponseEntity.ok(permissions);
    }
    @PostMapping("/add")
    public ResponseEntity<String> assignPermission(@RequestBody List<PermissionAssignDTO> dto) {
        for (PermissionAssignDTO dtos : dto) {
            permissionService.assignPermission(dtos);
            System.out.println(dtos);
        }
        return ResponseEntity.ok("Permission assigned successfully.");
    }
}
