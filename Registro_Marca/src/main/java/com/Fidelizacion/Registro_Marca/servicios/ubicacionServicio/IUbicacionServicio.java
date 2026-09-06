package com.Fidelizacion.Registro_Marca.servicios.ubicacionServicio;

import java.util.List;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;


public interface IUbicacionServicio {

	UbicacionResponseDTO crearUbicacion(UbicacionRequestDTO datos);
	UbicacionResponseDTO buscarUbicacionId(UUID id);
	List<UbicacionResponseDTO> listarUbicaciones();
	UbicacionResponseDTO actualizarUbicacion(UUID id, UbicacionRequestDTO datos);
	List<UsuarioResponseCompleto> buscarUsuariosUbicacion(UUID id);
}
