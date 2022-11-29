package niky.cloudsave.services;
import lombok.extern.slf4j.Slf4j;
import niky.cloudsave.data.User;
import niky.cloudsave.exceptions.UserAlreadyExistsException;
import niky.cloudsave.repositories.UserRepository;
import niky.cloudsave.security.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * @param username - username of type String
     * @return - looks through the db to see if the user exists.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("A user with username '" + username + "' could not be found."));

        return new UserObject(user);
    }

    /**
     *
     * @param username - username of type string
     * @param password - we recieve the password and we encode it.
     * @return - returns the whole user object.
     * @throws UserAlreadyExistsException
     */

    public User registerUser(String username, String password)
            throws UserAlreadyExistsException
    {
        var existing = userRepository.findByName(username);
        if (existing.isPresent()) {
            log.info("Failed to register user since name '" + username + "' already exists.");
            throw new UserAlreadyExistsException();
        }
        var user = new User(username, passwordEncoder.encode(password));
        log.info("Successfully registered user with id '" + user.getUserId() + "'.");
        return userRepository.save(user);
    }

}
