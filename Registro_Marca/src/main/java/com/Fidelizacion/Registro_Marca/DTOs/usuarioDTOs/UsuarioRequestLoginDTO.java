package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioRequestLogin", description = "Credenciales para registrar un usuario")
public record UsuarioRequestLoginDTO(
    @Schema(description = "Correo electrónico del usuario", example = "ana@example.com")
    String email,
    @Schema(description = "Contraseña del usuario", example = "ClaveSegura1!", accessMode = Schema.AccessMode.WRITE_ONLY)
    String contrasena
) {

    public Usuario toEntity(){
        return Usuario.builder()
        .email(email)
        .constrasena(contrasena)
        .build();
    }

}
