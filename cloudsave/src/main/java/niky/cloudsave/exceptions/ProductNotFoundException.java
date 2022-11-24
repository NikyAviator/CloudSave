package niky.cloudsave.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "A product with the specified id could not be found.")
public class ProductNotFoundException extends Exception {
}
