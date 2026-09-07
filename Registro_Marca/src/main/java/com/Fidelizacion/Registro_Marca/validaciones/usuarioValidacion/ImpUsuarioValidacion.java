package com.Fidelizacion.Registro_Marca.validaciones.usuarioValidacion;

import java.time.LocalDate;

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
    public void validarNombreApellido(String nombreApellido) {
        // si el nombre es null pasa como nulo sino se eliminan los espacios al inicio y
        // final
        String nombreLimpio = nombreApellido == null ? null : nombreApellido.trim();

        // se realiza una condicion para lanzar una excepcion que solo salta si el
        // nombre es nulo,
        // tiene una longitud menor a 3 o contiene numeros
        if (nombreLimpio == null
                || nombreLimpio.length() <= 2
                || !nombreLimpio.matches("\\p{L}+(?:\\s+\\p{L}+)*")) {
            throw new ValidacionExcepcion(
                    "nombre",
                    "El nombre debe tener al menos 3 letras y solo puede contener letras y espacios");
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
                    "contraseña",
                    "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial");
        }
    }

    @Override
    public void validacionLogin(Usuario usuario) {

        validarEmail(usuario.getEmail());
        validarContraseña(usuario.getConstrasena());
        validarRol(usuario.getRol());

    }

    @Override
    public void validacionCompletarInformacion(Usuario usuario) {

        validarNombreApellido(usuario.getNombre());
        validarNombreApellido(usuario.getApellido());
        validarIdentificacion(usuario.getTipoDocumento());
        validarRol(usuario.getRol());
        validarNumeroIdentificacion(usuario.getNumeroDocumento());
        validarFechaNacimiento(usuario.getFechaNacimiento());
        if (usuario.getTipoDocumento() != null && usuario.getTipoDocumento().getNombre() != null) {
            validacionTipoDocumento.validarNombre(usuario.getTipoDocumento().getNombre());
        }
        validacionMarca.validarNombreMarca(usuario.getMarca().getNombre());
        validacionUbicacion.validarUbicacion(usuario.getDireccion());

    }

    @Override
    public void validarActualizarUsuario(Usuario usuario) {
        validarRol(usuario.getRol());
        if (usuario.getEmail() != null) {
            validarEmail(usuario.getEmail());
        }

        if (usuario.getConstrasena() != null) {
            validarContraseña(usuario.getConstrasena());
        }

        if (usuario.getTipoDocumento() != null && usuario.getTipoDocumento().getNombre() != null) {
            validacionTipoDocumento.validarNombre(
                    usuario.getTipoDocumento().getNombre());
        }

        if (usuario.getMarca() != null) {
            validacionMarca.validarNombreMarca(
                    usuario.getMarca().getNombre());
        }

        if (usuario.getDireccion() != null) {
            validacionUbicacion.validarUbicacion(
                    usuario.getDireccion());
        }
    }

}
