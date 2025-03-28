package com.codecampushubt.NCKH2024TQQD.service.UserServices;

import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO;
import com.codecampushubt.NCKH2024TQQD.entity.User;


import java.util.ArrayList;

public interface UserService {
    ArrayList<UserBasicInfoDTO> getUserBasicInfo(Long userID);

    ArrayList<User> findAll();
}
