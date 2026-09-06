package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.Roles;

import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder 
@Schema(name = "UsuarioResponseLogin", description = "Respuesta básica del usuario")
public record UsuarioResponseLoginDTO(
    @Schema(description = "Identificador del usuario")
    UUID id,
    @Schema(description = "Correo electrónico del usuario", example = "ana@example.com")
    String email,
    @Schema(description = "Rol del usuario", example = "CLIENTE", accessMode = Schema.AccessMode.READ_ONLY)
    Roles rol
) {

    public static UsuarioResponseLoginDTO fromEntity(Usuario usuario){
        return new UsuarioResponseLoginDTO(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getRol()
        );
    }
}
