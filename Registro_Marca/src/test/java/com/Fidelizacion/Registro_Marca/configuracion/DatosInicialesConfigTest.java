package com.Fidelizacion.Registro_Marca.configuracion;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.repositorio.IMarcaRepositorio;
import com.Fidelizacion.Registro_Marca.repositorio.ITipoDocumentoRepositorio;

@ExtendWith(MockitoExtension.class)
class DatosInicialesConfigTest {

    @Mock
    private IMarcaRepositorio marcaRepositorio;

    @Mock
    private ITipoDocumentoRepositorio tipoDocumentoRepositorio;

    @Test
    void debeCargarDatosInicialesCuandoBaseDeDatosEstaVacia() throws Exception {
        when(tipoDocumentoRepositorio.existsByAbreviatura(any())).thenReturn(false);
        when(tipoDocumentoRepositorio.existsByNombre(any())).thenReturn(false);
        when(marcaRepositorio.existsByNombre(any())).thenReturn(false);

        when(tipoDocumentoRepositorio.save(any(TipoDocumento.class))).thenAnswer(i -> i.getArgument(0));
        when(marcaRepositorio.save(any(Marca.class))).thenAnswer(i -> i.getArgument(0));

        DatosInicialesConfig config = new DatosInicialesConfig();
        CommandLineRunner runner = config.inicializarDatos(marcaRepositorio, tipoDocumentoRepositorio);
        runner.run();

        verify(tipoDocumentoRepositorio, times(5)).save(any(TipoDocumento.class));
        verify(marcaRepositorio, times(6)).save(any(Marca.class));
    }

    @Test
    void noDebeDuplicarDatosSiYaExisten() throws Exception {
        when(tipoDocumentoRepositorio.existsByAbreviatura(any())).thenReturn(true);
        when(marcaRepositorio.existsByNombre(any())).thenReturn(true);

        DatosInicialesConfig config = new DatosInicialesConfig();
        CommandLineRunner runner = config.inicializarDatos(marcaRepositorio, tipoDocumentoRepositorio);
        runner.run();

        verify(tipoDocumentoRepositorio, never()).save(any(TipoDocumento.class));
        verify(marcaRepositorio, never()).save(any(Marca.class));
    }
}

