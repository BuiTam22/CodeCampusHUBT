package com.codecampushubt.NCKH2024TQQD.controller.Admin.Role;

import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.PermissionAssignDTO;
import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.UpdatePermissionsDTO;
import com.codecampushubt.NCKH2024TQQD.service.PermissionServices.PermissionService;
import com.codecampushubt.NCKH2024TQQD.service.RolePermissionsSecvice.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/role")
public class RoleAPIController {
    private final PermissionService permissionService;
    private final RolePermissionService rolePermissionService;

    public RoleAPIController(PermissionService permissionService, RolePermissionService rolePermissionService) {
        this.permissionService = permissionService;
        this.rolePermissionService = rolePermissionService;
    }


    @GetMapping("/show")
    public ResponseEntity<List<PermissionAssignDTO>> getPermissionsByRole() {
        List<PermissionAssignDTO> permissions = rolePermissionService.getAllRolePermissions();
        return ResponseEntity.ok(permissions);
    }

    @PostMapping("/permissionsAdd")
    public ResponseEntity<?> createRolePermission(@RequestBody PermissionAssignDTO dto) {
        try {
            System.out.println("controller"+dto);

            rolePermissionService.createRolePermissions(dto.getRoleName(), dto.getPermissionName());
            return ResponseEntity.ok("Role Permission created successfully!");

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }


    }
}
