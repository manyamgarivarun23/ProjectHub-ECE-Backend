package ProjectHub.ECE.controller;

import java.time.LocalDate;

import java.util.LinkedHashMap;

import java.util.List;

import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import ProjectHub.ECE.model.Component;

import ProjectHub.ECE.model.Feedback;

import ProjectHub.ECE.model.Project;

import ProjectHub.ECE.model.ProjectComponent;

import ProjectHub.ECE.model.ProjectMember;

import ProjectHub.ECE.model.ProjectTechnology;

import ProjectHub.ECE.model.ProjectTesting;

import ProjectHub.ECE.model.Technology;

import ProjectHub.ECE.model.User;

import ProjectHub.ECE.service.ComponentService;

import ProjectHub.ECE.service.FeedbackService;

import ProjectHub.ECE.service.ProjectComponentService;

import ProjectHub.ECE.service.ProjectMemberService;

import ProjectHub.ECE.service.ProjectService;

import ProjectHub.ECE.service.ProjectTechnologyService;

import ProjectHub.ECE.service.ProjectTestingService;

import ProjectHub.ECE.service.TechnologyService;

import ProjectHub.ECE.service.UserService;

@RestController

@RequestMapping("/api")

public class ReactApiController {

    private final ProjectService projectService;

    private final UserService userService;

    private final ProjectMemberService memberService;

    private final ComponentService componentService;

    private final ProjectComponentService projectComponentService;

    private final TechnologyService technologyService;

    private final ProjectTechnologyService projectTechnologyService;

    private final ProjectTestingService testingService;

    private final FeedbackService feedbackService;

    public ReactApiController(

            ProjectService projectService,

            UserService userService,

            ProjectMemberService memberService,

            ComponentService componentService,

            ProjectComponentService projectComponentService,

            TechnologyService technologyService,

            ProjectTechnologyService projectTechnologyService,

            ProjectTestingService testingService,

            FeedbackService feedbackService) {

        this.projectService = projectService;

        this.userService = userService;

        this.memberService = memberService;

        this.componentService = componentService;

        this.projectComponentService = projectComponentService;

        this.technologyService = technologyService;

        this.projectTechnologyService = projectTechnologyService;

        this.testingService = testingService;

        this.feedbackService = feedbackService;

    }

    // =========================================================

    // TEST

    // =========================================================

    @GetMapping("/test")

    public Map<String, String> test() {

        return Map.of(

                "message",

                "ProjectHub ECE API is working!"

        );

    }

    // =========================================================

    // PROJECTS

    // =========================================================

    @GetMapping("/projects")

    public List<Project> projects(Authentication authentication) {

        User user = userService.getUserByEmail(

                authentication.getName()

        );

        if ("STUDENT".equals(user.getRole())) {

            return projectService.getProjectsByCreator(

                    user.getId()

            );

        }

        return projectService.getAllProjects();

    }

    @GetMapping("/projects/{id}")

    public ResponseEntity<?> project(@PathVariable int id) {

        Project project = projectService.getProjectById(id);

        if (project == null) {

            return ResponseEntity.notFound().build();

        }

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("project", project);

        result.put(

                "feedback",

                feedbackService.getFeedbackByProjectId(id)

        );

        return ResponseEntity.ok(result);

    }

    @PostMapping("/projects")

    public ResponseEntity<Project> createProject(

            @RequestBody Map<String, String> body,

            Authentication authentication) {

        User user = userService.getUserByEmail(

                authentication.getName()

        );

        Project project = new Project();

        project.setTitle(body.get("title"));

        project.setDomain(body.get("domain"));

        project.setProjectAbstract(body.get("projectAbstract"));

        project.setDescription(body.get("description"));

        project.setStatus("DRAFT");

        project.setProgress(0);

        project.setCreatedBy(user.getId());

        projectService.createProject(project);

        Project created = projectService

                .getProjectsByCreator(user.getId())

                .stream()

                .findFirst()

                .orElse(project);

        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(created);

    }

    @PutMapping("/projects/status")

    public Project updateStatus(

            @RequestBody Map<String, Object> body) {

        int projectId = Integer.parseInt(

                String.valueOf(body.get("projectId"))

        );

        projectService.updateProjectStatus(

                projectId,

                String.valueOf(body.get("status"))

        );

        return projectService.getProjectById(projectId);

    }

    @PutMapping("/projects/progress")

