package com.codecampushubt.NCKH2024TQQD.service.UserServices;

import java.util.ArrayList;
import java.util.List;

import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginBasicDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserCreateDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserUpdateDTO;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import org.springframework.data.repository.query.Param;

public interface UserService {
    ArrayList<UserBasicInfoDTO> getUserBasicInfo(Long userID);

    ArrayList<User> findAll();

    LoginBasicDTO getLoginBasicDTO(String userName);

    List<UserShowDTO> getAllUsers();

    User addUser(UserCreateDTO dto);

    UserUpdateDTO  getUserUpdateDTOById(Long userID);



    String getFullName(String userName);

}
