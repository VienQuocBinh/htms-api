package htms.common.exception.handler;

import htms.common.exception.api.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GenericExceptionHandler extends RuntimeException {
    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param exception exception
     * @return the ApiError object
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception exception) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR,
                "Not handle exception: " + exception.getClass().getSimpleName(),
                exception);
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }
}
