package com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Marca;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MarcaResponse", description = "Información de una marca")
public record MarcaResponseDTO(
    @Schema(description = "Identificador de la marca")
    UUID id,
    @Schema(description = "Nombre de la marca", example = "Marca Central")
    String nombre
) {

    public static MarcaResponseDTO fromEntity(Marca marca){
        if (marca == null) {
            return null;
        }
        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNombre()
        ); 
    }

}
