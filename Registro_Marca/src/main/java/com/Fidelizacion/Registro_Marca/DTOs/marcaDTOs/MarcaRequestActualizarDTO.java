package com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MarcaRequestActualizar", description = "Datos para actualizar una marca")
public record MarcaRequestActualizarDTO(
        @Schema(description = "Nuevo nombre de la marca", example = "Marca Central")
        String nombre
) {
}
