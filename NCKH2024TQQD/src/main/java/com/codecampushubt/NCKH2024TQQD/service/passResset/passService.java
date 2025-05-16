package com.codecampushubt.NCKH2024TQQD.service.passResset;

import org.springframework.stereotype.Service;


public interface passService {
    void sendOtpToEmail(String email);
    void resetPassword(String email, String otp, String newPassword);
    String verifyOtp(String email, String otp);

}
