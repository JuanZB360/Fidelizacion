package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;


import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;

public record UsuarioRequestActualizarDTO(
    Ubicacion direccion,
    Marca marca
) {

    public Usuario toEntity(Usuario usuario){

        if (direccion != null) {
            usuario.setDireccion(direccion);    
        }

        if (marca != null) {
            usuario.setMarca(marca);
        }

        return usuario;
    }

}
