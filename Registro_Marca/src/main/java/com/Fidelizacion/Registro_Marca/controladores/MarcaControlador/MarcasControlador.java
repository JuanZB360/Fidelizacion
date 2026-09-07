package com.Fidelizacion.Registro_Marca.controladores.MarcaControlador;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.servicios.MarcaServicio.IMarcaServicio;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/marca")
@RequiredArgsConstructor
@Tag(name = "Marcas", description = "Operaciones para gestionar marcas")
public class MarcasControlador {

    private final IMarcaServicio marcaServicio;

    @PostMapping
        @Operation(summary = "Crear marca", description = "Registra una nueva marca")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Nombre inválido o duplicado")
        })
    public MarcaResponseDTO crearMarca(
            @RequestBody MarcaRequestCrearDTO datos) {
        return marcaServicio.crearMarca(datos);
    }

    @PatchMapping("/{id}")
        @Operation(summary = "Actualizar marca", description = "Actualiza el nombre de una marca")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Nombre inválido o duplicado"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
        })
    public MarcaResponseDTO actualizarMarca(
            @PathVariable UUID id,
            @RequestBody MarcaRequestActualizarDTO datos) {
        return marcaServicio.actualizarMarca(id, datos);
    }

    @GetMapping("/{id}")
        @Operation(summary = "Buscar marca", description = "Obtiene una marca por su identificador")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca encontrada"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
        })
    public MarcaResponseDTO buscarMarcaId(@PathVariable UUID id) {
        return marcaServicio.buscarMarcaId(id);
    }

    @GetMapping
        @Operation(summary = "Listar marcas", description = "Obtiene todas las marcas registradas")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marcas obtenidas correctamente")
        })
    public List<MarcaResponseDTO> listarMarcas() {
        return marcaServicio.listarMarcas();
    }

    @GetMapping("/{id}/usuarios")
        @Operation(summary = "Listar usuarios de una marca", description = "Obtiene los usuarios asociados a una marca")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
        })
    public List<UsuarioResponseCompleto> buscarUsuariosMarca(
            @PathVariable UUID id) {
        return marcaServicio.buscarUsuariosMarca(id);
    }
}
