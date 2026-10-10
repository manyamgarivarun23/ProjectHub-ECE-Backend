package ProjectHub.ECE.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProjectHub.ECE.model.Feedback;
import ProjectHub.ECE.model.User;
import ProjectHub.ECE.service.FeedbackService;
import ProjectHub.ECE.service.ProjectService;
import ProjectHub.ECE.service.UserService;

@Controller
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final ProjectService projectService;
    private final UserService userService;

    public FeedbackController(
            FeedbackService feedbackService,
            ProjectService projectService,
            UserService userService) {

        this.feedbackService = feedbackService;
        this.projectService = projectService;
        this.userService = userService;
    }


    @GetMapping("/feedback")
    public String feedbackPage(Model model) {

        model.addAttribute(
                "projects",
                projectService.getAllProjects()
        );

        model.addAttribute(
                "feedbackList",
                feedbackService.getAllFeedback()
        );

        return "feedback";
    }


    @PostMapping("/feedback")
    public String addFeedback(
            @RequestParam int projectId,
            @RequestParam String comments,
            @RequestParam String status,
            Authentication authentication) {

        User faculty =
                userService.getUserByEmail(authentication.getName());

        Feedback feedback = new Feedback();

        feedback.setProjectId(projectId);
        feedback.setFacultyId(faculty.getId());
        feedback.setComments(comments);
        feedback.setStatus(status);

        feedbackService.addFeedback(feedback);

        return "redirect:/feedback";
    }


    @PostMapping("/feedback/status")
    public String updateFeedbackStatus(
            @RequestParam int feedbackId,
            @RequestParam String status) {

        feedbackService.updateFeedbackStatus(
                feedbackId,
                status
        );

        return "redirect:/feedback";
    }
}