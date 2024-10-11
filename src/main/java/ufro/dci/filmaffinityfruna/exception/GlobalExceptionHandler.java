package ufro.dci.filmaffinityfruna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ufro.dci.filmaffinityfruna.utils.ResponseUtil;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejar excepciones genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        return ResponseUtil.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado: " + ex.getMessage());
    }

    // Manejar errores de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(field, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Manejar violaciones de integridad en la base de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseExceptions(DataIntegrityViolationException ex) {
        return ResponseUtil.createResponse(HttpStatus.CONFLICT, "Error de integridad en la base de datos: " + ex.getMessage());
    }

}

