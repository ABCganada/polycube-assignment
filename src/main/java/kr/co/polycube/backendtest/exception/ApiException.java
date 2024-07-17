package kr.co.polycube.backendtest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiException extends Exception {

    private final HttpStatus httpStatus;
    private final String reason;
}
