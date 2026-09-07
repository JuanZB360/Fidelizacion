package com.Fidelizacion.Registro_Marca.configuracion;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.repositorio.IMarcaRepositorio;
import com.Fidelizacion.Registro_Marca.repositorio.ITipoDocumentoRepositorio;

@Configuration
@Profile("!test")
public class DatosInicialesConfig {

    private static final Logger log = LoggerFactory.getLogger(DatosInicialesConfig.class);

    @Bean
    public CommandLineRunner inicializarDatos(
            IMarcaRepositorio marcaRepositorio,
            ITipoDocumentoRepositorio tipoDocumentoRepositorio) {
        return args -> {
            cargarTiposDocumento(tipoDocumentoRepositorio);
            cargarMarcas(marcaRepositorio);
        };
    }

    private void cargarTiposDocumento(ITipoDocumentoRepositorio tipoDocumentoRepositorio) {
        List<TipoDocumento> tipos = List.of(
                TipoDocumento.builder().nombre("Cédula de Ciudadanía").abreviatura("CC").build(),
                TipoDocumento.builder().nombre("Tarjeta de Identidad").abreviatura("TI").build(),
                TipoDocumento.builder().nombre("Cédula de Extranjería").abreviatura("CE").build(),
                TipoDocumento.builder().nombre("Pasaporte").abreviatura("PAS").build(),
                TipoDocumento.builder().nombre("Permiso Especial de Permanencia").abreviatura("PEP").build()
        );

        for (TipoDocumento tipo : tipos) {
            if (!tipoDocumentoRepositorio.existsByAbreviatura(tipo.getAbreviatura())
                    && !tipoDocumentoRepositorio.existsByNombre(tipo.getNombre())) {
                TipoDocumento guardado = tipoDocumentoRepositorio.save(tipo);
                log.info("Tipo de documento inicializado: {} ({}) - ID: {}", guardado.getNombre(), guardado.getAbreviatura(), guardado.getId());
            }
        }
    }

    private void cargarMarcas(IMarcaRepositorio marcaRepositorio) {
        List<String> marcas = List.of(
                "Americanino",
                "American Eagle",
                "Chevignon",
                "Esprit",
                "Naf Naf",
                "Rifle"
        );

        for (String nombre : marcas) {
            if (!marcaRepositorio.existsByNombre(nombre)) {
                Marca guardada = marcaRepositorio.save(Marca.builder().nombre(nombre).build());
                log.info("Marca inicializada: {} - ID: {}", guardada.getNombre(), guardada.getId());
            }
        }
    }
}
