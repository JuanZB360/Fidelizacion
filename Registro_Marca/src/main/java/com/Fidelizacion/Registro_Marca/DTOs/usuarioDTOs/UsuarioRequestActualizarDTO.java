package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;


import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;

public record UsuarioRequestActualizarDTO(
    UbicacionRequestDTO direccion,
    MarcaRequestDTO marca,
    String contrasena,
    String email
) {

    public Usuario toEntity(Usuario usuario){

        if (direccion != null) {
            usuario.setDireccion(direccion.toEntity());    
        }

        if (marca != null) {
            usuario.setMarca(marca.toEntity());
        }

        if (contrasena != null) {
            usuario.setConstrasena(contrasena);
        }

        if (email != null) {
            usuario.setEmail(email);
        }

        return usuario;
    }

}
