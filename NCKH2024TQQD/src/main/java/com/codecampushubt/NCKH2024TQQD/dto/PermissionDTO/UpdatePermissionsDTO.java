package com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO;

import com.codecampushubt.NCKH2024TQQD.entity.Message;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UpdatePermissionsDTO {
   @NotBlank(message = "Không Được Để Trống ")
    private String roleName;
   @NotBlank(message = "Không Được Để Trống ")
    private List<String> permissionName;
   public UpdatePermissionsDTO() {}

    public UpdatePermissionsDTO(String roleName, List<String> permissionName) {
        this.roleName = roleName;
        this.permissionName = permissionName;
    }

    public @NotBlank(message = "Không Được Để Trống ") String getRoleName() {
        return roleName;
    }

    public void setRoleName(@NotBlank(message = "Không Được Để Trống ") String roleName) {
        this.roleName = roleName;
    }

    public @NotBlank(message = "Không Được Để Trống ") List<String> getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(@NotBlank(message = "Không Được Để Trống ") List<String> permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "UpdatePermissionsDTO{" +
                "roleName='" + roleName + '\'' +
                ", permissionName=" + permissionName +
                '}';
    }
}
