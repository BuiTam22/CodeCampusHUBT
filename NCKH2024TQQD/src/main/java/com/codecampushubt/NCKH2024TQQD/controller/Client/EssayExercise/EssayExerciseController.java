package com.codecampushubt.NCKH2024TQQD.controller.Client.EssayExercise;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dao.EssaySubmissionRepository;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.ExerciseSubmissionDTO;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseDetailShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.EssayExerciseServices.EssayExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/practice/lesson/type-essay")
public class EssayExerciseController {

    private static final String BASE_PATH = "/practice/lesson/type-essay";

    private final EssayExerciseService essayExerciseService;
    private final EssaySubmissionRepository essaySubmissionRepository;

    @Autowired
    public EssayExerciseController(EssayExerciseService essayExerciseService,
                                   EssaySubmissionRepository essaySubmissionRepository) {
        this.essayExerciseService = essayExerciseService;
        this.essaySubmissionRepository = essaySubmissionRepository;
    }

    @GetMapping("/{lesson-slug}")
    public String showEssayListByLessonSlug(@PathVariable("lesson-slug") String theSlug, Model model) {
        model.addAttribute("activePage", "/contest");
        return "ClientTemplates/essay-exercise/show";
    }

    /** Trang làm bài essay */
    @GetMapping("/problem/{slug}")
    public String showEssayProblemBySlug(@PathVariable("slug") String theSlug, Model model) {
        EssayExerciseDetailShowDTO essayExercise =
                essayExerciseService.getEssayExerciseDetailShowDTOBySlug(theSlug);

        model.addAttribute("essayExercise", essayExercise);
        model.addAttribute("slug", theSlug);
        model.addAttribute("basePath", BASE_PATH);
        model.addAttribute("activePage", "/contest");   // ← highlight Cuộc thi
        return "ClientTemplates/essay-exercise/problem";
    }

    /** Trang lịch sử nộp bài của user cho một essay exercise */
    @GetMapping("/submissions/{slug}")
    public String showEssaySubmissions(@PathVariable("slug") String theSlug, Model model) {
        String username = UserContext.getUsername();
        List<ExerciseSubmissionDTO> submissions =
                essaySubmissionRepository.getSubmissionsByUserAndExerciseSlug(username, theSlug);

        model.addAttribute("submissions", submissions);
        model.addAttribute("slug", theSlug);
        model.addAttribute("basePath", BASE_PATH);
        model.addAttribute("activePage", "/contest");   // ← highlight Cuộc thi
        return "ClientTemplates/essay-exercise/submissions";
    }
}
