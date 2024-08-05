package org.imures.cashregister.exceptions;

public class TooManyEntitiesRequestedException extends RuntimeException{
    public TooManyEntitiesRequestedException(String message) {
        super(message);
    }
}
