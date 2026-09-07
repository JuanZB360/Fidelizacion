package com.Fidelizacion.Registro_Marca.controladores.TipoDocumentoControlador;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.servicios.tipoDocumentoServicio.ITipoDocumentoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipo-documento")
@RequiredArgsConstructor
@Tag(name = "Tipos de Documento", description = "Operaciones para gestionar tipos de documento")
public class TiposDocumentoControlador {

    private final ITipoDocumentoServicio tipoDocumentoServicio;

    @PostMapping
    @Operation(summary = "Crear tipo de documento", description = "Registra un nuevo tipo de documento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de documento creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o duplicados")
    })
    public TipoDocumentoResponseDTO crearTipoDocumento(
            @RequestBody TipoDocumentoRequestCrearDTO datos) {
        return tipoDocumentoServicio.crearTipoDocumento(datos);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar tipo de documento", description = "Actualiza los datos de un tipo de documento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de documento actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o duplicados"),
        @ApiResponse(responseCode = "404", description = "Tipo de documento no encontrado")
    })
    public TipoDocumentoResponseDTO actualizarTipoDocumento(
            @PathVariable UUID id,
            @RequestBody TipoDocumentoRequestActualizarDTO datos) {
        return tipoDocumentoServicio.actualizarTipoDocumento(id, datos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de documento", description = "Obtiene un tipo de documento por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de documento encontrado"),
        @ApiResponse(responseCode = "404", description = "Tipo de documento no encontrado")
    })
    public TipoDocumentoResponseDTO buscarTipoDocumentoId(@PathVariable UUID id) {
        return tipoDocumentoServicio.buscarTipoDocumentoId(id);
    }

    @GetMapping
    @Operation(summary = "Listar tipos de documento", description = "Obtiene todos los tipos de documento registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipos de documento obtenidos correctamente")
    })
    public List<TipoDocumentoResponseDTO> listarTiposDocumento() {
        return tipoDocumentoServicio.listarTiposDocumento();
    }

    @GetMapping("/{id}/usuarios")
    @Operation(summary = "Listar usuarios de un tipo de documento", description = "Obtiene los usuarios asociados a un tipo de documento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente"),
        @ApiResponse(responseCode = "404", description = "Tipo de documento no encontrado")
    })
    public List<UsuarioResponseCompleto> buscarUsuariosTipoDocumento(
            @PathVariable UUID id) {
        return tipoDocumentoServicio.buscarUsuariosTipoDocumento(id);
    }

}

