package ProjectHub.ECE.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ProjectHub.ECE.model.User;
import ProjectHub.ECE.service.UserService;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/me")
    public Map<String, Object> currentUser(Authentication authentication) {

        User user = userService.getUserByEmail(authentication.getName());

        return Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail(),
                "role", user.getRole()
        );
    }
}
