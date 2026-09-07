package com.Fidelizacion.Registro_Marca.validaciones.tipoDocumentoValidacion;

import org.springframework.stereotype.Component;

import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;

@Component
public class ImpTipoDocumentoValidacion implements ITipoDocumentoValidacion {

    @Override
    public void validarNombre(String nombre) {
        String nombreLimpio = nombre == null ? null : nombre.trim();

        if (nombreLimpio == null
                || nombreLimpio.length() < 2
                || !nombreLimpio.matches("\\p{L}+(?:[ '\\-]\\p{L}+)*")) {
            throw new ValidacionExcepcion(
                    "nombre",
                    "El nombre del tipo de documento debe tener al menos 2 letras y solo puede contener letras, espacios y guiones");
        }
    }

    @Override
    public void validarAbreviatura(String abreviatura) {
        String abreviaturaLimpia = abreviatura == null ? null : abreviatura.trim();

        if (abreviaturaLimpia == null
                || abreviaturaLimpia.length() < 2
                || abreviaturaLimpia.length() > 10
                || !abreviaturaLimpia.matches("^[A-Za-z0-9]+$")) {
            throw new ValidacionExcepcion(
                    "abreviatura",
                    "La abreviatura del tipo de documento debe tener entre 2 y 10 caracteres alfanuméricos");
        }
    }

    @Override
    public void validarQueNombreSeaUnico(Boolean confirmacion) {
        if (Boolean.TRUE.equals(confirmacion)) {
            throw new ValidacionExcepcion(
                    "nombre",
                    "El nombre del tipo de documento ya existe");
        }
    }

    @Override
    public void validarQueAbreviaturaSeaUnica(Boolean confirmacion) {
        if (Boolean.TRUE.equals(confirmacion)) {
            throw new ValidacionExcepcion(
                    "abreviatura",
                    "La abreviatura del tipo de documento ya existe");
        }
    }

    @Override
    public void validarCreacionTipoDocumento(TipoDocumento tipoDocumento, Boolean existeNombre, Boolean existeAbreviatura) {
        if (tipoDocumento == null) {
            throw new ValidacionExcepcion("tipoDocumento", "El tipo de documento no puede ser nulo");
        }
        validarNombre(tipoDocumento.getNombre());
        validarAbreviatura(tipoDocumento.getAbreviatura());
        validarQueNombreSeaUnico(existeNombre);
        validarQueAbreviaturaSeaUnica(existeAbreviatura);
    }

    @Override
    public void validarTipoDocumento(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null) {
            throw new ValidacionExcepcion("tipoDocumento", "Debes seleccionar un tipo de documento válido");
        }
        validarNombre(tipoDocumento.getNombre());
        validarAbreviatura(tipoDocumento.getAbreviatura());
    }

}

