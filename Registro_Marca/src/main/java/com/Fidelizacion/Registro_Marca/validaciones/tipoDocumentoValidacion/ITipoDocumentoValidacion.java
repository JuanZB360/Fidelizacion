package com.Fidelizacion.Registro_Marca.validaciones.tipoDocumentoValidacion;

import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;

public interface ITipoDocumentoValidacion {

    void validarNombre(String nombre);
    void validarAbreviatura(String abreviatura);
    void validarQueNombreSeaUnico(Boolean confirmacion);
    void validarQueAbreviaturaSeaUnica(Boolean confirmacion);
    void validarCreacionTipoDocumento(TipoDocumento tipoDocumento, Boolean existeNombre, Boolean existeAbreviatura);
    void validarTipoDocumento(TipoDocumento tipoDocumento);

}

