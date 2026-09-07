package com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TipoDocumentoRequest", description = "Referencia a un tipo de documento existente")
public record TipoDocumentoRequestDTO(
    @Schema(description = "Identificador del tipo de documento existente", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id
) {

    public TipoDocumento toEntity(){
        return TipoDocumento.builder()
        .id(id)
        .build();
    }

}

