package niky.cloudsave.controllers;

import lombok.Getter;
import lombok.Setter;
import niky.cloudsave.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterUser registerUser
    ) {
        var user = userService.registerUser(registerUser.getUsername(), registerUser.getPassword());
        return ResponseEntity.ok(user.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginUser loginUser
    ) {
        var token = userService.login(loginUser.getUsername(), loginUser.getPassword());
        return ResponseEntity.ok(token);
    }

    @Getter
    @Setter
    public static class RegisterUser {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class LoginUser {
        private String username;
        private String password;
    }
}