    public Project updateProgress(

            @RequestBody Map<String, Object> body) {

        int projectId = Integer.parseInt(

                String.valueOf(body.get("projectId"))

        );

        int progress = Integer.parseInt(

                String.valueOf(body.get("progress"))

        );

        progress = Math.max(0, Math.min(100, progress));

        projectService.updateProjectProgress(

                projectId,

                progress

        );

        return projectService.getProjectById(projectId);

    }

    // =========================================================

    // STUDENTS

    // =========================================================

    @GetMapping("/students")

    public List<Map<String, Object>> students() {

        return userService.getAllStudents()

                .stream()

                .map(student -> {

                    Map<String, Object> map =

                            new LinkedHashMap<>();

                    map.put("id", student.getId());

                    map.put("name", student.getName());

                    map.put("email", student.getEmail());

                    map.put("role", student.getRole());

                    return map;

                })

                .toList();

    }

    // =========================================================

    // PROJECT TEAM

    // =========================================================

    @GetMapping("/projects/{projectId}/team")

    public List<ProjectMember> team(

            @PathVariable int projectId) {

        return memberService.getMembersByProjectId(projectId);

    }

    @PostMapping("/projects/{projectId}/team")

    public ResponseEntity<?> addMember(

            @PathVariable int projectId,

            @RequestBody Map<String, Object> body) {

        Object rawStudentId = body.get("studentId");

        if (rawStudentId == null) {

            return ResponseEntity.badRequest()

                    .body(Map.of(

                            "message",

                            "Student ID is required."

                    ));

        }

        final int studentId;

        try {

            studentId = Integer.parseInt(

                    rawStudentId.toString().trim()

            );

        } catch (NumberFormatException e) {

            return ResponseEntity.badRequest()

                    .body(Map.of(

                            "message",

                            "Student ID must be a valid number."

                    ));

        }

        ProjectMember member = new ProjectMember();

        member.setProjectId(projectId);

        member.setStudentId(studentId);

        memberService.addMember(member);

        return ResponseEntity.ok(

                memberService.getMembersByProjectId(projectId)

        );

    }

    @DeleteMapping("/projects/team/{id}")

    public ResponseEntity<Void> removeMember(

            @PathVariable int id) {

        memberService.removeMember(id);

        return ResponseEntity.noContent().build();

    }

    // =========================================================

    // COMPONENT CATALOG

    // =========================================================

    @GetMapping("/components")

    public Object components() {

        return componentService.getAllComponents();

    }

    @PostMapping("/components")

    public ResponseEntity<?> createComponent(

            @RequestBody Map<String, Object> body) {

        String name = body.get("name") == null

                ? ""

                : String.valueOf(body.get("name"));

        String category = body.get("category") == null

                ? ""

                : String.valueOf(body.get("category"));

        String description = body.get("description") == null

                ? ""

                : String.valueOf(body.get("description"));

        if (name.trim().isEmpty()) {

            return ResponseEntity.badRequest()

                    .body(Map.of(

                            "message",

                            "Component name is required."

                    ));

        }

        Component component = new Component();

        component.setName(name.trim());

        component.setCategory(category.trim());

        component.setDescription(description.trim());

        componentService.addComponent(component);

        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(componentService.getAllComponents());

    }

    // =========================================================

    // PROJECT COMPONENTS

    // =========================================================

    @GetMapping("/projects/{projectId}/components")

    public List<ProjectComponent> projectComponents(

            @PathVariable int projectId) {

        return projectComponentService

                .getComponentsByProjectId(projectId);

    }

    @PostMapping("/projects/{projectId}/components")

    public List<ProjectComponent> addComponent(

            @PathVariable int projectId,

            @RequestBody Map<String, Object> body) {

        ProjectComponent item = new ProjectComponent();

        item.setProjectId(projectId);

        item.setComponentId(Integer.parseInt(

                String.valueOf(body.get("componentId"))

        ));

        item.setQuantity(Integer.parseInt(

                String.valueOf(body.get("quantity"))

        ));

        projectComponentService.addProjectComponent(item);

        return projectComponentService

                .getComponentsByProjectId(projectId);

    }

    // =========================================================

