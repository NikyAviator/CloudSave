package niky.cloudsave.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "A product with the specified name already exists.")
public class ProductAlreadyExistsException extends Exception {
}
