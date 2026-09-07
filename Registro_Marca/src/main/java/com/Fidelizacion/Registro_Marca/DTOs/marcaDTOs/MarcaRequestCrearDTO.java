package com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs;

import com.Fidelizacion.Registro_Marca.modelos.Marca;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MarcaRequestCrear", description = "Datos para crear una marca")
public record MarcaRequestCrearDTO(
    @Schema(description = "Nombre de la marca", example = "Marca Central")
    String nombre
) {

    public Marca toEntity(){
        return Marca.builder()
        .nombre(nombre != null ? nombre.trim() : null)
        .build();
    }

}
