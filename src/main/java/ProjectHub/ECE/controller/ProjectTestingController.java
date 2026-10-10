package ProjectHub.ECE.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProjectHub.ECE.model.ProjectTesting;
import ProjectHub.ECE.service.ProjectTestingService;

@Controller
public class ProjectTestingController {

    private final ProjectTestingService projectTestingService;

    public ProjectTestingController(
            ProjectTestingService projectTestingService) {

        this.projectTestingService = projectTestingService;
    }


    @GetMapping("/projects/testing")
    public String testingPage(
            @RequestParam int projectId,
            Model model) {

        model.addAttribute(
                "projectId",
                projectId
        );

        model.addAttribute(
                "testingList",
                projectTestingService
                        .getTestingByProjectId(projectId)
        );

        return "project-testing";
    }


    @PostMapping("/projects/testing/add")
    public String addTesting(
            @RequestParam int projectId,
            @RequestParam String testName,
            @RequestParam(required = false) LocalDate testDate,
            @RequestParam String result,
            @RequestParam(required = false) String remarks) {

        ProjectTesting testing =
                new ProjectTesting();

        testing.setProjectId(projectId);
        testing.setTestName(testName);
        testing.setTestDate(testDate);
        testing.setResult(result);
        testing.setRemarks(remarks);

        projectTestingService.addTesting(testing);

        return "redirect:/projects/testing?projectId="
                + projectId;
    }


    @PostMapping("/projects/testing/delete")
    public String deleteTesting(
            @RequestParam int id,
            @RequestParam int projectId) {

        projectTestingService.deleteTesting(id);

        return "redirect:/projects/testing?projectId="
                + projectId;
    }
}