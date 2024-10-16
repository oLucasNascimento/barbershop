package br.com.barbermanager.barbershopmanagement.exception.handler;

import br.com.barbermanager.barbershopmanagement.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<RestErrorMessage> runTimeException(RuntimeException exception, WebRequest request){
        String timestamp = LocalDateTime.now().toString();
        String path = request.getDescription(false).replace("uri=", "");
        String errorCode = "RUNTIME_ERROR"; // Ou algum código apropriado

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                timestamp,
                path,
                errorCode
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<RestErrorMessage> nullPointerException(NullPointerException exception, WebRequest request){
        String timestamp = LocalDateTime.now().toString();
        String path = request.getDescription(false).replace("uri=", "");
        String errorCode = "NULL_POINTER_ERROR"; // Ou algum código apropriado

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                timestamp,
                path,
                errorCode
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<RestErrorMessage> notFoundException(NotFoundException exception, WebRequest request){
        String timestamp = LocalDateTime.now().toString();
        String path = request.getDescription(false).replace("uri=", "");
        String errorCode = "NOT_FOUND_ERROR";
        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                timestamp,
                path,
                errorCode
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<RestErrorMessage> alreadyExistsException(AlreadyExistsException exception, WebRequest request){
        String timestamp = LocalDateTime.now().toString();
        String path = request.getDescription(false).replace("uri=", "");
        String errorCode = "ALREADY_EXISTS_ERROR"; // Ou algum código apropriado

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                timestamp,
                path,
                errorCode
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<RestErrorMessage> badRequestException(BadRequestException exception, WebRequest request){
        String timestamp = LocalDateTime.now().toString();
        String path = request.getDescription(false).replace("uri=", "");
        String errorCode = "BAD_REQUEST_ERROR"; // Ou algum código apropriado

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                timestamp,
                path,
                errorCode
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(AlreadyActiveException.class)
    protected ResponseEntity<RestErrorMessage> alreadyActiveException(AlreadyActiveException exception, WebRequest request){
        String timestamp = LocalDateTime.now().toString();
        String path = request.getDescription(false).replace("uri=", "");
        String errorCode = "ALREADY_ACTIVE_ERROR"; // Ou algum código apropriado

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                timestamp,
                path,
                errorCode
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(InactiveException.class)
    protected ResponseEntity<RestErrorMessage> inactiveException(InactiveException exception, WebRequest request){
        String timestamp = LocalDateTime.now().toString();
        String path = request.getDescription(false).replace("uri=", "");
        String errorCode = "INACTIVE_ERROR"; // Ou algum código apropriado

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                timestamp,
                path,
                errorCode
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<RestErrorMessage> businessException(BusinessException exception, WebRequest request){
        String timestamp = LocalDateTime.now().toString();
        String path = request.getDescription(false).replace("uri=", "");
        String errorCode = "BUSINESS_ERROR";

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                timestamp,
                path,
                errorCode
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @Override
    protected ResponseEntity<java.lang.Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String defaultMessage = exception.getBindingResult().getFieldErrors().stream()
                .findFirst() // Obtém o primeiro erro
                .map(fieldError -> fieldError.getDefaultMessage()) .orElse("Validation error occurred.");

        String timestamp = LocalDateTime.now().toString();
        String path = request.getDescription(false).replace("uri=", "");
        String errorCode = "VALIDATION_ERROR"; // Ou algum código apropriado

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                defaultMessage,
                timestamp,
                path,
                errorCode
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}