package com.Fidelizacion.Registro_Marca.validaciones.marcaValidacion;

import org.springframework.stereotype.Component;

import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.Marca;

@Component 
public class ImpMarcaValidacion implements IMarcaValidacion{

	@Override
	public void validarNombreMarca(String nombre) {
		String nombreLimpio = nombre == null ? null : nombre.trim();

		if (nombreLimpio == null
				|| nombreLimpio.length() < 2
				|| !nombreLimpio.matches("\\p{L}+(?:[ '\\-]\\p{L}+)*")) {
			throw new ValidacionExcepcion(
					"nombre",
					"El nombre de la marca debe tener al menos 2 letras y solo puede contener letras, espacios y guiones");
		}
	}

    @Override
    public void validarQueSeaUnico(Boolean confirmacion) {
        if (confirmacion) {
            throw new ValidacionExcepcion(
					"nombre",
					"El nombre de la marca ya Existe");
        }
    }

    @Override
    public void validarCreacionMarca(Marca marca, Boolean confirmacion) {
        validarNombreMarca(marca.getNombre());
        validarQueSeaUnico(confirmacion);
    }

}
