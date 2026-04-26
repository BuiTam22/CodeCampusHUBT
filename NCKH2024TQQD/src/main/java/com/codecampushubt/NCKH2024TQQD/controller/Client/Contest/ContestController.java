package com.codecampushubt.NCKH2024TQQD.controller.Client.Contest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codecampushubt.NCKH2024TQQD.Constant.Constant;
import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dao.ContestExerciseAttemptRepository;
import com.codecampushubt.NCKH2024TQQD.dto.EssayExerciseDTO.EssayExerciseListShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.EssayExerciseServices.EssayExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CodingSubmission.CodingSubmissionShow;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.ContestShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.CodingExerciseServices.CodingExerciseService;
import com.codecampushubt.NCKH2024TQQD.service.CodingSubmissionServices.CodingSubmissionService;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/contest")
public class ContestController {
    private final LessonService lessonService;
    private final CodingExerciseService codingExerciseService;
    private final EssayExerciseService essayExerciseService;
    private final CodingSubmissionService codingSubmissionService;
    private final ContestExerciseAttemptRepository attemptRepository;

    @Autowired
    public ContestController(LessonService lessonService,
                             CodingExerciseService codingExerciseService,
                             EssayExerciseService essayExerciseService,
                             CodingSubmissionService codingSubmissionService,
                             ContestExerciseAttemptRepository attemptRepository) {
        this.lessonService = lessonService;
        this.codingExerciseService = codingExerciseService;
        this.essayExerciseService = essayExerciseService;
        this.codingSubmissionService = codingSubmissionService;
        this.attemptRepository = attemptRepository;
    }

    @GetMapping("")
    public String showContest(Model model, HttpServletRequest request){
        // fix cứng moduleId là 3
        List<ContestShowDTO> contests = lessonService.getContestShowDTOByIsContest(Constant.ID_MODULE_COMMON);
        model.addAttribute("contests", contests);
        model.addAttribute("activePage", request.getRequestURI());
        return  "ClientTemplates/contest/contest";
    }

    /**
     * Build Map<exerciseID, attemptNumber> từ kết quả query bulk.
     * Trả về map rỗng nếu user chưa đăng nhập.
     */
    private Map<Long, Integer> buildAttemptMap(String exerciseType) {
        Long userID = UserContext.getUserID();
        Map<Long, Integer> map = new HashMap<>();
        if (userID == null) return map;
        List<Object[]> rows = attemptRepository.getAttemptSummaryByUserAndType(userID, exerciseType);
        for (Object[] row : rows) {
            Long exID      = ((Number) row[0]).longValue();
            Integer maxAtt = ((Number) row[1]).intValue();
            map.put(exID, maxAtt);
        }
        return map;
    }

    @GetMapping("/{lesson-slug}")
    public String showCodingExerciseByLessonSlug(@PathVariable("lesson-slug") String theSlug,
                                                  Model model, HttpServletRequest request){
        List<CodingExerciseDTO> exercises = codingExerciseService.getCodingExerciseDTOByLessonSlug(theSlug);
        exercises = exercises != null ? exercises : new ArrayList<>();

        // Map: exerciseID -> số lần đã làm
        Map<Long, Integer> attemptMap = buildAttemptMap("coding");

        model.addAttribute("exercises", exercises);
        model.addAttribute("attemptMap", attemptMap);
        model.addAttribute("activePage", request.getRequestURI());
        return  "ClientTemplates/contest/show";
    }

    @GetMapping("/type-essay")
    public String showEssayExercises(Model model, HttpServletRequest request){
        List<ContestShowDTO> essayContests = lessonService.getEssayContestShowDTOByIsContest(Constant.ID_MODULE_COMMON);
        model.addAttribute("contests", essayContests);
        model.addAttribute("activePage", request.getRequestURI());
        return  "ClientTemplates/contest/contest";
    }

    @GetMapping("/lesson/problem/{slug}")
    public String showContestProblemBySlug(@PathVariable("slug") String theSlug,
                                            Model model, HttpServletRequest request){
        CodingExerciseDetailDTO exercise = codingExerciseService.getCodingExerciseDetailDTOByExerciseSlug(theSlug);
        model.addAttribute("exercise", exercise);
        model.addAttribute("slug", theSlug);
        model.addAttribute("activePage", "/contest");
        model.addAttribute("basePath", "/contest/lesson");
        return "ClientTemplates/coding-exercise/problem";
    }

    @GetMapping("/lesson/submissions/{slug}")
    public String showContestSubmissions(@PathVariable("slug") String theSlug, Model model){
        List<CodingSubmissionShow> submissions = codingSubmissionService.getCodingSubmissionShowByUserName(UserContext.getUsername(), theSlug);
        model.addAttribute("submissions", submissions);
        model.addAttribute("slug", theSlug);
        model.addAttribute("activePage", "/contest");
        model.addAttribute("basePath", "/contest/lesson");
        return "ClientTemplates/coding-exercise/submission";
    }

    @GetMapping("/lesson/leaderboard/{slug}")
    public String showContestLeaderBoard(@PathVariable("slug") String theSlug, Model model){
        List<CodingSubmissionShow> submissions = codingSubmissionService.getCodingSubmissionShowBySlugExercise(theSlug);
        model.addAttribute("submissions", submissions);
        model.addAttribute("slug", theSlug);
        model.addAttribute("activePage", "/contest");
        model.addAttribute("basePath", "/contest/lesson");
        return "ClientTemplates/coding-exercise/leaderboard";
    }

    @GetMapping("/lesson/tutorial/{slug}")
    public String showContestTutorial(@PathVariable("slug") String theSlug, Model model){
        model.addAttribute("slug", theSlug);
        model.addAttribute("activePage", "/contest");
        model.addAttribute("basePath", "/contest/lesson");
        return "ClientTemplates/coding-exercise/tutorial";
    }

    @GetMapping("/type-essay/{lesson-slug}")
    public String showEssayExerciseByLessonSlug(@PathVariable("lesson-slug") String theSlug,
                                                 Model model, HttpServletRequest request){
        List<EssayExerciseListShowDTO> exercises = essayExerciseService.getEssayExerciseListShowDTOByLessonSlug(theSlug);
        exercises = exercises != null ? exercises : new ArrayList<>();

        // Map: exerciseID -> số lần đã làm
        Map<Long, Integer> attemptMap = buildAttemptMap("essay");

        model.addAttribute("exercises", exercises);
        model.addAttribute("attemptMap", attemptMap);
        model.addAttribute("activePage", request.getRequestURI());
        return  "ClientTemplates/contest/list-essay-show";
    }
}
