package com.Fidelizacion.Registro_Marca.servicios.MarcaServicio;

import java.util.List;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;

public interface IMarcaServicio {

    MarcaResponseDTO buscarMarcaId(UUID id);
    List<MarcaResponseDTO> listarMarcas();
    MarcaResponseDTO crearMarca(MarcaRequestCrearDTO datos);
    MarcaResponseDTO actualizarMarca(UUID id, MarcaRequestActualizarDTO datos);
    List<UsuarioResponseCompleto> buscarUsuariosMarca(UUID id);

}
