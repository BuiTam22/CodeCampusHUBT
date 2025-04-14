package com.codecampushubt.NCKH2024TQQD.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserShowDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginBasicDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO;
import com.codecampushubt.NCKH2024TQQD.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    ArrayList<User> findByEmailAndUserName(String email, String userName);

    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO(u.userName, u.fullName, u.image) FROM User u WHERE u.userID = :userID")
    ArrayList<UserBasicInfoDTO> getUserBasicInfo(@Param("userID") Long userID);

    // truy vấn 3 trường để so sánh login
    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginBasicDTO(u.userName, u.email, u.password) FROM User u WHERE u.userName = :userName")
    LoginBasicDTO getLoginBasicDTO(@Param("userName") String userName);

//    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserShowDTO(u.userID, u.userName, u.email, r.roleName) " +
//            "FROM User u " +
//            "join  UserRole ur on u.userID = ur.user.userID" +
//            "join Roles r ")
//    List<UserShowDTO> getAllUsers();
//    List<String>
    boolean existsByEmail(String email);
    Optional<User> findByUserName(String userName);


}
