package niky.cloudsave.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom response if the username already exists.
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "A user with the specified name already exists.")
public class UserAlreadyExistsException extends Exception {
}
