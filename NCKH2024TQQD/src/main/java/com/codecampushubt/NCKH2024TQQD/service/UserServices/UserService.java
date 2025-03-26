package com.codecampushubt.NCKH2024TQQD.service.UserServices;

import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO;


import java.util.ArrayList;

public interface UserService {
    ArrayList<UserBasicInfoDTO> getUserBasicInfo(Long userID);
}
