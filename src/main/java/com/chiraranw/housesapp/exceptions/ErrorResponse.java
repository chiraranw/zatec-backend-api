package com.chiraranw.housesapp.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponse() {
        timestamp=LocalDateTime.now();
    }

    public ErrorResponse( String message,HttpStatus status, String path) {
        this();
        this.status = status;
        this.message = message;
        this.path=path;
    }
}
