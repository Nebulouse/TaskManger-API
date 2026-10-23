    package com.example.taskmanager.exception;
    
    
    import jakarta.persistence.EntityNotFoundException;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;
    
    import java.util.Map;
    
    @RestControllerAdvice
    public class GlobalExceptionHandler {
    
        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Map<String, String>> tratarNaoEncontrada(
                EntityNotFoundException ex) {
            Map<String, String> erro = Map.of(
                    "erro", ex.getMessage()
            );
    
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(erro);
        }
    
    }