        @DeleteMapping("/projects/{projectId}/components/{id}")
    public ResponseEntity<?> removeProjectComponent(
            @PathVariable int projectId,
            @PathVariable int id) {

        boolean belongsToProject = projectComponentService
                .getComponentsByProjectId(projectId)
                .stream()
                .anyMatch(component -> component.getId() == id);

        if (!belongsToProject) {
            return ResponseEntity.notFound().build();
        }

        boolean removed = projectComponentService.removeProjectComponent(id);

        if (!removed) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    // =========================================================
    // TECHNOLOGY CATALOG

    // =========================================================

    @GetMapping("/technologies")

    public Object technologies() {

        return technologyService.getAllTechnologies();

    }

    @PostMapping("/technologies")

    public ResponseEntity<?> createTechnology(

            @RequestBody Map<String, Object> body) {

        String name = body.get("name") == null

                ? ""

                : String.valueOf(body.get("name"));

        String category = body.get("category") == null

                ? ""

                : String.valueOf(body.get("category"));

        if (name.trim().isEmpty()) {

            return ResponseEntity.badRequest()

                    .body(Map.of(

                            "message",

                            "Technology name is required."

                    ));

        }

        Technology technology = new Technology();

        technology.setName(name.trim());

        technology.setCategory(category.trim());

        technologyService.addTechnology(technology);

        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(technologyService.getAllTechnologies());

    }

    // =========================================================

    // PROJECT TECHNOLOGIES

    // =========================================================

    @GetMapping("/projects/{projectId}/technologies")

    public List<ProjectTechnology> projectTechnologies(

            @PathVariable int projectId) {

        return projectTechnologyService

                .getTechnologiesByProjectId(projectId);

    }

    @PostMapping("/projects/{projectId}/technologies")

    public ResponseEntity<?> addTechnology(

            @PathVariable int projectId,

            @RequestBody Map<String, Object> body) {

        ProjectTechnology item = new ProjectTechnology();

        item.setProjectId(projectId);

        item.setTechnologyId(Integer.parseInt(

                String.valueOf(body.get("technologyId"))

        ));

        if (!projectTechnologyService

                .addProjectTechnology(item)) {

            return ResponseEntity

                    .status(HttpStatus.CONFLICT)

                    .body(Map.of(

                            "message",

                            "Technology already assigned."

                    ));

        }

        return ResponseEntity.ok(

                projectTechnologyService

                        .getTechnologiesByProjectId(projectId)

        );

    }

    // =========================================================

    // TESTING

    // =========================================================

    @GetMapping("/projects/{projectId}/testing")

    public List<ProjectTesting> testing(

            @PathVariable int projectId) {

        return testingService.getTestingByProjectId(projectId);

    }

    @PostMapping("/projects/{projectId}/testing")

    public List<ProjectTesting> addTesting(

            @PathVariable int projectId,

            @RequestBody ProjectTesting body) {

        body.setId(0);

        body.setProjectId(projectId);

        if (body.getTestDate() == null) {

            body.setTestDate(LocalDate.now());

        }

        testingService.addTesting(body);

        return testingService.getTestingByProjectId(projectId);

    }

    @DeleteMapping("/projects/testing/{id}")

    public ResponseEntity<Void> deleteTesting(

            @PathVariable int id) {

        testingService.deleteTesting(id);

        return ResponseEntity.noContent().build();

    }

    // =========================================================

    // FEEDBACK

    // =========================================================

    @GetMapping("/feedback")

    public List<Feedback> feedback() {

        return feedbackService.getAllFeedback();

    }

    @PostMapping("/feedback")

    public List<Feedback> addFeedback(

            @RequestBody Map<String, Object> body,

            Authentication authentication) {

        User faculty = userService.getUserByEmail(

                authentication.getName()

        );

        Feedback feedback = new Feedback();

        feedback.setProjectId(Integer.parseInt(

                String.valueOf(body.get("projectId"))

        ));

        feedback.setFacultyId(faculty.getId());

        feedback.setComments(

                String.valueOf(body.get("comments"))

        );

        feedback.setStatus(

                String.valueOf(body.get("status"))

        );

        feedbackService.addFeedback(feedback);

        return feedbackService.getAllFeedback();

    }

    @PutMapping("/feedback/{feedbackId}/status")

    public List<Feedback> updateFeedbackStatus(

            @PathVariable int feedbackId,

            @RequestBody Map<String, String> body) {

        feedbackService.updateFeedbackStatus(

                feedbackId,

                body.get("status")

        );

        return feedbackService.getAllFeedback();

    }

}
