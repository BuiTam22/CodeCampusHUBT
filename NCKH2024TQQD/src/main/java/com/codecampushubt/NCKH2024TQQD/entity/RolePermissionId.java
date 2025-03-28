package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class RolePermissionId {
    @Column(name = "role_id")  // Đúng với tên cột trong bảng RolePermission
    private Integer roleID;

    @Column(name = "permission_id")  // Đúng với tên cột trong bảng RolePermission
    private Integer permissionID;

    public RolePermissionId() {
    }

    public RolePermissionId(Integer roleID, Integer permissionID) {
        this.roleID = roleID;
        this.permissionID = permissionID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(Integer permissionID) {
        this.permissionID = permissionID;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionId that = (RolePermissionId) o;
        return Objects.equals(roleID, that.roleID) && Objects.equals(permissionID, that.permissionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleID, permissionID);
    }
}
