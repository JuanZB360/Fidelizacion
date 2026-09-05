package com.Fidelizacion.Registro_Marca.servicios.usuarioServicio;

import java.util.List;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCompletarInformacioDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestLoginDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseLoginDTO;

public interface IUsuarioServicio {

    UsuarioResponseLoginDTO crearUsuario(UsuarioRequestLoginDTO usuario);
    UsuarioResponseCompleto completarInformacio(UUID id,UsuarioRequestCompletarInformacioDTO informacion);
    UsuarioResponseCompleto buscarUsuarioId(UUID id);
    List<UsuarioResponseCompleto> listarUsuarios();
    UsuarioResponseCompleto actualuzarUsuario(UUID id, UsuarioRequestActualizarDTO datos);

}
