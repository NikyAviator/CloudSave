package niky.cloudsave.repositories;
import niky.cloudsave.data.FileDB;
import niky.cloudsave.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<FileDB, String> {

    Optional<FileDB> findFileByUser(User user);
}
