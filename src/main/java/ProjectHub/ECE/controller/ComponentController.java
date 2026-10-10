package ProjectHub.ECE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProjectHub.ECE.model.ProjectComponent;
import ProjectHub.ECE.service.ComponentService;
import ProjectHub.ECE.service.ProjectComponentService;

@Controller
public class ComponentController {

    private final ComponentService componentService;
    private final ProjectComponentService projectComponentService;

    public ComponentController(
            ComponentService componentService,
            ProjectComponentService projectComponentService) {

        this.componentService = componentService;
        this.projectComponentService = projectComponentService;
    }

    @GetMapping("/projects/components")
    public String projectComponents(
            @RequestParam int projectId,
            Model model) {

        model.addAttribute(
                "components",
                componentService.getAllComponents()
        );

        model.addAttribute(
                "projectComponents",
                projectComponentService.getComponentsByProjectId(projectId)
        );

        model.addAttribute("projectId", projectId);

        return "project-components";
    }

    @PostMapping("/projects/components/add")
    public String addComponent(
            @RequestParam int projectId,
            @RequestParam int componentId,
            @RequestParam int quantity) {

        ProjectComponent projectComponent =
                new ProjectComponent();

        projectComponent.setProjectId(projectId);
        projectComponent.setComponentId(componentId);
        projectComponent.setQuantity(quantity);

        projectComponentService.addProjectComponent(projectComponent);

        return "redirect:/projects/components?projectId="
                + projectId;
    }
}