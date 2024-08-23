package br.com.barbermanager.barbershopmanagement.exception;

public class AlreadyActiveException extends RuntimeException{

    public AlreadyActiveException(String message) {
        super(message);
    }

}
