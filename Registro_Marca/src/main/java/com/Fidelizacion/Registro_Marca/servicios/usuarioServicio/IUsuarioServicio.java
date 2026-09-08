package com.Fidelizacion.Registro_Marca.servicios.usuarioServicio;

import java.util.List;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;

public interface IUsuarioServicio {

    UsuarioResponseCompleto crearUsuario(UsuarioRequestCrearDTO usuario);
    UsuarioResponseCompleto buscarUsuarioId(UUID id);
    List<UsuarioResponseCompleto> listarUsuarios();
    UsuarioResponseCompleto actualuzarUsuario(UUID id, UsuarioRequestActualizarDTO datos);
    void enviarEmailBienvenida(Usuario usuario);

}
