package com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;

public record UbicacionResponseDTO(
    UUID id,
    String direccion,
    String ciudad,
    String departamento,
    String pais
) {

    public static UbicacionResponseDTO fromEntity(Ubicacion ubicacion){
        return new UbicacionResponseDTO(
            ubicacion.getId(),
            ubicacion.getDireccion(), 
            ubicacion.getCiudad(),
            ubicacion.getDepartamento(),
            ubicacion.getPais()
        );
    }
}
