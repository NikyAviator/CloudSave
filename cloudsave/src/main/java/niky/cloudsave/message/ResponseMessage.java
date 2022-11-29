package niky.cloudsave.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A class that we call (instantiate) when we want to send a message to the user.
 */

@Getter
@Setter
@AllArgsConstructor
public class ResponseMessage {
    private String message;
}
