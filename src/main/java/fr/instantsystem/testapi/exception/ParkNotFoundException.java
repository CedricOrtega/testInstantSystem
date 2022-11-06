package fr.instantsystem.testapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ParkNotFoundException permits to customize error message when an open API isn't find for city send in request
 *
 * @author Cédric Ortega
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "City Not Found")
public class ParkNotFoundException extends Exception {
    public ParkNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
