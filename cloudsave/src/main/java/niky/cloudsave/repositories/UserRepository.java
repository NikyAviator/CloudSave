package niky.cloudsave.repositories;

import niky.cloudsave.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
