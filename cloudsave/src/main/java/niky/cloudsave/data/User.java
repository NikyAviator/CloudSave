package niky.cloudsave.data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 *  User entity, the one we create and then saved to the DB!
 */

@Entity(name="file_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private UUID userId;
    private String name;
    private String password;
    public User( String name, String password) {
        this.userId = UUID.randomUUID();
        this.name = name;
        this.password = password;
    }
}
