package com.Fidelizacion.Registro_Marca.controladores.MarcaControlador;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.servicios.MarcaServicio.IMarcaServicio;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/marca")
@RequiredArgsConstructor
public class MarcasControlador {

    private final IMarcaServicio marcaServicio;

    @PostMapping
    public MarcaResponseDTO crearMarca(
            @RequestBody MarcaRequestCrearDTO datos) {
        return marcaServicio.crearMarca(datos);
    }

    @GetMapping("/{id}")
    public MarcaResponseDTO buscarMarcaId(@PathVariable UUID id) {
        return marcaServicio.buscarMarcaId(id);
    }

    @GetMapping
    public List<MarcaResponseDTO> listarMarcas() {
        return marcaServicio.listarMarcas();
    }

    @GetMapping("/{id}/usuarios")
    public List<UsuarioResponseCompleto> buscarUsuariosMarca(
            @PathVariable UUID id) {
        return marcaServicio.buscarUsuariosMarca(id);
    }
}
