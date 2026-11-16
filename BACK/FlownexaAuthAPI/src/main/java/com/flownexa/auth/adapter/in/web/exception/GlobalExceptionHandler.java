package com.flownexa.auth.adapter.in.web.exception;

import com.flownexa.auth.domain.exception.CompanyAlreadyExistsException;
import com.flownexa.auth.domain.exception.CompanyNotFoundException;
import com.flownexa.auth.domain.exception.EmailAlreadyExistsException;
import com.flownexa.auth.domain.exception.InvalidCredentialsException;
import com.flownexa.auth.domain.exception.InvalidResetTokenException;
import com.flownexa.auth.domain.exception.UnauthorizedActionException;
import com.flownexa.business.exception.DepartmentAlreadyExistsException;
import com.flownexa.business.exception.DepartmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error", "Unauthorized",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailExists(EmailAlreadyExistsException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", HttpStatus.CONFLICT.value(),
                "error", "Conflict",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
    
    @ExceptionHandler(InvalidResetTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidResetToken(InvalidResetTokenException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Bad Request",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    
    @ExceptionHandler(CompanyAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleCompanyExists(CompanyAlreadyExistsException ex) {
    Map<String, Object> body = Map.of(
            "timestamp", Instant.now().toString(),
            "status", HttpStatus.CONFLICT.value(),
            "error", "Conflict",
            "message", ex.getMessage()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
    
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedActionException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", HttpStatus.FORBIDDEN.value(),
                "error", "Forbidden",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
    
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCompanyNotFound(CompanyNotFoundException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Not Found",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
    
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDepartmentNotFound(DepartmentNotFoundException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Not Found",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
    
    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDepartmentExists(DepartmentAlreadyExistsException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", HttpStatus.CONFLICT.value(),
                "error", "Conflict",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
    
        @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
        public ResponseEntity<Map<String, Object>> handleAccessDenied(
                org.springframework.security.access.AccessDeniedException ex) {
            Map<String, Object> body = Map.of(
                    "timestamp", Instant.now().toString(),
                    "status", HttpStatus.FORBIDDEN.value(),
                    "error", "Forbidden",
                    "message", "You do not have permission to perform this action"
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
        }
    
}