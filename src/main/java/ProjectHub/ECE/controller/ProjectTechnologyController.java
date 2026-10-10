package ProjectHub.ECE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProjectHub.ECE.model.ProjectTechnology;
import ProjectHub.ECE.service.ProjectTechnologyService;
import ProjectHub.ECE.service.TechnologyService;

@Controller
public class ProjectTechnologyController {

    private final ProjectTechnologyService projectTechnologyService;
    private final TechnologyService technologyService;

    public ProjectTechnologyController(
            ProjectTechnologyService projectTechnologyService,
            TechnologyService technologyService) {

        this.projectTechnologyService = projectTechnologyService;
        this.technologyService = technologyService;
    }

    @GetMapping("/projects/technologies")
    public String projectTechnologies(
            @RequestParam int projectId,
            Model model) {

        model.addAttribute(
                "technologies",
                technologyService.getAllTechnologies()
        );

        model.addAttribute(
                "projectTechnologies",
                projectTechnologyService
                        .getTechnologiesByProjectId(projectId)
        );

        model.addAttribute("projectId", projectId);

        return "project-technologies";
    }

    @PostMapping("/projects/technologies/add")
    public String addTechnology(
            @RequestParam int projectId,
            @RequestParam int technologyId) {

        ProjectTechnology projectTechnology =
                new ProjectTechnology();

        projectTechnology.setProjectId(projectId);
        projectTechnology.setTechnologyId(technologyId);

        boolean added = projectTechnologyService
                .addProjectTechnology(projectTechnology);

        if (!added) {
            return "redirect:/projects/technologies?projectId="
                    + projectId
                    + "&error=duplicate";
        }

        return "redirect:/projects/technologies?projectId="
                + projectId;
    }
}