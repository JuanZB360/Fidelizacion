package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.time.LocalDate;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionResponseDTO;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.TipoDocumento;
import com.Fidelizacion.Registro_Marca.utils.Roles;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioResponseCompleto", description = "Información completa de un usuario")
public record UsuarioResponseCompleto(
    @Schema(description = "Identificador del usuario")
    UUID id,
    @Schema(description = "Nombre del usuario")
    String nombre,
    @Schema(description = "Apellido del usuario")
    String apellido,
    @Schema(description = "Correo electrónico del usuario")
    String email,
    @Schema(description = "Rol del usuario", example = "CLIENTE", accessMode = Schema.AccessMode.READ_ONLY)
    Roles rol,
    @Schema(description = "Tipo de documento")
    TipoDocumento tipoDocumento,
    @Schema(description = "Número de documento")
    String numeroDocumento,
    @Schema(description = "Fecha de nacimiento")
    LocalDate fechaNacimiento,
    @Schema(description = "Dirección asociada")
    UbicacionResponseDTO direccion,
    @Schema(description = "Marca asociada")
    MarcaResponseDTO marca
) {

    public static UsuarioResponseCompleto fromEntity(Usuario usuario){
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponseCompleto(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getEmail(),
            usuario.getRol(),
            usuario.getTipoDocumento(),
            usuario.getNumeroDocumento(),
            usuario.getFechaNacimiento(),
            usuario.getDireccion() != null ? UbicacionResponseDTO.fromEntity(usuario.getDireccion()) : null,
            usuario.getMarca() != null ? MarcaResponseDTO.fromEntity(usuario.getMarca()) : null
        );
    }

}
