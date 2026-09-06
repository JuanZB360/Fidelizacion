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
@Tag(name = "Usuarios", description = "Operaciones para gestionar usuarios")
public class UsuariosControlador {

    private final IUsuarioServicio usuarioServicio;

    @PostMapping
        @Operation(summary = "Crear usuario", description = "Registra un usuario con sus credenciales")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
        })
    public UsuarioResponseLoginDTO crearUsuario(
            @RequestBody UsuarioRequestLoginDTO datos) {
        return usuarioServicio.crearUsuario(datos);
    }

    @PutMapping("/{id}/informacion")
        @Operation(summary = "Completar información", description = "Completa la información personal de un usuario")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Información completada correctamente"),
            @ApiResponse(responseCode = "400", description = "Información inválida"),
            @ApiResponse(responseCode = "404", description = "Usuario o marca no encontrada")
        })
    public UsuarioResponseCompleto completarInformacion(
            @PathVariable UUID id,
            @RequestBody UsuarioRequestCompletarInformacioDTO informacion) {
        return usuarioServicio.completarInformacio(id, informacion);
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
