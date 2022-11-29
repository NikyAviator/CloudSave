package niky.cloudsave.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * When we save a file we save all these fields.
 * These fields are later used as a message to the user to tell what kind of file we saved.
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFile {
    private String name, url, type;
    private long size;
}
