package by.bsuir.backend.util;

import by.bsuir.backend.model.dto.response.ErrorResponseTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorsHandlerUtil {

    public static Integer getErrorCode(HttpStatus status, Integer subCode) {
        return status.value() * 100 + subCode;
    }

    public static ResponseEntity<ErrorResponseTo> getErrorResponseEntity(HttpStatus status,
                                                                         Integer subCode,
                                                                         String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponseTo(message, getErrorCode(status, subCode)));
    }
}
