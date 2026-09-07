package com.Fidelizacion.Registro_Marca.controladores.ExcepcionControlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Fidelizacion.Registro_Marca.DTOs.errorDTOs.ErrorResponseDTO;
import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;

@RestControllerAdvice
public class ControladorExcepciones {

    @ExceptionHandler(ValidacionExcepcion.class)
    public ResponseEntity<ErrorResponseDTO> manejarValidacionExcepcion(ValidacionExcepcion ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO error = ErrorResponseDTO.de(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                ex.getCampo()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> manejarIllegalArgumentException(IllegalArgumentException ex) {
        String mensaje = ex.getMessage() != null ? ex.getMessage() : "Argumento inválido";
        boolean esNoEncontrado = mensaje.toLowerCase().contains("no existe")
                || mensaje.toLowerCase().contains("no encontrad");

        HttpStatus status = esNoEncontrado ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        ErrorResponseDTO error = ErrorResponseDTO.de(
                status.value(),
                status.getReasonPhrase(),
                mensaje
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> manejarMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String campo = fieldError != null ? fieldError.getField() : null;
        String mensaje = fieldError != null ? fieldError.getDefaultMessage() : "Error de validación en la solicitud";

        ErrorResponseDTO error = ErrorResponseDTO.de(
                status.value(),
                status.getReasonPhrase(),
                mensaje,
                campo
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> manejarHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO error = ErrorResponseDTO.de(
                status.value(),
                status.getReasonPhrase(),
                "El cuerpo de la solicitud no tiene un formato JSON válido o contiene tipos incompatibles"
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> manejarExcepcionGenerica(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDTO error = ErrorResponseDTO.de(
                status.value(),
                status.getReasonPhrase(),
                "Ha ocurrido un error interno en el servidor"
        );
        return ResponseEntity.status(status).body(error);
    }
}

