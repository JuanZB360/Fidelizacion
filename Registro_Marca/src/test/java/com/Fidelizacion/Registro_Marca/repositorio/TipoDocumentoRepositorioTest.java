package com.Fidelizacion.Registro_Marca.repositorio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;

@SpringBootTest
@Transactional
class TipoDocumentoRepositorioTest {

    @Autowired
    private ITipoDocumentoRepositorio tipoDocumentoRepositorio;

    @Test
    void debeGuardarYVerificarExistenciaPorNombreYAbreviatura() {
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();
        TipoDocumento guardado = tipoDocumentoRepositorio.save(tipoDoc);

        assertNotNull(guardado.getId());
        assertTrue(tipoDocumentoRepositorio.existsByNombre("Cédula de Ciudadanía"));
        assertTrue(tipoDocumentoRepositorio.existsByAbreviatura("CC"));
        assertFalse(tipoDocumentoRepositorio.existsByNombre("Pasaporte"));
        assertFalse(tipoDocumentoRepositorio.existsByAbreviatura("PA"));
    }

    @Test
    void debeVerificarExistenciaPorNombreYAbreviaturaExcluyendoIdPropio() {
        TipoDocumento doc1 = TipoDocumento.builder().nombre("Cédula de Ciudadanía").abreviatura("CC").build();
        TipoDocumento doc2 = TipoDocumento.builder().nombre("Tarjeta de Identidad").abreviatura("TI").build();

        tipoDocumentoRepositorio.save(doc1);
        tipoDocumentoRepositorio.save(doc2);

        // doc1 con su propio id no debe considerarse duplicado
        assertFalse(tipoDocumentoRepositorio.existsByNombreAndIdNot("Cédula de Ciudadanía", doc1.getId()));
        assertFalse(tipoDocumentoRepositorio.existsByAbreviaturaAndIdNot("CC", doc1.getId()));

        // pero con un id distinto sí debe retornar true
        assertTrue(tipoDocumentoRepositorio.existsByNombreAndIdNot("Cédula de Ciudadanía", doc2.getId()));
        assertTrue(tipoDocumentoRepositorio.existsByAbreviaturaAndIdNot("CC", doc2.getId()));
        assertTrue(tipoDocumentoRepositorio.existsByNombreAndIdNot("Cédula de Ciudadanía", UUID.randomUUID()));
    }
}

