package com.Fidelizacion.Registro_Marca.controladores.UbicacionControlador;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.servicios.ubicacionServicio.IUbicacionServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ubicacion")
@RequiredArgsConstructor
@Tag(name = "Ubicaciones", description = "Operaciones para gestionar ubicaciones")
public class UbicacionesControlador {

    private final IUbicacionServicio ubicacionServicio;

    @PostMapping
    @Operation(summary = "Crear ubicación", description = "Registra una nueva ubicación geográfica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ubicación creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de ubicación inválidos")
    })
    public UbicacionResponseDTO crearUbicacion(
            @RequestBody UbicacionRequestDTO datos) {
        return ubicacionServicio.crearUbicacion(datos);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar ubicación", description = "Actualiza los datos de una ubicación existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ubicación actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos"),
        @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
    })
    public UbicacionResponseDTO actualizarUbicacion(
            @PathVariable UUID id,
            @RequestBody UbicacionRequestDTO datos) {
        return ubicacionServicio.actualizarUbicacion(id, datos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ubicación", description = "Obtiene una ubicación por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ubicación encontrada"),
        @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
    })
    public UbicacionResponseDTO buscarUbicacionId(@PathVariable UUID id) {
        return ubicacionServicio.buscarUbicacionId(id);
    }

    @GetMapping
    @Operation(summary = "Listar ubicaciones", description = "Obtiene todas las ubicaciones registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ubicaciones obtenidas correctamente")
    })
    public List<UbicacionResponseDTO> listarUbicaciones() {
        return ubicacionServicio.listarUbicaciones();
    }

    @GetMapping("/{id}/usuarios")
    @Operation(summary = "Listar usuarios de una ubicación", description = "Obtiene los usuarios asociados a una ubicación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente"),
        @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
    })
    public List<UsuarioResponseCompleto> buscarUsuariosUbicacion(
            @PathVariable UUID id) {
        return ubicacionServicio.buscarUsuariosUbicacion(id);
    }
}

