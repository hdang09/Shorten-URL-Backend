package hdang09.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(BindException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();

        FieldError firstError = fieldErrors.get(0);
        String errorMessage = firstError.getDefaultMessage();

        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    // Add more exception handling methods as needed for specific exceptions
    // For example:
    /*
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        // Create an error response object
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Resource not found");
        // Set other properties of the error response as needed

        // Return the error response with an appropriate HTTP status code
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    */
}