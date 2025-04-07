package com.codecampushubt.NCKH2024TQQD.service.PermissionServices;

import com.codecampushubt.NCKH2024TQQD.dao.PermissionRepository;
import com.codecampushubt.NCKH2024TQQD.dto.PermissionDTO.PermissionNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService{

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository){
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<String> getPermissionNameDTO(String userName) {
        return permissionRepository.getPermissionNameDTO(userName);
    }
}
