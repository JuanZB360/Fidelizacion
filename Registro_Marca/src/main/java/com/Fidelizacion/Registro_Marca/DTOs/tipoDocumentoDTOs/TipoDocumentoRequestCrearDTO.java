package com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs;

import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TipoDocumentoRequestCrear", description = "Datos para crear un tipo de documento")
public record TipoDocumentoRequestCrearDTO(
    @Schema(description = "Nombre del tipo de documento", example = "Cédula de Ciudadanía")
    String nombre,
    @Schema(description = "Abreviatura o código del tipo de documento", example = "CC")
    String abreviatura
) {

    public TipoDocumento toEntity(){
        return TipoDocumento.builder()
        .nombre(nombre != null ? nombre.trim() : null)
        .abreviatura(abreviatura != null ? abreviatura.trim().toUpperCase() : null)
        .build();
    }

}

