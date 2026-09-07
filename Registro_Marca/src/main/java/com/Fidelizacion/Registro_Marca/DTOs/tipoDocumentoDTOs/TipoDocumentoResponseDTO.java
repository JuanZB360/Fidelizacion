package com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TipoDocumentoResponse", description = "Información de un tipo de documento")
public record TipoDocumentoResponseDTO(
    @Schema(description = "Identificador del tipo de documento")
    UUID id,
    @Schema(description = "Nombre del tipo de documento", example = "Cédula de Ciudadanía")
    String nombre,
    @Schema(description = "Abreviatura o sigla del tipo de documento", example = "CC")
    String abreviatura
) {

    public static TipoDocumentoResponseDTO fromEntity(TipoDocumento tipoDocumento){
        if (tipoDocumento == null) {
            return null;
        }
        return new TipoDocumentoResponseDTO(
            tipoDocumento.getId(),
            tipoDocumento.getNombre(),
            tipoDocumento.getAbreviatura()
        ); 
    }

}

