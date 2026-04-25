package com.codecampushubt.NCKH2024TQQD.dto.UserDTO;

import java.time.LocalDateTime;

public class UserProfileDTO {
    private String userName;
    private String fullName;
    private String email;
    private String school;
    private String bio;
    private String image;
    private LocalDateTime createdAt;

    public UserProfileDTO(String userName, String fullName, String email, String school, String bio, String image, LocalDateTime createdAt) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.school = school;
        this.bio = bio;
        this.image = image;
        this.createdAt = createdAt;
    }

    public String getUserName() { return userName; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getSchool() { return school; }
    public String getBio() { return bio; }
    public String getImage() { return image; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
