package com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Marca;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MarcaRequest", description = "Referencia a una marca existente")
public record MarcaRequestDTO(
    @Schema(description = "Identificador de la marca existente", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id
) {

    public Marca toEntity(){
        return Marca.builder()
        .id(id)
        .build();
    }

}
