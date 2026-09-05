package com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Marca;

public record MarcaRequestDTO(
    UUID id
) {

    public Marca toEntity(){
        return Marca.builder()
        .id(id)
        .build();
    }

}
