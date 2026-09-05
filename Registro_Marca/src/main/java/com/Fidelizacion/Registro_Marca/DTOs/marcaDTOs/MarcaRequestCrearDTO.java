package com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs;

import com.Fidelizacion.Registro_Marca.modelos.Marca;

public record MarcaRequestCrearDTO(
    String nombre
) {

    public Marca toEntity(){
        return Marca.builder()
        .nombre(nombre.trim())
        .build();
    }

}
