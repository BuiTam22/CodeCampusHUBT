package com.codecampushubt.NCKH2024TQQD.dao;

import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.CourseLessonShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonReponsitoryA  extends JpaRepository<CourseLesson ,Long>{

//    lấy rolename dựa vào userName
    @Query("SELECT r.roleName FROM User u " +
            "JOIN u.userRoles ur " +
            "JOIN ur.role r " +
            "WHERE u.userName = :userName ")
    List<String> findRoleNameByUserName(String userName);
//    lấy leson dựa vào rolename nếu là admin
    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.CourseLessonShowDTO( " +
            "cl.title, cl.description, cl.type, cl.content, cl.duration, r.roleName , u.userName) " +
            "FROM CourseLesson cl " +
            "JOIN cl.module cm " +
            "JOIN cm.course c " +
            "JOIN c.instructor u " +
            "JOIN u.userRoles ur " +
            "JOIN ur.role r " +
            "WHERE r.roleName = :roleName")
    List<CourseLessonShowDTO> findLessonByRoleName(@Param("roleName") String roleName);

//lấy lesson dựa vào userid nếu rolename không phải là admin
    @Query("SELECT new com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.CourseLessonShowDTO( " +
            "cl.title, cl.description, cl.type, cl.content, cl.duration, r.roleName , u.userName) " +
            "FROM CourseLesson cl " +
            "JOIN cl.module cm " +
            "JOIN cm.course c " +
            "JOIN c.instructor u " +
            "JOIN u.userRoles ur " +
            "JOIN ur.role r " +
            "WHERE u.userID = :userId")
    List<CourseLessonShowDTO> findLessonByInstructorId(@Param("userId") Long userId);

//    Lấy userid dựa vào username
    @Query("SELECT u.userID FROM User u where u.userName = :userName")
    Long finduseridByUsername(String userName);






}

