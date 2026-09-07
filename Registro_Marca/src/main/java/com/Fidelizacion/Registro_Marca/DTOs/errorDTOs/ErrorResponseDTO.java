package com.Fidelizacion.Registro_Marca.DTOs.errorDTOs;

import java.time.LocalDateTime;

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

    @Schema(description = "Mensaje detallado del error", example = "El nombre debe tener al menos 3 letras")
    String mensaje,

    @Schema(description = "Campo que causó el error (si aplica)", example = "nombre")
    String campo
) {
    public static ErrorResponseDTO de(int estado, String error, String mensaje, String campo) {
        return new ErrorResponseDTO(LocalDateTime.now(), estado, error, mensaje, campo);
    }

    public static ErrorResponseDTO de(int estado, String error, String mensaje) {
        return new ErrorResponseDTO(LocalDateTime.now(), estado, error, mensaje, null);
    }
}

