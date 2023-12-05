package dev.patika.vetManagementSystem.core.exception;

public class DataNotFoundException  extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }
}
