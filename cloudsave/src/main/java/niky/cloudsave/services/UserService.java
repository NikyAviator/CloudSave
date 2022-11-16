package niky.cloudsave.services;

import niky.cloudsave.data.User;
import niky.cloudsave.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final Map<String, String> tokens = new HashMap<>();
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerUser(String username, String password) {
        var user = new User(UUID.randomUUID().toString(), username, password);
        return userRepository.save(user);
    }
    public String login(String username, String password) {
        var userOpt = getByUsername(username);
        if (userOpt.isEmpty()) {
            return null;
        }

        var user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            return null;
        }

        var token = UUID.randomUUID().toString();
        tokens.put(token, username);
        return token;
    }
    public User verifyToken(String token) {
        var username = tokens.get(token);
        if (username == null) return null;

        var user = getByUsername(username);
        return user.orElse(null);
    }
    public Optional<User> getByUsername(String username) {
        return userRepository.findByName(username);
    }
}
