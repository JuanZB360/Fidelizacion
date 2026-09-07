package com.Fidelizacion.Registro_Marca.servicios.usuarioServicio;

import java.util.List;
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

		if (datos.marca() == null || datos.marca().id() == null) {
			throw new ValidacionExcepcion("nombre", "Selecciona Una Marca Valida");
		}
		Marca marca = repositorioMarca.findById(datos.marca().id())
				.orElseThrow(() -> new ValidacionExcepcion("nombre", "Selecciona Una Marca Valida"));

		if (datos.tipoDocumento() == null || datos.tipoDocumento().id() == null) {
			throw new ValidacionExcepcion("tipoDocumento", "Debes seleccionar un tipo de documento válido");
		}
		TipoDocumento tipoDocumento = repositorioTipoDocumento.findById(datos.tipoDocumento().id())
				.orElseThrow(() -> new ValidacionExcepcion("tipoDocumento", "Selecciona Un Tipo de Documento Valido"));

		if (datos.direccion() == null) {
			throw new ValidacionExcepcion("direccion", "Debes proporcionar una ubicación válida");
		}
		Ubicacion ubicacion = repositorioUbicacion.findByDireccionAndCiudadAndDepartamentoAndPais(
				datos.direccion().direccion(), datos.direccion().ciudad(), datos.direccion().departamento(), datos.direccion().pais())
				.orElse(null);

		if (ubicacion == null) {
			ubicacion = repositorioUbicacion.save(datos.direccion().toEntity());
		}

		Usuario usuario = datos.toEntity();
		usuario.setMarca(marca);
		usuario.setTipoDocumento(tipoDocumento);
		usuario.setDireccion(ubicacion);

		validacion.validarCreacionUsuario(usuario);

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

		if (datos.tipoDocumento() != null && datos.tipoDocumento().id() != null) {
			TipoDocumento tipoDocumento = repositorioTipoDocumento.findById(datos.tipoDocumento().id())
					.orElseThrow(() -> new ValidacionExcepcion("tipoDocumento", "Selecciona Un Tipo de Documento Valido"));
			usuarioExistente.setTipoDocumento(tipoDocumento);
		}

		validacion.validarActualizarUsuario(usuarioExistente);

		repositorioUsuario.save(usuarioExistente);
		return UsuarioResponseCompleto.fromEntity(usuarioExistente);

	}

}
