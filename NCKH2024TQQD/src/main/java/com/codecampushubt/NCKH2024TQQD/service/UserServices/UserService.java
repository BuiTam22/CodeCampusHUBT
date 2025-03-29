package com.codecampushubt.NCKH2024TQQD.service.UserServices;

import java.util.ArrayList;

import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginBasicDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO;
import com.codecampushubt.NCKH2024TQQD.entity.User;

public interface UserService {
    ArrayList<UserBasicInfoDTO> getUserBasicInfo(Long userID);

    ArrayList<User> findAll();

    LoginBasicDTO getLoginBasicDTO(String userName);
}
