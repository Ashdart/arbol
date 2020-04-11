package com.caece.arbol.controller.advice;

import com.caece.arbol.exception.ArbolAlreadyExistException;
import com.caece.arbol.exception.ArbolNotFoundException;
import com.caece.arbol.exception.CreateArbolException;
import com.caece.arbol.exception.EmptyRootException;
import com.caece.arbol.exception.NodoNotFoundException;
import com.caece.arbol.util.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> unexpectedException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(SimpleResponse.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build());
    }

    @ExceptionHandler(CreateArbolException.class)
    protected ResponseEntity<Object> handleArbolCreationException(CreateArbolException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(SimpleResponse.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build());
    }

    @ExceptionHandler(ArbolNotFoundException.class)
    protected ResponseEntity<Object> handleArbolNotFoundException(ArbolNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(SimpleResponse.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @ExceptionHandler(ArbolAlreadyExistException.class)
    protected ResponseEntity<Object> handleArbolExistException(ArbolAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(SimpleResponse.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
    }

    @ExceptionHandler(NodoNotFoundException.class)
    protected ResponseEntity<Object> handleNodoNotFoundException(NodoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(SimpleResponse.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @ExceptionHandler(EmptyRootException.class)
    protected ResponseEntity<Object> handleEmptyArbolException(EmptyRootException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(SimpleResponse.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NO_CONTENT)
                        .build());
    }

}
