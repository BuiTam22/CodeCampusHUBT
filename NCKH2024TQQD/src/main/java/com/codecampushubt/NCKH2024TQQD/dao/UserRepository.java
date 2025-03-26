package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO;
import com.codecampushubt.NCKH2024TQQD.entity.User;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    ArrayList<User> findByEmailAndUserName(String email, String userName);

    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO(u.userName, u.fullName, u.image) FROM User u WHERE u.userID = :userID")
    ArrayList<UserBasicInfoDTO> getUserBasicInfo(@Param("userID") Long userID);
}
