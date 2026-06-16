package com.codecampushubt.NCKH2024TQQD.controller.Client.Management;

import com.codecampushubt.NCKH2024TQQD.Constant.Constants;
import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseListShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.ContestManagementShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.EditLessonDTO;
import com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.LessonSubmissionDTO;
import com.codecampushubt.NCKH2024TQQD.entity.CourseLesson;
import com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices.CodingExerciseService;
import com.codecampushubt.NCKH2024TQQD.service.CodingSubmissionServices.CodingSubmissionService;
import com.codecampushubt.NCKH2024TQQD.service.EssayExerciseServices.EssayExerciseService;
import com.codecampushubt.NCKH2024TQQD.service.EssaySubmissionServices.EssaySubmissionService;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import com.codecampushubt.NCKH2024TQQD.service.LessonSubmissionServices.LessonSubmissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.codecampushubt.NCKH2024TQQD.dto.SubmissionDTO.EssayScoreDetailDTO;

@Controller
@RequestMapping("/management")
public class ManagementController {
    private final LessonService lessonService;
    private final CodingSubmissionService codingSubmissionService;
    private final LessonSubmissionService lessonSubmissionService;
    private final CodingExerciseService codingExerciseService;
    private final EssayExerciseService essayExerciseService;
    private final EssaySubmissionService essaySubmissionService;

    @Autowired
    public ManagementController(LessonService lessonService,
                                CodingSubmissionService codingSubmissionService,
                                LessonSubmissionService lessonSubmissionService,
                                CodingExerciseService codingExerciseService,
                                EssayExerciseService essayExerciseService,
                                EssaySubmissionService essaySubmissionService) {
        this.lessonService = lessonService;
        this.codingSubmissionService = codingSubmissionService;
        this.lessonSubmissionService = lessonSubmissionService;
        this.codingExerciseService = codingExerciseService;
        this.essayExerciseService = essayExerciseService;
        this.essaySubmissionService = essaySubmissionService;
    }


    @GetMapping("/contest")
    public String showCourse(Model model, HttpServletRequest request){
        List<ContestManagementShowDTO> contests = lessonService.getContestManagementShowDTO(Constants.ID_MODULE_COMMON, UserContext.getUsername());
        model.addAttribute("contests",contests);
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/management/contest";
    }

    @GetMapping("/contest/challenge")
    public String showChallenge(Model model, HttpServletRequest request){
        List<ContestManagementShowDTO> contests = lessonService.getContestManagementShowDTO(Constants.ID_MODULE_COMMON, UserContext.getUsername());
        model.addAttribute("contests",contests);
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/management/challenge";
    }

    @GetMapping("/contest/challenge/{lessonSlug}")
    public String showChallengeManagementByLesson(@PathVariable("lessonSlug") String lessonSlug, Model model, HttpServletRequest request){
        Long lessonID = lessonService.findLessonIdBySlug(lessonSlug);
        if (lessonID == null) {
            return "redirect:/management/contest/challenge";
        }

        Optional<CourseLesson> lessonOptional = lessonService.findById(lessonID);
        if (lessonOptional.isEmpty()) {
            return "redirect:/management/contest/challenge";
        }

        CourseLesson lesson = lessonOptional.get();
        if (lesson.getCreator() == null || !UserContext.getUsername().equals(lesson.getCreator().getuserName())) {
            return "redirect:/management/contest/challenge";
        }

        model.addAttribute("lessonSlug", lessonSlug);
        model.addAttribute("lessonTitle", lesson.getTitle());
        model.addAttribute("lessonType", lesson.getType());

        if ("essay".equalsIgnoreCase(lesson.getType())) {
            List<EssayExerciseListShowDTO> essayExercises = essayExerciseService.getEssayExerciseListShowDTOByLessonSlug(lessonSlug);
            model.addAttribute("essayExercises", essayExercises);
        } else {
            List<CodingExerciseDTO> codingExercises = codingExerciseService.getCodingExerciseDTOByLessonSlug(lessonSlug);
            model.addAttribute("codingExercises", codingExercises);
        }

        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/management/challenge-exercises";
    }

    @GetMapping("/contest/create")
    public String createConteset(Model model, HttpServletRequest request){

        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/management/contest-create";
    }

    @GetMapping("/contest/edit/{lessonSlug}")
    public String editConteset(@PathVariable("lessonSlug") String theSlug, Model model, HttpServletRequest request){
        EditLessonDTO lesson = lessonService.getEditLessonDTO(Constants.ID_MODULE_COMMON, theSlug);
        model.addAttribute("lesson", lesson);
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/management/contest-edit";
    }

    @GetMapping("/contest/score/{lessonType}/{lessonSlug}")
    private String showScoreInLesson(@PathVariable("lessonType") String lessonType, @PathVariable("lessonSlug") String theSlug, Model model, HttpServletRequest request){
        Long lessonID = lessonService.findLessonIdBySlug(theSlug);
        List<LessonSubmissionDTO> submissionDTOs = lessonSubmissionService.getLessonSubmissionsByLessonId(lessonID);

        if ("essay".equalsIgnoreCase(lessonType)) {
            List<EssayScoreDetailDTO> essayDetails =
                    essaySubmissionService.getEssayScoreDetailsByLessonId(lessonID);
            model.addAttribute("essayDetails", essayDetails);

            // Set usernames đã có finalScore → khóa form chấm lại
            Set<String> gradedUsers = essayDetails.stream()
                    .filter(d -> d.getFinalScore() != null)
                    .map(EssayScoreDetailDTO::getUserName)
                    .collect(Collectors.toSet());
            model.addAttribute("gradedUsers", gradedUsers);
        }

        model.addAttribute("lessonType", lessonType);
        model.addAttribute("submissions", submissionDTOs);
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/management/contest-score";
    }
}
