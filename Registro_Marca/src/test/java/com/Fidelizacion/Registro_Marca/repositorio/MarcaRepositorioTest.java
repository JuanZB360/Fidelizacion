package com.Fidelizacion.Registro_Marca.repositorio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.Fidelizacion.Registro_Marca.modelos.Marca;

@SpringBootTest
@Transactional
class MarcaRepositorioTest {

    @Autowired
    private IMarcaRepositorio marcaRepositorio;

    @Test
    void debeGuardarYVerificarExistenciaPorNombre() {
        Marca marca = Marca.builder()
                .nombre("Marca Exclusiva")
                .build();
        Marca guardada = marcaRepositorio.save(marca);

        assertNotNull(guardada.getId());
        assertTrue(marcaRepositorio.existsByNombre("Marca Exclusiva"));
        assertFalse(marcaRepositorio.existsByNombre("Marca Inexistente"));
    }

    @Test
    void debeVerificarExistenciaPorNombreExcluyendoIdPropio() {
        Marca marca1 = Marca.builder().nombre("Marca Alfa").build();
        Marca marca2 = Marca.builder().nombre("Marca Beta").build();

        marcaRepositorio.save(marca1);
        marcaRepositorio.save(marca2);

        // marca1 con su propio id no debe considerarse duplicada
        assertFalse(marcaRepositorio.existsByNombreAndIdNot("Marca Alfa", marca1.getId()));

        // pero con un id distinto (ej: el de marca2 o un UUID aleatorio) sí debe retornar true
        assertTrue(marcaRepositorio.existsByNombreAndIdNot("Marca Alfa", marca2.getId()));
        assertTrue(marcaRepositorio.existsByNombreAndIdNot("Marca Alfa", UUID.randomUUID()));
    }
}
