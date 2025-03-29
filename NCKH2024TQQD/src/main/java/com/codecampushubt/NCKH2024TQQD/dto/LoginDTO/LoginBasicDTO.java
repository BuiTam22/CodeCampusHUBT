package com.codecampushubt.NCKH2024TQQD.dto.LoginDTO;

public class LoginBasicDTO {
    private String userName;
    private String email;
    private String password;

    public LoginBasicDTO(String userName, String email, String password  ) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
