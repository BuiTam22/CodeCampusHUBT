package com.codecampushubt.NCKH2024TQQD.service.UserServices;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codecampushubt.NCKH2024TQQD.dao.UserRepository;
import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginBasicDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO;
import com.codecampushubt.NCKH2024TQQD.entity.User;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<UserBasicInfoDTO> getUserBasicInfo(Long userID) {
        return userRepository.getUserBasicInfo(userID);
    }

    @Override
    public ArrayList<User> findAll(){
        return (ArrayList<User>) userRepository.findAll();
    }

    @Override
    public LoginBasicDTO getLoginBasicDTO(String userName){
        return (LoginBasicDTO) userRepository.getLoginBasicDTO(userName);
    }
}
