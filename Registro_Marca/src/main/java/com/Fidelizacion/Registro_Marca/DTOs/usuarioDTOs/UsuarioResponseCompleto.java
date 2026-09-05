package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;

import java.time.LocalDate;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionResponseDTO;
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
    UbicacionResponseDTO direccion,
    MarcaResponseDTO marca
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
            UbicacionResponseDTO.fromEntity(usuario.getDireccion()),
            MarcaResponseDTO.fromEntity(usuario.getMarca())
        );
    }

}
