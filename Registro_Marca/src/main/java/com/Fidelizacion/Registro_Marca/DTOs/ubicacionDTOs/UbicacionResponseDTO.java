package com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UbicacionResponse", description = "Información de una ubicación")
public record UbicacionResponseDTO(
    @Schema(description = "Identificador de la ubicación")
    UUID id,
    @Schema(description = "Dirección", example = "Calle 10 # 20-30")
    String direccion,
    @Schema(description = "Ciudad", example = "Bogotá")
    String ciudad,
    @Schema(description = "Departamento", example = "Cundinamarca")
    String departamento,
    @Schema(description = "País", example = "Colombia")
    String pais
) {

    public static UbicacionResponseDTO fromEntity(Ubicacion ubicacion){
        if (ubicacion == null) {
            return null;
        }
        return new UbicacionResponseDTO(
            ubicacion.getId(),
            ubicacion.getDireccion(), 
            ubicacion.getCiudad(),
            ubicacion.getDepartamento(),
            ubicacion.getPais()
        );
    }
}
