package com.Fidelizacion.Registro_Marca.DTOs.errorDTOs;

import java.time.LocalDateTime;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Estructura estándar de respuesta para errores")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
    @Schema(description = "Marca de tiempo del error", example = "2026-09-06T20:30:00")
    LocalDateTime timestamp,

    @Schema(description = "Código de estado HTTP", example = "400")
    int estado,

    @Schema(description = "Nombre del estado HTTP", example = "Bad Request")
    String error,

    @Schema(description = "Mensaje detallado del error", example = "Se encontraron errores de validación")
    String mensaje,

    @Schema(description = "Campo que causó el error (si aplica)", example = "nombre")
    String campo,

    @Schema(description = "Mapa de campos con sus respectivos mensajes de error")
    Map<String, String> errores
) {
    public static ErrorResponseDTO de(int estado, String error, String mensaje, String campo, Map<String, String> errores) {
        return new ErrorResponseDTO(LocalDateTime.now(), estado, error, mensaje, campo, errores);
    }

    public static ErrorResponseDTO de(int estado, String error, String mensaje, String campo) {
        return new ErrorResponseDTO(LocalDateTime.now(), estado, error, mensaje, campo, null);
    }

    public static ErrorResponseDTO de(int estado, String error, String mensaje, Map<String, String> errores) {
        String unicoCampo = (errores != null && errores.size() == 1) ? errores.keySet().iterator().next() : null;
        return new ErrorResponseDTO(LocalDateTime.now(), estado, error, mensaje, unicoCampo, errores);
    }

    public static ErrorResponseDTO de(int estado, String error, String mensaje) {
        return new ErrorResponseDTO(LocalDateTime.now(), estado, error, mensaje, null, null);
    }
}

