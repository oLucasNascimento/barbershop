package br.com.barbermanager.barbershopmanagement.exception.handler;

import br.com.barbermanager.barbershopmanagement.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    private ResponseEntity<RestErrorMessage> runTimeException(RuntimeException exception){
//        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//    }
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<RestErrorMessage> notFoundException(NotFoundException exception){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    private ResponseEntity<RestErrorMessage> alreadyExistsException(AlreadyExistsException exception){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<RestErrorMessage> badRequestException(BadRequestException exception){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(AlreadyActiveException.class)
    private ResponseEntity<RestErrorMessage> alreadyActiveException(AlreadyActiveException exception){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(InactiveException.class)
    private ResponseEntity<RestErrorMessage> inactiveException(InactiveException exception){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

}