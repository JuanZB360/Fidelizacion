package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.time.LocalDate;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.Roles;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioRequestCrear", description = "Datos completos para registrar un usuario")
public record UsuarioRequestCrearDTO(
    @Schema(description = "Nombre del usuario", example = "Ana")
    String nombre,
    @Schema(description = "Apellido del usuario", example = "García")
    String apellido,
    @Schema(description = "Correo electrónico del usuario", example = "ana@example.com")
    String email,
    @Schema(description = "Contraseña del usuario", example = "ClaveSegura1!", accessMode = Schema.AccessMode.WRITE_ONLY)
    String contrasena,
    @Schema(description = "Rol del usuario (opcional, por defecto CLIENTE)", example = "CLIENTE")
    Roles rol,
    @Schema(description = "Tipo de documento existente")
    TipoDocumentoRequestDTO tipoDocumento,
    @Schema(description = "Número de documento", example = "1234567890")
    String numeroDocumento,
    @Schema(description = "Fecha de nacimiento", example = "1995-06-15")
    LocalDate fechaNacimiento,
    @Schema(description = "Dirección del usuario")
    UbicacionRequestDTO direccion,
    @Schema(description = "Marca existente asociada al usuario")
    MarcaRequestDTO marca
) {

    public Usuario toEntity() {
        return Usuario.builder()
            .nombre(nombre != null ? nombre.trim() : null)
            .apellido(apellido != null ? apellido.trim() : null)
            .email(email != null ? email.trim() : null)
            .constrasena(contrasena)
            .rol(rol != null ? rol : Roles.CLIENTE)
            .tipoDocumento(tipoDocumento != null ? tipoDocumento.toEntity() : null)
            .numeroDocumento(numeroDocumento != null ? numeroDocumento.trim() : null)
            .fechaNacimiento(fechaNacimiento)
            .direccion(direccion != null ? direccion.toEntity() : null)
            .marca(marca != null ? marca.toEntity() : null)
            .build();
    }

}

