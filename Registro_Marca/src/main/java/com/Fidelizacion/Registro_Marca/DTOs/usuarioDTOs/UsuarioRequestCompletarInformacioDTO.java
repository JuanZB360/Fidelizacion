package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.time.LocalDate;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.TipoDocumento;

public record UsuarioRequestCompletarInformacioDTO(
    UUID id,
    String nombre,
    String apellido,
    TipoDocumento tipoDocumento,
    String numeroDocumento,
    LocalDate fechaNacimiento,
    Ubicacion direccion,
    Marca marca
) {

    public Usuario toEntity(){
        return Usuario.builder()
        .id(id)
        .nombre(nombre)
        .apellido(apellido)
        .tipoDocumento(tipoDocumento)
        .numeroDocumento(numeroDocumento)
        .fechaNacimiento(fechaNacimiento)
        .direccion(direccion)
        .marca(marca)
        .build();
    }

}
