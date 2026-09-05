package com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs;

import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;

/*

    datos de la ubicacion:
     dirección,
     ciudad,
     departamento,
     país 

*/

public record UbicacionRequestDTO(
    String direccion,
    String ciudad,
    String departamento,
    String pais
) {

    public Ubicacion toEntity(){
        return Ubicacion.builder()
        .direccion(direccion)
        .ciudad(ciudad)
        .departamento(departamento)
        .pais(pais)
        .build();
    }

}
