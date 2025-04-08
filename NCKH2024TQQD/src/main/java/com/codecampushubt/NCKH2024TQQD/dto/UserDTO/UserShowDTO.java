package com.codecampushubt.NCKH2024TQQD.dto.UserDTO;

public class UserShowDTO {
    private Long UserID ;
    private String UserName ;
    private String Email ;
    private String UserRole ;

    public UserShowDTO(Long userID, String userName, String email, String userRole) {
        UserID = userID;
        UserName = userName;
        Email = email;
        UserRole = userRole;
    }

    public Long getUserID() {
        return UserID;
    }

    public void setUserID(Long userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }
}
