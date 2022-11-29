package niky.cloudsave.data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "files")
public class FileDB {
    /**
     * @Id is primary key. (String id), ids are stored as a UUID values.
     * @ManyToOne one user can have many files.
     * @Lob - saves the files in this field as a Lob. Lob can save binary data.
     */

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    private User user;
    private String name;
    private String type;
    @Lob
    private byte[] data;
    public FileDB(String name, String type, byte[] data, User user) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.user = user;
    }

}
