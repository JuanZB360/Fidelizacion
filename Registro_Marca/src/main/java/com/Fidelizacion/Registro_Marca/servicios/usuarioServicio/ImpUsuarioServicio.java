package com.Fidelizacion.Registro_Marca.servicios.usuarioServicio;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.repositorio.IMarcaRepositorio;
import com.Fidelizacion.Registro_Marca.repositorio.ITipoDocumentoRepositorio;
import com.Fidelizacion.Registro_Marca.repositorio.IUbicacionRepositorio;
import com.Fidelizacion.Registro_Marca.repositorio.IUsuarioRepositorio;
import com.Fidelizacion.Registro_Marca.validaciones.usuarioValidacion.IUsuarioValidacion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpUsuarioServicio implements IUsuarioServicio {

    private final IUsuarioRepositorio repositorioUsuario;
	private final IMarcaRepositorio repositorioMarca;
	private final ITipoDocumentoRepositorio repositorioTipoDocumento;
	private final IUsuarioValidacion validacion;
	private final IUbicacionRepositorio repositorioUbicacion;

	@Override
	@Transactional 
	public UsuarioResponseCompleto crearUsuario(UsuarioRequestCrearDTO datos) {

		Map<String, String> errores = new LinkedHashMap<>();

		Marca marca = null;
		if (datos.marca() == null || datos.marca().id() == null) {
			errores.put("marca", "Selecciona una marca válida");
		} else {
			marca = repositorioMarca.findById(datos.marca().id()).orElse(null);
			if (marca == null) {
				errores.put("marca", "Selecciona una marca válida");
			}
		}

		TipoDocumento tipoDocumento = null;
		if (datos.tipoDocumento() == null || datos.tipoDocumento().id() == null) {
			errores.put("tipoDocumento", "Debes seleccionar un tipo de documento válido");
		} else {
			tipoDocumento = repositorioTipoDocumento.findById(datos.tipoDocumento().id()).orElse(null);
			if (tipoDocumento == null) {
				errores.put("tipoDocumento", "Selecciona un tipo de documento válido");
			}
		}

		Ubicacion ubicacion = null;
		if (datos.direccion() == null) {
			errores.put("direccion", "Debes proporcionar una ubicación válida");
		} else {
			ubicacion = repositorioUbicacion.findByDireccionAndCiudadAndDepartamentoAndPais(
					datos.direccion().direccion(), datos.direccion().ciudad(), datos.direccion().departamento(), datos.direccion().pais())
					.orElse(null);
			if (ubicacion == null) {
				ubicacion = datos.direccion().toEntity();
			}
		}

		Usuario usuario = datos.toEntity();
		if (marca != null) {
			usuario.setMarca(marca);
		}
		if (tipoDocumento != null) {
			usuario.setTipoDocumento(tipoDocumento);
		}
		if (ubicacion != null) {
			usuario.setDireccion(ubicacion);
		}

		try {
			validacion.validarCreacionUsuario(usuario);
		} catch (ValidacionExcepcion ex) {
			if (ex.getErrores() != null && !ex.getErrores().isEmpty()) {
				errores.putAll(ex.getErrores());
			} else if (ex.getCampo() != null) {
				errores.put(ex.getCampo(), ex.getMessage());
			}
		}

		if (!errores.isEmpty()) {
			throw new ValidacionExcepcion(errores);
		}

		if (ubicacion != null && ubicacion.getId() == null) {
			ubicacion = repositorioUbicacion.save(ubicacion);
			usuario.setDireccion(ubicacion);
		}

		repositorioUsuario.save(usuario);
		return UsuarioResponseCompleto.fromEntity(usuario);

	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioResponseCompleto buscarUsuarioId(UUID id) {
		Usuario usuario = repositorioUsuario.findById(id).orElseThrow(() -> new IllegalArgumentException("El usuario no fue encontrado"));

		return UsuarioResponseCompleto.fromEntity(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioResponseCompleto> listarUsuarios() {
		return repositorioUsuario.findAll().stream().map(UsuarioResponseCompleto::fromEntity).toList();
	}

	@Override
	@Transactional
	public UsuarioResponseCompleto actualuzarUsuario(UUID id, UsuarioRequestActualizarDTO datos) {

		Usuario usuarioExistente = repositorioUsuario.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));

		datos.toEntity(usuarioExistente);

		Map<String, String> errores = new LinkedHashMap<>();

		if (datos.tipoDocumento() != null && datos.tipoDocumento().id() != null) {
			TipoDocumento tipoDocumento = repositorioTipoDocumento.findById(datos.tipoDocumento().id()).orElse(null);
			if (tipoDocumento == null) {
				errores.put("tipoDocumento", "Selecciona un tipo de documento válido");
			} else {
				usuarioExistente.setTipoDocumento(tipoDocumento);
			}
		}

		try {
			validacion.validarActualizarUsuario(usuarioExistente);
		} catch (ValidacionExcepcion ex) {
			if (ex.getErrores() != null && !ex.getErrores().isEmpty()) {
				errores.putAll(ex.getErrores());
			} else if (ex.getCampo() != null) {
				errores.put(ex.getCampo(), ex.getMessage());
			}
		}

		if (!errores.isEmpty()) {
			throw new ValidacionExcepcion(errores);
		}

		repositorioUsuario.save(usuarioExistente);
		return UsuarioResponseCompleto.fromEntity(usuarioExistente);

	}

}
