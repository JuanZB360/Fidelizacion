package com.Fidelizacion.Registro_Marca.controladores.UsuarioControlador;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCompletarInformacioDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestLoginDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseLoginDTO;
import com.Fidelizacion.Registro_Marca.servicios.usuarioServicio.IUsuarioServicio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor 
public class UsuariosControlador {

    private final IUsuarioServicio usuarioServicio;

    @PostMapping
    public UsuarioResponseLoginDTO crearUsuario(
            @RequestBody UsuarioRequestLoginDTO datos) {
        return usuarioServicio.crearUsuario(datos);
    }

    @PutMapping("/{id}/informacion")
    public UsuarioResponseCompleto completarInformacion(
            @PathVariable UUID id,
            @RequestBody UsuarioRequestCompletarInformacioDTO informacion) {
        return usuarioServicio.completarInformacio(id, informacion);
    }

    @GetMapping("/{id}")
    public UsuarioResponseCompleto buscarUsuarioId(@PathVariable UUID id) {
        return usuarioServicio.buscarUsuarioId(id);
    }

    @GetMapping
    public List<UsuarioResponseCompleto> listarUsuarios() {
        return usuarioServicio.listarUsuarios();
    }

    @PatchMapping("/{id}")
    public UsuarioResponseCompleto actualizarUsuario(
            @PathVariable UUID id,
            @RequestBody UsuarioRequestActualizarDTO datos) {
        return usuarioServicio.actualuzarUsuario(id, datos);
    }

}
