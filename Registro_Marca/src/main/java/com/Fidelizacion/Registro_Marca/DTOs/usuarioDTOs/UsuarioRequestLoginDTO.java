package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import com.Fidelizacion.Registro_Marca.modelos.Usuario;

public record UsuarioRequestLoginDTO(
    String email,
    String contrasena
) {

    public Usuario toEntity(){
        return Usuario.builder()
        .email(email)
        .constrasena(contrasena)
        .build();
    }

}
