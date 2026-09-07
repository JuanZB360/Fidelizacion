package com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs;

import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import io.swagger.v3.oas.annotations.media.Schema;

/*

    datos de la ubicacion:
     dirección,
     ciudad,
     departamento,
     país 

*/

@Schema(name = "UbicacionRequest", description = "Datos de una ubicación")
public record UbicacionRequestDTO(
    @Schema(description = "Dirección", example = "Calle 10 # 20-30")
    String direccion,
    @Schema(description = "Ciudad", example = "Bogotá")
    String ciudad,
    @Schema(description = "Departamento", example = "Cundinamarca")
    String departamento,
    @Schema(description = "País", example = "Colombia")
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
