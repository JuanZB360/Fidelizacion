package com.Fidelizacion.Registro_Marca.exepciones;

import lombok.Getter;

@Getter
public class ValidacionExcepcion extends RuntimeException {

    private final String campo;

    public ValidacionExcepcion(String campo, String mensaje) {
        super(mensaje);
        this.campo = campo;
    }
}
