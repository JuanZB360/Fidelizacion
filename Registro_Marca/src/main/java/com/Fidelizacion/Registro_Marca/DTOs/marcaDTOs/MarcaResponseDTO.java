package com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Marca;

public record MarcaResponseDTO(
    UUID id,
    String nombre
) {

    public static MarcaResponseDTO fromEntity(Marca marca){
        return new MarcaResponseDTO(
            marca.getId(),
            marca.getNombre()
        ); 
    }

}
