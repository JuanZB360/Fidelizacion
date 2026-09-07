package com.Fidelizacion.Registro_Marca.validaciones.ubicacionValidacion;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;

@Component 
public class ImpUbicacionValidacion implements IUbicacionValidacion {

	@Override
	public void validarDireccion(String direccion) {
		String direccionLimpia = direccion == null ? null : direccion.trim();

		if (direccionLimpia == null
				|| direccionLimpia.length() < 5
				|| !direccionLimpia.matches("[\\p{L}0-9]+(?:[ .,#\\-/]+[\\p{L}0-9]+)*[.]?")) {
			throw new ValidacionExcepcion(
					"direccion",
					"La dirección debe tener al menos 5 caracteres y un formato válido");
		}
	}

	@Override
	public void validarCiudad(String ciudad) {
		validarTextoGeografico(ciudad, "ciudad");
	}

	@Override
	public void validarDepartamento(String departamento) {
		validarTextoGeografico(departamento, "departamento");
	}

	@Override
	public void validarPais(String pais) {
		validarTextoGeografico(pais, "pais");
	}

	private void validarTextoGeografico(String valor, String campo) {
		String valorLimpio = valor == null ? null : valor.trim();

		if (valorLimpio == null
				|| valorLimpio.length() < 2
				|| !valorLimpio.matches("\\p{L}+(?:[ '\\-]\\p{L}+)*")) {
			throw new ValidacionExcepcion(
					campo,
					"El campo debe tener al menos 2 letras y solo puede contener letras, espacios y guiones");
		}
	}

    @Override
    public void validarUbicacion(Ubicacion ubicacion) {
        if (ubicacion == null) {
            throw new ValidacionExcepcion("direccion", "Debes proporcionar una ubicación válida");
        }
        Map<String, String> errores = new LinkedHashMap<>();
        try {
            validarDireccion(ubicacion.getDireccion());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            validarCiudad(ubicacion.getCiudad());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            validarDepartamento(ubicacion.getDepartamento());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        try {
            validarPais(ubicacion.getPais());
        } catch (ValidacionExcepcion ex) {
            errores.put(ex.getCampo(), ex.getMessage());
        }
        if (!errores.isEmpty()) {
            throw new ValidacionExcepcion(errores);
        }
    }

}
