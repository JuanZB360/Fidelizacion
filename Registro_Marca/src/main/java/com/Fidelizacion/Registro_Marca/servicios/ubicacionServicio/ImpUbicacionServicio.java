package com.Fidelizacion.Registro_Marca.servicios.ubicacionServicio;


import org.springframework.stereotype.Service;


import com.Fidelizacion.Registro_Marca.repositorio.IUbicacionRepositorio;
import com.Fidelizacion.Registro_Marca.validaciones.ubicacionValidacion.ImpUbicacionValidacion;

import lombok.RequiredArgsConstructor;

@Service  
@RequiredArgsConstructor 
public class ImpUbicacionServicio implements IUbicacionServicio {

    private final ImpUbicacionValidacion validacionesUbicacion;
    private final IUbicacionRepositorio repositorioUbicacion;

}
