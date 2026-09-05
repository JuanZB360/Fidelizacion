package com.Fidelizacion.Registro_Marca.validaciones.ubicacionValidacion;

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
				|| !direccionLimpia.matches("[\\p{L}0-9]+(?:[ .,#\\-/][\\p{L}0-9]+)*")) {
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
        validarDireccion(ubicacion.getDireccion());
        validarCiudad(ubicacion.getCiudad());
        validarDepartamento(ubicacion.getDepartamento());
        validarPais(ubicacion.getPais());
    }

}
