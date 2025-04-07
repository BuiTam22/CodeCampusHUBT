package com.codecampushubt.NCKH2024TQQD.service.PermissionServices;

import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.PermissionNameDTO;


import java.util.List;

public interface PermissionService {
    List<String> getPermissionNameDTO(String userName);
}
