package com.Fidelizacion.Registro_Marca.validaciones.ubicacionValidacion;

import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;

public interface IUbicacionValidacion {

	void validarDireccion(String direccion);
	void validarCiudad(String ciudad);
	void validarDepartamento(String departamento);
	void validarPais(String pais);
    void validarUbicacion(Ubicacion ubicacion);

}
