package niky.cloudsave.repositories;

import niky.cloudsave.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByName(String username);
}
