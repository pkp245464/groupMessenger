package com.group.Messenger.core.exceptions;

public class CustomGroupMessengerException extends RuntimeException{

    public CustomGroupMessengerException() {
        super();
    }

    public CustomGroupMessengerException(String message) {
        super(message);
    }

    public CustomGroupMessengerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomGroupMessengerException(Throwable cause) {
        super(cause);
    }
}
