package niky.cloudsave.controllers;

import lombok.Getter;
import lombok.Setter;
import niky.cloudsave.exceptions.UserAlreadyExistsException;
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
    ) throws UserAlreadyExistsException {
        var user = userService.registerUser(registerUser.getUsername(), registerUser.getPassword(),registerUser.isAdmin());
        return ResponseEntity.ok(user.getName() + ", user created successfully!");
    }

    @Getter
    @Setter
    public static class RegisterUser {
        private String username;
        private String password;
        private boolean admin;
    }

}
