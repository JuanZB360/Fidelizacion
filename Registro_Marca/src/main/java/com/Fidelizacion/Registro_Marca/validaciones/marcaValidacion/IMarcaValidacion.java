package com.Fidelizacion.Registro_Marca.validaciones.marcaValidacion;

import com.Fidelizacion.Registro_Marca.modelos.Marca;

public interface IMarcaValidacion {

	void validarNombreMarca(String nombre);
    void validarQueSeaUnico(Boolean confirmacion);
    void validarCreacionMarca(Marca marca, Boolean confirmacion);

}
