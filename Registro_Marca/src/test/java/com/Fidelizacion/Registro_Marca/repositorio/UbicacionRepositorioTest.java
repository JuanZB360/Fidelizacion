package com.Fidelizacion.Registro_Marca.repositorio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;

@SpringBootTest
@Transactional
class UbicacionRepositorioTest {

    @Autowired
    private IUbicacionRepositorio ubicacionRepositorio;

    @Test
    void debeVerificarExistenciaPorDireccionCiudadDepartamentoYPais() {
        Ubicacion ubicacion = Ubicacion.builder()
                .direccion("Carrera 7#40-10")
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build();
        ubicacionRepositorio.save(ubicacion);

        boolean existe = ubicacionRepositorio.existsByDireccionAndCiudadAndDepartamentoAndPais(
                "Carrera 7#40-10", "Bogotá", "Cundinamarca", "Colombia");
        assertTrue(existe);

        boolean noExiste = ubicacionRepositorio.existsByDireccionAndCiudadAndDepartamentoAndPais(
                "Calle 100#15-20", "Bogotá", "Cundinamarca", "Colombia");
        assertFalse(noExiste);
    }

    @Test
    void debeBuscarPorDireccionCiudadDepartamentoYPais() {
        Ubicacion ubicacion = Ubicacion.builder()
                .direccion("Avenida Siempre Viva 742")
                .ciudad("Medellín")
                .departamento("Antioquia")
                .pais("Colombia")
                .build();
        Ubicacion guardada = ubicacionRepositorio.save(ubicacion);

        Optional<Ubicacion> encontrada = ubicacionRepositorio.findByDireccionAndCiudadAndDepartamentoAndPais(
                "Avenida Siempre Viva 742", "Medellín", "Antioquia", "Colombia");

        assertTrue(encontrada.isPresent());
        assertNotNull(encontrada.get().getId());
        assertEquals(guardada.getId(), encontrada.get().getId());
        assertEquals("Medellín", encontrada.get().getCiudad());
    }
}

