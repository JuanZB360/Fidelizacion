package com.Fidelizacion.Registro_Marca.servicios.ubicacionServicio;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import com.Fidelizacion.Registro_Marca.repositorio.IUbicacionRepositorio;
import com.Fidelizacion.Registro_Marca.validaciones.ubicacionValidacion.IUbicacionValidacion;

import lombok.RequiredArgsConstructor;

@Service  
@RequiredArgsConstructor 
public class ImpUbicacionServicio implements IUbicacionServicio {

        private final IUbicacionValidacion validacionUbicacion;
    private final IUbicacionRepositorio repositorioUbicacion;

        @Override
        @Transactional
        public UbicacionResponseDTO crearUbicacion(UbicacionRequestDTO datos) {
        Ubicacion ubicacion = datos.toEntity();
        validacionUbicacion.validarUbicacion(ubicacion);

        return UbicacionResponseDTO.fromEntity(
            repositorioUbicacion.save(ubicacion));
        }

        @Override
        @Transactional(readOnly = true)
        public UbicacionResponseDTO buscarUbicacionId(UUID id) {
        Ubicacion ubicacion = repositorioUbicacion.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                "La ubicación no existe: " + id));

        return UbicacionResponseDTO.fromEntity(ubicacion);
        }

        @Override
        @Transactional(readOnly = true)
        public List<UbicacionResponseDTO> listarUbicaciones() {
        return repositorioUbicacion.findAll()
            .stream()
            .map(UbicacionResponseDTO::fromEntity)
            .toList();
        }

        @Override
        @Transactional
        public UbicacionResponseDTO actualizarUbicacion(
            UUID id,
            UbicacionRequestDTO datos) {
        Ubicacion ubicacion = repositorioUbicacion.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                "La ubicación no existe: " + id));

        ubicacion.setDireccion(datos.direccion());
        ubicacion.setCiudad(datos.ciudad());
        ubicacion.setDepartamento(datos.departamento());
        ubicacion.setPais(datos.pais());

        validacionUbicacion.validarUbicacion(ubicacion);

        return UbicacionResponseDTO.fromEntity(
            repositorioUbicacion.save(ubicacion));
        }

        @Override
        @Transactional(readOnly = true)
        public List<UsuarioResponseCompleto> buscarUsuariosUbicacion(UUID id) {
        Ubicacion ubicacion = repositorioUbicacion.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                "La ubicación no existe: " + id));

        return ubicacion.getUsuarios()
            .stream()
            .map(UsuarioResponseCompleto::fromEntity)
            .toList();
        }
}
