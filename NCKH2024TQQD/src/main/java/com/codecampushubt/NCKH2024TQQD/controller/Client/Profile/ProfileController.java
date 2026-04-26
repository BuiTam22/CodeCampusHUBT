package com.codecampushubt.NCKH2024TQQD.controller.Client.Profile;

import com.codecampushubt.NCKH2024TQQD.dao.CodingSubmissionRepository;
import com.codecampushubt.NCKH2024TQQD.dao.EssaySubmissionRepository;
import com.codecampushubt.NCKH2024TQQD.dao.UserRepository;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.LessonProgressDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserProfileDTO;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final CodingSubmissionRepository codingSubmissionRepository;
    private final EssaySubmissionRepository essaySubmissionRepository;

    @Autowired
    public ProfileController(UserRepository userRepository,
                             CodingSubmissionRepository codingSubmissionRepository,
                             EssaySubmissionRepository essaySubmissionRepository) {
        this.userRepository = userRepository;
        this.codingSubmissionRepository = codingSubmissionRepository;
        this.essaySubmissionRepository = essaySubmissionRepository;
    }

    @GetMapping("/profile/{username}")
    public String showProfile(@PathVariable("username") String username,
                              Model model, HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findByUserName(username);
        if (userOptional.isEmpty()) {
            return "error/404";
        }

        User user = userOptional.get();
        UserProfileDTO profile = new UserProfileDTO(
                user.getuserName(),
                user.getFullName(),
                user.getEmail(),
                user.getSchool(),
                user.getBio(),
                user.getImage(),
                user.getCreatedAt()
        );

        // Lấy lesson progress từ coding + essay submissions
        List<LessonProgressDTO> codingProgress = codingSubmissionRepository.getLessonProgressByUserName(username);
        List<LessonProgressDTO> essayProgress = essaySubmissionRepository.getLessonProgressByUserName(username);

        // Merge: dùng lessonSlug làm key để tránh trùng
        Map<String, LessonProgressDTO> progressMap = new LinkedHashMap<>();
        for (LessonProgressDTO lp : codingProgress) {
            progressMap.put(lp.getLessonSlug(), lp);
        }
        for (LessonProgressDTO lp : essayProgress) {
            progressMap.put(lp.getLessonSlug(), lp);
        }

        List<LessonProgressDTO> allProgress = new ArrayList<>(progressMap.values());

        // Tính tổng thống kê
        double totalScore = allProgress.stream()
                .mapToDouble(LessonProgressDTO::getTotalScore).sum();
        long totalCompleted = allProgress.stream()
                .mapToLong(LessonProgressDTO::getCompletedCount).sum();

        model.addAttribute("profile", profile);
        model.addAttribute("lessonProgress", allProgress);
        model.addAttribute("totalScore", totalScore);
        model.addAttribute("totalCompleted", totalCompleted);
        model.addAttribute("activePage", request.getRequestURI());

        return "ClientTemplates/profile/profile";
    }
}
