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
import com.Fidelizacion.Registro_Marca.servicios.emailServicio.IEmailServicio;
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
	private final IEmailServicio emailServicio;

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
					datos.direccion().direccion(), datos.direccion().ciudad(), datos.direccion().departamento(),
					datos.direccion().pais())
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

		if (usuario.getEmail() != null && !errores.containsKey("email")) {
			try {
				validacion.validarEmailUnico(repositorioUsuario.existsByEmail(usuario.getEmail()));
			} catch (ValidacionExcepcion ex) {
				errores.put(ex.getCampo(), ex.getMessage());
			}
		}

		if (usuario.getNumeroDocumento() != null && !errores.containsKey("numeroDocumento")) {
			try {
				validacion.validarNumeroDocumentoUnico(
						repositorioUsuario.existsByNumeroDocumento(usuario.getNumeroDocumento()));
			} catch (ValidacionExcepcion ex) {
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
		enviarEmailBienvenida(usuario);
		return UsuarioResponseCompleto.fromEntity(usuario);

	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioResponseCompleto buscarUsuarioId(UUID id) {
		Usuario usuario = repositorioUsuario.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El usuario no fue encontrado"));

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

		if (usuarioExistente.getEmail() != null && !errores.containsKey("email")) {
			try {
				validacion.validarEmailUnico(repositorioUsuario.existsByEmailAndIdNot(usuarioExistente.getEmail(), id));
			} catch (ValidacionExcepcion ex) {
				errores.put(ex.getCampo(), ex.getMessage());
			}
		}

		if (!errores.isEmpty()) {
			throw new ValidacionExcepcion(errores);
		}

		repositorioUsuario.save(usuarioExistente);
		return UsuarioResponseCompleto.fromEntity(usuarioExistente);

	}

	@Override
	public void enviarEmailBienvenida(Usuario usuario) {
		String estructuraHTML = """
				<!-- Contenedor Principal Centrado -->
				<div style="background-color: #f4f4f5; padding: 40px 16px; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; min-height: 100%%; box-sizing: border-box;">

				  <!-- Tarjeta Central del Correo -->
				  <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 16px; overflow: hidden; border: 1px solid #e4e4e7; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);">

				    <!-- Encabezado / Banner Superior -->
				    <div style="background-color: #18181b; padding: 36px 24px; text-align: center;">
				      <h1 style="margin: 0; font-size: 24px; font-weight: 700; color: #ffffff; letter-spacing: -0.5px;">
				        CLUB DE FIDELIZACIÓN
				      </h1>
				      <p style="margin: 6px 0 0 0; font-size: 13px; color: #a1a1aa; text-transform: uppercase; letter-spacing: 1px;">
				        Beneficios Exclusivos en tus Marcas Favoritas
				      </p>
				    </div>

				    <!-- Cuerpo del Contenido -->
				    <div style="padding: 36px 32px;">

				      <!-- Saludo Personalizado -->
				      <h2 style="margin: 0 0 12px 0; font-size: 22px; font-weight: 700; color: #18181b; line-height: 1.3;">
				        ¡Hola, %s %s! 👋
				      </h2>

				      <p style="margin: 0 0 24px 0; font-size: 15px; color: #52525b; line-height: 1.6;">
				        Tu registro se ha completado exitosamente. A partir de hoy comienzas a disfrutar de beneficios exclusivos y a acumular puntos en tus compras.
				      </p>

				      <!-- Tarjeta Marca Principal -->
				      <div style="background-color: #fafafa; border: 1px solid #e4e4e7; border-radius: 12px; padding: 20px 24px; margin-bottom: 24px; display: flex; justify-content: space-between; align-items: center;">
				        <span style="font-size: 14px; color: #71717a; font-weight: 500;">Marca principal asignada:</span>
				        <span style="font-size: 15px; font-weight: 700; color: #18181b;">%s</span>
				      </div>

				      <!-- Tarjeta Bono de Puntos -->
				      <div style="background-color: #18181b; border-radius: 12px; padding: 24px; text-align: center; color: #ffffff; margin-bottom: 28px;">
				        <span style="font-size: 12px; text-transform: uppercase; letter-spacing: 1.5px; color: #d4d4d8; display: block; margin-bottom: 4px;">
				          Bono de Bienvenida
				        </span>
				        <strong style="font-size: 32px; font-weight: 800; color: #ffffff; display: block; letter-spacing: -0.5px;">
				          +100 PUNTOS
				        </strong>
				        <p style="margin: 6px 0 0 0; font-size: 13px; color: #a1a1aa;">
				          Acreditados automáticamente para tus compras en %s.
				        </p>
				      </div>

				    </div>

				    <!-- Separador -->
				    <div style="border-top: 1px solid #f4f4f5; margin: 0 32px;"></div>

				    <!-- Pie de Página -->
				    <div style="padding: 24px 32px; text-align: center; color: #a1a1aa; font-size: 12px; line-height: 1.5;">
				      <p style="margin: 0 0 6px 0;">
				        Has recibido este mensaje porque te registraste en nuestra plataforma de fidelización.
				      </p>
				      <p style="margin: 0;">
				        © 2026 Club de Fidelización. Todos los derechos reservados.
				      </p>
				    </div>

				  </div>

				</div>
				"""
				.formatted(
						usuario.getNombre(),
						usuario.getApellido(),
						usuario.getMarca().getNombre(),
						usuario.getMarca().getNombre()
					);
		
		emailServicio.enviarCorreoHtml(usuario.getEmail(), "¡Bienvenido a la marca! Aquí tienes tu beneficio", estructuraHTML);

	}

}
