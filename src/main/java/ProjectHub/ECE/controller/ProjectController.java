package ProjectHub.ECE.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProjectHub.ECE.model.Project;
import ProjectHub.ECE.model.User;
import ProjectHub.ECE.service.FeedbackService;
import ProjectHub.ECE.service.ProjectService;
import ProjectHub.ECE.service.UserService;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final FeedbackService feedbackService;

    public ProjectController(
            ProjectService projectService,
            UserService userService,
            FeedbackService feedbackService) {

        this.projectService = projectService;
        this.userService = userService;
        this.feedbackService = feedbackService;
    }


    @GetMapping("/projects/new")
    public String createProjectPage() {
        return "create-project";
    }


    @PostMapping("/projects")
    public String createProject(
            @RequestParam String title,
            @RequestParam String domain,
            @RequestParam String projectAbstract,
            @RequestParam String description,
            Authentication authentication) {

        User user =
                userService.getUserByEmail(authentication.getName());

        Project project = new Project();

        project.setTitle(title);
        project.setDomain(domain);
        project.setProjectAbstract(projectAbstract);
        project.setDescription(description);
        project.setStatus("DRAFT");
        project.setProgress(0);
        project.setCreatedBy(user.getId());

        projectService.createProject(project);

        return "redirect:/projects";
    }


    @GetMapping("/projects")
    public String projects(Model model) {

        model.addAttribute(
                "projects",
                projectService.getAllProjects()
        );

        return "projects";
    }


    @GetMapping("/projects/{id}")
    public String projectDetails(
            @PathVariable int id,
            Model model) {

        Project project =
                projectService.getProjectById(id);

        model.addAttribute(
                "project",
                project
        );

        model.addAttribute(
                "feedbackList",
                feedbackService.getFeedbackByProjectId(id)
        );

        return "project-details";
    }


    @PostMapping("/projects/status")
    public String updateProjectStatus(
            @RequestParam int projectId,
            @RequestParam String status) {

        projectService.updateProjectStatus(
                projectId,
                status
        );

        return "redirect:/projects/" + projectId;
    }


    @PostMapping("/projects/progress")
    public String updateProjectProgress(
            @RequestParam int projectId,
            @RequestParam int progress) {

        if (progress < 0) {
            progress = 0;
        }

        if (progress > 100) {
            progress = 100;
        }

        projectService.updateProjectProgress(
                projectId,
                progress
        );

        return "redirect:/projects/" + projectId;
    }
}