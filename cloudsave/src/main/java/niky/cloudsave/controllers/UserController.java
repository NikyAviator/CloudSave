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

    /**
     *
     * @param registerUser - The class that merges username and password. Into a user.
     * @return - returns the name of the user with a message.
     * @throws UserAlreadyExistsException - Checks if there already exists a user. Throws UserAlreadyExistsException if there already
     *  exists a user with same name.
     */

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterUser registerUser
    ) throws UserAlreadyExistsException {
        if(registerUser.getUsername() == null || registerUser.getPassword() == null){
            return ResponseEntity.badRequest().body("Enter correct body for username and password!!");
        }
        else {
            var user = userService.registerUser(registerUser.getUsername(), registerUser.getPassword());
            return ResponseEntity.ok(user.getName() + ", user created successfully!");
        }

    }

    /**
     *  We use this class in /register to save both username and password.
     */
    @Getter
    @Setter
    public static class RegisterUser {
        private String username;
        private String password;
    }

}
