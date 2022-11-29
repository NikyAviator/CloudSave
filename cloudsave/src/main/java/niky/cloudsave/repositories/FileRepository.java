package niky.cloudsave.repositories;
import niky.cloudsave.data.FileDB;
import niky.cloudsave.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * A custom query to find all files by a user.
 */

@Repository
@Transactional
public interface FileRepository extends JpaRepository<FileDB, String> {

    List<FileDB> findAllByUser(User user);
}
