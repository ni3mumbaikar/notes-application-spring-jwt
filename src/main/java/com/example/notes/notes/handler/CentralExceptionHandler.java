package com.example.notes.notes.handler;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class CentralExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleDatabaseException(SQLException sqlException) {
        return new ResponseEntity<>("Database Error : "+ sqlException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleJwtException(SignatureException jwtException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT Token Error : Invalid or Expired token");
    }

}
