package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Usuario;

import lombok.Builder;

@Builder 
public record UsuarioResponseLoginDTO(
    UUID id,
    String email
) {

    public static UsuarioResponseLoginDTO fromEntity(Usuario usuario){
        return new UsuarioResponseLoginDTO(usuario.getId(), usuario.getEmail());
    }
}
