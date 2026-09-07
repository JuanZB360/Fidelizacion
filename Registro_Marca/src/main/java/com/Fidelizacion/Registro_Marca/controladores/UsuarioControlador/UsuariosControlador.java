package com.Fidelizacion.Registro_Marca.controladores.UsuarioControlador;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.servicios.usuarioServicio.IUsuarioServicio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor 
@Tag(name = "Usuarios", description = "Operaciones para gestionar usuarios")
public class UsuariosControlador {

    private final IUsuarioServicio usuarioServicio;

    @PostMapping
        @Operation(summary = "Crear usuario", description = "Registra un usuario con toda su información personal, ubicación y marca")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos"),
            @ApiResponse(responseCode = "404", description = "Marca o tipo de documento no encontrado")
        })
    public UsuarioResponseCompleto crearUsuario(
            @RequestBody UsuarioRequestCrearDTO datos) {
        return usuarioServicio.crearUsuario(datos);
    }

    @GetMapping("/{id}")
        @Operation(summary = "Buscar usuario", description = "Obtiene un usuario por su identificador")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
    public UsuarioResponseCompleto buscarUsuarioId(@PathVariable UUID id) {
        return usuarioServicio.buscarUsuarioId(id);
    }

    @GetMapping
        @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente")
        })
    public List<UsuarioResponseCompleto> listarUsuarios() {
        return usuarioServicio.listarUsuarios();
    }

    @PatchMapping("/{id}")
        @Operation(summary = "Actualizar usuario", description = "Actualiza parcialmente los datos de un usuario")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
    public UsuarioResponseCompleto actualizarUsuario(
            @PathVariable UUID id,
            @RequestBody UsuarioRequestActualizarDTO datos) {
        return usuarioServicio.actualuzarUsuario(id, datos);
    }

}
