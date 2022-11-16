package niky.cloudsave.services;

import niky.cloudsave.data.User;
import niky.cloudsave.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(String username){
        var user = new User(UUID.randomUUID().toString(),username);
        return userRepository.save(user);
    }
    // We create and return our own findByName method for the user (as optional because of null management)
    public Optional<User> getByUserName(String username){
        return userRepository.findByName(username);
    }
}
