package com.Fidelizacion.Registro_Marca.validaciones.usuarioValidacion;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.Roles;
import com.Fidelizacion.Registro_Marca.validaciones.marcaValidacion.ImpMarcaValidacion;
import com.Fidelizacion.Registro_Marca.validaciones.tipoDocumentoValidacion.ImpTipoDocumentoValidacion;
import com.Fidelizacion.Registro_Marca.validaciones.ubicacionValidacion.ImpUbicacionValidacion;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImpUsuarioValidacion implements IUsuarioValidacion {

    private final ImpMarcaValidacion validacionMarca;
    private final ImpUbicacionValidacion validacionUbicacion;
    private final ImpTipoDocumentoValidacion validacionTipoDocumento;

    @Override
    public void validarNombre(String nombre) {
        String nombreLimpio = nombre == null ? null : nombre.trim();

        if (nombreLimpio == null
                || nombreLimpio.length() <= 2
                || !nombreLimpio.matches("\\p{L}+(?:\\s+\\p{L}+)*")) {
            throw new ValidacionExcepcion(
                    "nombre",
                    "El nombre debe tener al menos 3 letras y solo puede contener letras y espacios");
        }
    }

    @Override
    public void validarNombreApellido(String nombre) {
        validarNombre(nombre);
    }

    @Override
    public void validarApellido(String apellido) {
        String apellidoLimpio = apellido == null ? null : apellido.trim();

        if (apellidoLimpio == null
                || apellidoLimpio.length() <= 2
                || !apellidoLimpio.matches("\\p{L}+(?:\\s+\\p{L}+)*")) {
            throw new ValidacionExcepcion(
                    "apellido",
                    "El apellido debe tener al menos 3 letras y solo puede contener letras y espacios");
        }
    }

    @Override
    public void validarIdentificacion(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null) {
            throw new ValidacionExcepcion(
                    "tipoDocumento",
                    "Debes seleccionar un tipo de documento válido.");
        }

    }

    @Override
    public void validarRol(Roles rol) {
        if (rol == null) {
            throw new ValidacionExcepcion(
                    "rol",
                    "El rol del usuario es obligatorio");
        }
    }

    @Override
    public void validarNumeroIdentificacion(String numeroIdentificacion) {
        String numeroIdentificacionLimpio = numeroIdentificacion == null
                ? null
                : numeroIdentificacion.trim();

        if (numeroIdentificacionLimpio == null
                || numeroIdentificacionLimpio.length() < 10
                || !numeroIdentificacionLimpio.matches("[0-9]+")) {
            throw new ValidacionExcepcion(
                    "numeroDocumento",
                    "El número de documento debe contener solo dígitos y tener al menos 10 caracteres");
        }
    }

    @Override
    public void validarFechaNacimiento(LocalDate fechaNacimiento) {
        LocalDate fechaMinima = LocalDate.now().minusYears(18);

        if (fechaNacimiento == null || fechaNacimiento.isAfter(fechaMinima)) {
            throw new ValidacionExcepcion(
                    "fechaNacimiento",
                    "Debes tener al menos 18 años y proporcionar una fecha válida");
        }
    }

    @Override
    public void validarEmail(String email) {

        String emailLimpio = email == null
                ? null
                : email.trim();

        if (emailLimpio == null
                || emailLimpio.isBlank()
                || !emailLimpio.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidacionExcepcion(
                    "email",
                    "Debes agregar un email válido");
        }

    }

    @Override
    public void validarContraseña(String contrasena) {
        if (contrasena == null
                || !contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])\\S{8,}$")) {
            throw new ValidacionExcepcion(
                    "contrasena",
                    "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial");
        }
    }

    public void acumularErroresLogin(Usuario usuario, Map<String, String> errores) {
        if (usuario == null) {
            errores.put("usuario", "El usuario no puede ser nulo");
            return;
        }
        try {
            validarEmail(usuario.getEmail());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            validarContraseña(usuario.getConstrasena());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            validarRol(usuario.getRol());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
    }

    public void acumularErroresCompletarInformacion(Usuario usuario, Map<String, String> errores) {
        if (usuario == null) {
            errores.put("usuario", "El usuario no puede ser nulo");
            return;
        }
        try {
            validarNombre(usuario.getNombre());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            validarApellido(usuario.getApellido());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            validarIdentificacion(usuario.getTipoDocumento());
            if (usuario.getTipoDocumento() != null && usuario.getTipoDocumento().getNombre() != null) {
                validacionTipoDocumento.validarNombre(usuario.getTipoDocumento().getNombre());
            }
        } catch (ValidacionExcepcion ex) {
            errores.put("tipoDocumento", ex.getMessage());
        }
        try {
            validarRol(usuario.getRol());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            validarNumeroIdentificacion(usuario.getNumeroDocumento());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            validarFechaNacimiento(usuario.getFechaNacimiento());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            if (usuario.getMarca() == null) {
                throw new ValidacionExcepcion("marca", "Debes seleccionar una marca válida");
            }
            if (usuario.getMarca().getNombre() != null) {
                validacionMarca.validarNombreMarca(usuario.getMarca().getNombre());
            }
        } catch (ValidacionExcepcion ex) {
            errores.put("marca", ex.getMessage());
        }
        try {
            validacionUbicacion.validarUbicacion(usuario.getDireccion());
        } catch (ValidacionExcepcion ex) {
            if (ex.getErrores() != null && !ex.getErrores().isEmpty()) {
                errores.putAll(ex.getErrores());
            } else if (ex.getCampo() != null) {
                errores.put(ex.getCampo(), ex.getMessage());
            }
        }
    }

    @Override
    public void validacionLogin(Usuario usuario) {
        Map<String, String> errores = new LinkedHashMap<>();
        acumularErroresLogin(usuario, errores);
        if (!errores.isEmpty()) {
            throw new ValidacionExcepcion(errores);
        }
    }

    @Override
    public void validacionCompletarInformacion(Usuario usuario) {
        Map<String, String> errores = new LinkedHashMap<>();
        acumularErroresCompletarInformacion(usuario, errores);
        if (!errores.isEmpty()) {
            throw new ValidacionExcepcion(errores);
        }
    }

    @Override
    public void validarCreacionUsuario(Usuario usuario) {
        Map<String, String> errores = new LinkedHashMap<>();
        acumularErroresLogin(usuario, errores);
        acumularErroresCompletarInformacion(usuario, errores);
        if (!errores.isEmpty()) {
            throw new ValidacionExcepcion(errores);
        }
    }

    @Override
    public void validarActualizarUsuario(Usuario usuario) {
        Map<String, String> errores = new LinkedHashMap<>();
        try {
            validarRol(usuario.getRol());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }

        if (usuario.getEmail() != null) {
            try {
                validarEmail(usuario.getEmail());
            } catch (ValidacionExcepcion ex) {
                errores.put(ex.getCampo(), ex.getMessage());
            }
        }

        if (usuario.getConstrasena() != null) {
            try {
                validarContraseña(usuario.getConstrasena());
            } catch (ValidacionExcepcion ex) {
                errores.put(ex.getCampo(), ex.getMessage());
            }
        }

        if (usuario.getTipoDocumento() != null && usuario.getTipoDocumento().getNombre() != null) {
            try {
                validacionTipoDocumento.validarNombre(usuario.getTipoDocumento().getNombre());
            } catch (ValidacionExcepcion e) {
                errores.put("tipoDocumento", e.getMessage());
            }
        }

        if (usuario.getMarca() != null && usuario.getMarca().getNombre() != null) {
            try {
                validacionMarca.validarNombreMarca(usuario.getMarca().getNombre());
            } catch (ValidacionExcepcion e) {
                errores.put("marca", e.getMessage());
            }
        }

        if (usuario.getDireccion() != null) {
            try {
                validacionUbicacion.validarUbicacion(usuario.getDireccion());
            } catch (ValidacionExcepcion ex) {
                errores.put(ex.getCampo(), ex.getMessage());
            }
        }

        if (!errores.isEmpty()) {
            throw new ValidacionExcepcion(errores);
        }
    }

}
