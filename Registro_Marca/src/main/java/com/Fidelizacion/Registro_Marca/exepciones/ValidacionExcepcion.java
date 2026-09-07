package com.Fidelizacion.Registro_Marca.exepciones;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class ValidacionExcepcion extends RuntimeException {

    private final String campo;
    private final Map<String, String> errores;

    public ValidacionExcepcion(String campo, String mensaje) {
        super(mensaje);
        this.campo = campo;
        Map<String, String> map = new LinkedHashMap<>();
        if (campo != null && mensaje != null) {
            map.put(campo, mensaje);
        }
        this.errores = Collections.unmodifiableMap(map);
    }

    public ValidacionExcepcion(Map<String, String> errores) {
        super(construirMensaje(errores));
        Map<String, String> map = errores != null ? new LinkedHashMap<>(errores) : new LinkedHashMap<>();
        this.errores = Collections.unmodifiableMap(map);
        this.campo = (this.errores.size() == 1) ? this.errores.keySet().iterator().next() : null;
    }

    private static String construirMensaje(Map<String, String> errores) {
        if (errores == null || errores.isEmpty()) {
            return "Se encontraron errores de validación";
        }
        if (errores.size() == 1) {
            return errores.values().iterator().next();
        }
        return "Se encontraron " + errores.size() + " errores de validación";
    }
}
