package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.time.LocalDate;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.TipoDocumento;

public record UsuarioRequestCompletarInformacioDTO(
    UUID id,
    String nombre,
    String apellido,
    TipoDocumento tipoDocumento,
    String numeroDocumento,
    LocalDate fechaNacimiento,
    UbicacionRequestDTO direccion,
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
