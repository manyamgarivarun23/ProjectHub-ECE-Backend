package ProjectHub.ECE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ProjectHub.ECE.service.TechnologyService;

@Controller
public class TechnologyController {

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @GetMapping("/technologies")
    public String technologies(Model model) {

        model.addAttribute(
                "technologies",
                technologyService.getAllTechnologies()
        );

        return "technologies";
    }
}