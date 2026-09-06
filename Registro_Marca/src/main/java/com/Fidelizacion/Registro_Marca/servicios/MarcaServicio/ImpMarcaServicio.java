package com.Fidelizacion.Registro_Marca.servicios.MarcaServicio;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.repositorio.IMarcaRepositorio;
import com.Fidelizacion.Registro_Marca.validaciones.marcaValidacion.IMarcaValidacion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpMarcaServicio implements IMarcaServicio {

    private final IMarcaRepositorio repositorioMarca;
    private final IMarcaValidacion validacionMarca;

    @Override
    @Transactional(readOnly = true)
    public MarcaResponseDTO buscarMarcaId(UUID id) {
        Marca marca = repositorioMarca.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La marca no existe: " + id));

        return MarcaResponseDTO.fromEntity(marca);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarcaResponseDTO> listarMarcas() {
        return repositorioMarca.findAll()
                .stream()
                .map(MarcaResponseDTO::fromEntity)
                .toList();
    }

    @Override
    @Transactional 
    public MarcaResponseDTO crearMarca(MarcaRequestCrearDTO datos) {
        Marca marca = datos.toEntity();

        validacionMarca.validarCreacionMarca(marca, repositorioMarca.existsByNombre(marca.getNombre()));

        repositorioMarca.save(marca);

        return MarcaResponseDTO.fromEntity(marca);

    }

    @Override
    @Transactional
    public MarcaResponseDTO actualizarMarca(UUID id, MarcaRequestActualizarDTO datos) {
        Marca marca = repositorioMarca.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La marca no existe: " + id));

        String nombre = datos.nombre() == null ? null : datos.nombre().trim();
        validacionMarca.validarNombreMarca(nombre);
        validacionMarca.validarQueSeaUnico(
                repositorioMarca.existsByNombreAndIdNot(nombre, id));

        marca.setNombre(nombre);
        return MarcaResponseDTO.fromEntity(repositorioMarca.save(marca));
    }

    @Override
	@Transactional(readOnly = true)
	public List<UsuarioResponseCompleto> buscarUsuariosMarca(UUID id) {
		Marca marca = repositorioMarca.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"La marca no existe: " + id));

		return marca.getUsuarios()
				.stream()
				.map(UsuarioResponseCompleto::fromEntity)
				.toList();
	}
    
}
