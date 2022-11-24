package niky.cloudsave.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

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
    private boolean admin;

    public User( String name, String password, boolean admin) {
        this.userId = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.admin = admin;
    }
}
