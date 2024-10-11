package ufro.dci.filmaffinityfruna.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    private ResponseUtil() {
    }

    /** Método estático para crear respuestas estándar*/
    public static ResponseEntity<Map<String, String>> createResponse(HttpStatus status, String message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", String.valueOf(status.value()));
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}

