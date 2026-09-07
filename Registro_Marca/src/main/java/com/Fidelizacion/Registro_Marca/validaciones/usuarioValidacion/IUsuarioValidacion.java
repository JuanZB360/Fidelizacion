package com.Fidelizacion.Registro_Marca.validaciones.usuarioValidacion;

import java.time.LocalDate;

import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.Roles;

/*

    Datos del usuario:
    tipo de identificación,
    número de identificación,
    nombres,
    apellidos,
    fecha de nacimiento
    email,
    contraseña

*/

public interface IUsuarioValidacion {

    void validarNombre(String nombre);
    default void validarNombreApellido(String nombre) {
        validarNombre(nombre);
    }
    void validarApellido(String apellido);
    void validarIdentificacion(TipoDocumento tipoDocumento);
    void validarRol(Roles rol);
    void validarNumeroIdentificacion(String numeroIdentificacion);
    void validarFechaNacimiento(LocalDate fechaNacimiento);
    void validarEmail(String email);
    void validarContraseña(String contraseña);
    default void validarContrasena(String contrasena) {
        validarContraseña(contrasena);
    }
    void validacionLogin(Usuario usuario);
    void validacionCompletarInformacion(Usuario usuario);
    void validarCreacionUsuario(Usuario usuario);
    void validarActualizarUsuario(Usuario usuario);
    void validarEmailUnico(Boolean existeEmail);
    void validarNumeroDocumentoUnico(Boolean existeNumeroDocumento);

}
