package com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TipoDocumentoRequestActualizar", description = "Datos para actualizar un tipo de documento")
public record TipoDocumentoRequestActualizarDTO(
    @Schema(description = "Nuevo nombre del tipo de documento", example = "Cédula de Extranjería")
    String nombre,
    @Schema(description = "Nueva abreviatura del tipo de documento", example = "CE")
    String abreviatura
) {
}

