package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.util.UUID;

import com.Fidelizacion.Registro_Marca.modelos.Usuario;

import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder 
@Schema(name = "UsuarioResponseLogin", description = "Respuesta básica del usuario")
public record UsuarioResponseLoginDTO(
    @Schema(description = "Identificador del usuario")
    UUID id,
    @Schema(description = "Correo electrónico del usuario", example = "ana@example.com")
    String email
) {

    public static UsuarioResponseLoginDTO fromEntity(Usuario usuario){
        return new UsuarioResponseLoginDTO(
            usuario.getId(),
            usuario.getEmail()
        );
    }
}
