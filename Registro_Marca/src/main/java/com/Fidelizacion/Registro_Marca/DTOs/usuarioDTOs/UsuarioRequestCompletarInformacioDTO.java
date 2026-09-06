package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.time.LocalDate;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.TipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioRequestCompletarInformacion", description = "Información personal para completar un usuario")
public record UsuarioRequestCompletarInformacioDTO(
    @Schema(description = "Identificador del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,
    @Schema(description = "Nombre del usuario", example = "Ana")
    String nombre,
    @Schema(description = "Apellido del usuario", example = "García")
    String apellido,
    @Schema(description = "Tipo de documento", example = "CC")
    TipoDocumento tipoDocumento,
    @Schema(description = "Número de documento", example = "1234567890")
    String numeroDocumento,
    @Schema(description = "Fecha de nacimiento", example = "1995-06-15")
    LocalDate fechaNacimiento,
    @Schema(description = "Dirección del usuario")
    UbicacionRequestDTO direccion,
    @Schema(description = "Marca existente asociada al usuario")
    MarcaRequestDTO marca
) {

    public Usuario toEntity(){
        return Usuario.builder()
        .id(id)
        .nombre(nombre)
        .apellido(apellido)
        .tipoDocumento(tipoDocumento)
        .numeroDocumento(numeroDocumento)
        .fechaNacimiento(fechaNacimiento)
        .direccion(direccion.toEntity())
        .marca(marca.toEntity())
        .build();
    }

}
