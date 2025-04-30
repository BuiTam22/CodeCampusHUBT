package com.codecampushubt.NCKH2024TQQD.service.RolePermissionsSecvice;

import com.codecampushubt.NCKH2024TQQD.dao.PermissionRepository;
import com.codecampushubt.NCKH2024TQQD.dao.RolePermissionRepository;
import com.codecampushubt.NCKH2024TQQD.dao.RoleRepository;
import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.PermissionAssignDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Permission;
import com.codecampushubt.NCKH2024TQQD.entity.Role;
import com.codecampushubt.NCKH2024TQQD.entity.RolePermission;
import com.codecampushubt.NCKH2024TQQD.entity.RolePermissionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @Override
    public void createRolePermissions(String roleName, String permissionName) {
            Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
            System.out.println(role.getRoleID());
            Permission permission = permissionRepository.findByPermissionName(permissionName)
                    .orElseGet(() ->{
                        Permission newpermission = new Permission(permissionName);
                        return permissionRepository.save(newpermission);
                    });
            System.out.println(permission.getPermissionID());

            RolePermissionId id = new RolePermissionId(role.getRoleID(),permission.getPermissionID());
            if (rolePermissionRepository.existsById(id)) {
                throw new RuntimeException("Role permission already exists");
            }
            RolePermission rolePermission = new RolePermission();
            rolePermission.setId(id);
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
            rolePermissionRepository.save(rolePermission);
    }

    public void deleteRolePermissions(String roleName,String permissionName) {
        System.out.println(roleName+" "+permissionName);
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        System.out.println(role.getRoleID());
        Permission permission = permissionRepository.findByPermissionName(permissionName).orElseThrow(() -> new RuntimeException("Permission not found"));
        System.out.println(permission.getPermissionID());
        RolePermissionId id = new RolePermissionId(role.getRoleID(),permission.getPermissionID());
        rolePermissionRepository.deleteById(id);


    }


}
