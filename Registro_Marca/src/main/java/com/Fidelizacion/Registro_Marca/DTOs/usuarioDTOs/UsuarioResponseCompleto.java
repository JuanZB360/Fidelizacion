package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.time.LocalDate;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.TipoDocumento;

public record UsuarioResponseCompleto(
    UUID id,
    String nombre,
    String apellido,
    String email,
    TipoDocumento tipoDocumento,
    String numeroDocumento,
    LocalDate fechaNacimiento,
    Ubicacion direccion,
    Marca marca
) {

    public static UsuarioResponseCompleto fromEntity(Usuario usuario){
        return new UsuarioResponseCompleto(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getEmail(),
            usuario.getTipoDocumento(),
            usuario.getNumeroDocumento(),
            usuario.getFechaNacimiento(),
            usuario.getDireccion(),
            usuario.getMarca()
        );
    }

}
