package com.Fidelizacion.Registro_Marca.servicios.usuarioServicio;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCompletarInformacioDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestLoginDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseLoginDTO;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.repositorio.IUsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
	public class ImpUsuarioServicio implements IUsuarioServicio {

	private final IUsuarioRepositorio repositorio;

	@Override
	public UsuarioResponseLoginDTO crearUsuario(UsuarioRequestLoginDTO datos) {

		Usuario usuario = datos.toEntity();
		repositorio.save(usuario);
		return UsuarioResponseLoginDTO.fromEntity(usuario);

	}

	@Override
	public UsuarioResponseCompleto completarInformacio(UUID id, UsuarioRequestCompletarInformacioDTO informacion) {

		Usuario usuario = repositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));

		usuario.setNombre(informacion.nombre());
		usuario.setApellido(informacion.apellido());
		usuario.setTipoDocumento(informacion.tipoDocumento());
		usuario.setNumeroDocumento(informacion.numeroDocumento());
		usuario.setFechaNacimiento(informacion.fechaNacimiento());
		usuario.setDireccion(informacion.direccion());
		usuario.setMarca(informacion.marca());

		repositorio.save(usuario);
		return UsuarioResponseCompleto.fromEntity(usuario);
	}

	@Override
	public UsuarioResponseCompleto buscarUsuarioId(UUID id) {
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("El usuario no fue encontrado"));

		return UsuarioResponseCompleto.fromEntity(usuario);
	}

	@Override
	public List<UsuarioResponseCompleto> listarUsuarios() {
		return repositorio.findAll().stream().map(UsuarioResponseCompleto::fromEntity).toList();
	}

	@Override
	public UsuarioResponseCompleto actualuzarUsuario(UUID id, UsuarioRequestActualizarDTO datos) {

		Usuario usuarioExistente = repositorio.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));

		datos.toEntity(usuarioExistente);

		repositorio.save(usuarioExistente);
		return UsuarioResponseCompleto.fromEntity(usuarioExistente);

	}

}
