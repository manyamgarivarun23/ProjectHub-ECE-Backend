package ProjectHub.ECE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProjectHub.ECE.model.ProjectMember;
import ProjectHub.ECE.service.ProjectMemberService;
import ProjectHub.ECE.service.UserService;

@Controller
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;
    private final UserService userService;

    public ProjectMemberController(ProjectMemberService projectMemberService,
                                    UserService userService) {
        this.projectMemberService = projectMemberService;
        this.userService = userService;
    }

    @GetMapping("/projects/team")
    public String teamPage(
            @RequestParam int projectId,
            Model model) {

        model.addAttribute(
                "members",
                projectMemberService.getMembersByProjectId(projectId)
        );

        model.addAttribute(
                "students",
                userService.getAllStudents()
        );

        model.addAttribute("projectId", projectId);

        return "team-management";
    }

    @PostMapping("/projects/team/add")
    public String addMember(
            @RequestParam int projectId,
            @RequestParam int studentId) {

        ProjectMember member = new ProjectMember();

        member.setProjectId(projectId);
        member.setStudentId(studentId);

        projectMemberService.addMember(member);

        return "redirect:/projects/team?projectId=" + projectId;
    }
    @PostMapping("/projects/team/remove")
public String removeMember(
        @RequestParam int projectId,
        @RequestParam int memberId) {

    projectMemberService.removeMember(memberId);

    return "redirect:/projects/team?projectId=" + projectId;
}
}