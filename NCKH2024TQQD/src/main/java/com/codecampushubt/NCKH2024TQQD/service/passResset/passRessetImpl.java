package com.codecampushubt.NCKH2024TQQD.service.passResset;

import com.codecampushubt.NCKH2024TQQD.dao.PasswordResetTokenRepository;
import com.codecampushubt.NCKH2024TQQD.dao.UserRepository;
import com.codecampushubt.NCKH2024TQQD.entity.PasswordResetToken;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;

@Service
public class passRessetImpl implements passService {
    private final PasswordResetTokenRepository tokenRepo;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public passRessetImpl(PasswordResetTokenRepository tokenRepo,
                          UserRepository userRepository,
                          JavaMailSender mailSender,
                          PasswordEncoder passwordEncoder) {
        this.tokenRepo = tokenRepo;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void sendOtpToEmail(String email) {
        System.out.println(email);
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) throw new RuntimeException("Email không tồn tại");

        // Kiểm tra nếu đã có token tồn tại thì xóa
        Optional<PasswordResetToken> existingToken = tokenRepo.findByEmail(email);
        existingToken.ifPresent(token -> tokenRepo.delete(token));

        // Tạo OTP mới
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        PasswordResetToken token = new PasswordResetToken();
        token.setEmail(email);
        token.setOtp(otp);
        token.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        tokenRepo.save(token);

        // Gửi mail - đoạn này lồng luôn logic thay vì gọi hàm riêng
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px; background-color: #f5f5f5;'>"
                    + "<h2 style='color: #333;'>Đặt lại mật khẩu</h2>"
                    + "<p>Xin chào,</p>"
                    + "<p>Bạn (hoặc ai đó) đã yêu cầu đặt lại mật khẩu cho tài khoản.</p>"
                    + "<p><strong>Mã OTP của bạn là:</strong></p>"
                    + "<div style='font-size: 28px; font-weight: bold; color: #007bff; padding: 10px 0;'>" + otp + "</div>"
                    + "<p>OTP có hiệu lực trong <strong>10 phút</strong>.</p>"
                    + "<p>Nếu bạn không yêu cầu, hãy bỏ qua email này.</p>"
                    + "<hr style='margin-top: 30px;'/>"
                    + "<p style='font-size: 12px; color: #888;'>CodeHUBT - Hệ thống học tập thông minh</p>"
                    + "</div>";

            helper.setTo(email);
            helper.setSubject("🛡️ Mã OTP Đặt lại mật khẩu");
            helper.setText(htmlContent, true); // gửi HTML

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi gửi email OTP", e);
        }
    }

    @Override
    public String verifyOtp(String email , String otp) {
        PasswordResetToken token = tokenRepo.findByEmail(email)
                .orElseThrow(()-> new RuntimeException(" Không Tìm Thấy Email của bạn "));
        if (!token.getOtp().equals(otp)) {
            return "Otp Không Chính xác ";
        }
        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "Otp Đã Hết Hạn ";
        }

        return "done";
    }



    @Override
    public String resetPassword(String email , String otp , String newPassword) {
        PasswordResetToken token = tokenRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy OTP cho email này."));
        if (!token.getOtp().equals(otp)) {
            throw new RuntimeException("OTP Không Chính Xác");
        }
        if (token.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new RuntimeException("OTP đã hết hạn");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Không tìm thấy Người Dùng "));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepo.delete(token);
        return "done_p";

    }


}
