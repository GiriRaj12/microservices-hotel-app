package org.hotelapp.commons.Utilities;

public class InvalidPayloadException extends RuntimeException {
    public InvalidPayloadException(String message){
        super(message);
    }
}
