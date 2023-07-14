package htms.common.exception.handler;

import htms.common.exception.IllegalArgumentException;
import htms.common.exception.*;
import htms.common.exception.api.ApiError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Log4j2
public class DomainExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Wrong login information
     *
     * @param exception {@code AuthenticationException}
     * @return {@link ResponseEntity<Object>}
     * @author Vien Binh
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception) {
        log.error(exception.getMessage());
        return buildResponseEntity(
                ApiError.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .message("Authentication failed: " + exception.getClass().getName() + ". " + exception.getMessage())
                        .build());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> handleInternalServerError(InternalServerErrorException exception) {
        log.error(exception.getMessage());
        return buildResponseEntity(
                ApiError.builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Runtime error: " + exception.getMessage())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error(exception.getMessage());
        return buildResponseEntity(
                ApiError.builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Runtime error: " + exception.getMessage())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception) {
        log.error(exception.getMessage());
        return buildResponseEntity(
                ApiError.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Runtime error: " + exception.getMessage())
                        .build());
    }

    /**
     * Trigger when exception is thrown while executing the enrollment request
     *
     * @param exception {@link EnrollmentProcessException}
     * @return the ApiError object
     */
    @ExceptionHandler(EnrollmentProcessException.class)
    public ResponseEntity<Object> handleEnrolProcessException(EnrollmentProcessException exception) {
        log.error(exception.getMessage());
        return buildResponseEntity(
                ApiError.builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Enrollment process error: " + exception.getMessage())
                        .build());
    }

    /**
     * Trigger when exception is thrown while executing the create class request
     *
     * @param exception {@link CreateClassException}
     * @return the ApiError object
     */
    @ExceptionHandler(CreateClassException.class)
    public ResponseEntity<Object> handleCreateClassException(CreateClassException exception) {
        log.error(exception.getMessage());
        return buildResponseEntity(
                ApiError.builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Create class process error: " + exception.getMessage())
                        .build());
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}
