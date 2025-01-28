package com.group.Messenger.core.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorInfo> handleRuntimeExceptionForOthers(HttpServletRequest req, RuntimeException ex) {
        System.out.println("hello-RuntimeException : " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body(new ErrorInfo(req.getRequestURL().toString(), ex.getLocalizedMessage(), String.valueOf(ex.getMessage()), new Date()));
    }

    @ExceptionHandler(CustomGroupMessengerException.class)
    public ResponseEntity<ErrorInfo> handleCustomGroupMessengerException(CustomGroupMessengerException customGroupMessengerException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(
                null,
                "CustomGroupMessengerException",
                customGroupMessengerException.getMessage(),
                new Date()
        ));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorInfo> handleNullPointerException(HttpServletRequest httpServletRequest, NullPointerException nullPointerException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(
                httpServletRequest.getRequestURL().toString(),
                "NullPointerException",
                nullPointerException.getMessage(),
                new Date()
        ));
    }
}
