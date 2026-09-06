package com.Fidelizacion.Registro_Marca.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.repositorio.IMarcaRepositorio;
import com.Fidelizacion.Registro_Marca.servicios.MarcaServicio.ImpMarcaServicio;
import com.Fidelizacion.Registro_Marca.validaciones.marcaValidacion.IMarcaValidacion;

@ExtendWith(MockitoExtension.class)
class MarcaServicioTest {

    @Mock
    private IMarcaRepositorio repositorio;

    @Mock
    private IMarcaValidacion validacion;

    private ImpMarcaServicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new ImpMarcaServicio(repositorio, validacion);
    }

    @Test
    void debeCrearMarcaDespuesDeValidarla() {
        Marca marca = Marca.builder().id(UUID.randomUUID()).nombre("Marca Central").build();
        when(repositorio.existsByNombre("Marca Central")).thenReturn(false);
        when(repositorio.save(any(Marca.class))).thenReturn(marca);

        MarcaResponseDTO respuesta = servicio.crearMarca(
                new MarcaRequestCrearDTO("Marca Central"));

        verify(validacion).validarCreacionMarca(any(Marca.class), org.mockito.ArgumentMatchers.eq(false));
        verify(repositorio).save(any(Marca.class));
        assertEquals(marca.getId(), respuesta.id());
    }

    @Test
    void debeRechazarMarcaDuplicada() {
        when(repositorio.existsByNombre("Marca Central")).thenReturn(true);
        org.mockito.Mockito.doThrow(new ValidacionExcepcion("nombre", "duplicada"))
                .when(validacion)
                .validarCreacionMarca(any(Marca.class), org.mockito.ArgumentMatchers.eq(true));

        assertThrows(
                ValidacionExcepcion.class,
                () -> servicio.crearMarca(new MarcaRequestCrearDTO("Marca Central")));
    }

    @Test
    void debeBuscarMarcaPorId() {
        UUID id = UUID.randomUUID();
        Marca marca = Marca.builder().id(id).nombre("Marca Central").build();
        when(repositorio.findById(id)).thenReturn(Optional.of(marca));

        MarcaResponseDTO respuesta = servicio.buscarMarcaId(id);

        assertEquals(id, respuesta.id());
        assertEquals("Marca Central", respuesta.nombre());
    }

    @Test
    void debeListarMarcas() {
        Marca marca = Marca.builder().id(UUID.randomUUID()).nombre("Marca Central").build();
        when(repositorio.findAll()).thenReturn(List.of(marca));

        List<MarcaResponseDTO> respuesta = servicio.listarMarcas();

        assertEquals(1, respuesta.size());
        assertEquals("Marca Central", respuesta.get(0).nombre());
    }

    @Test
    void debeActualizarNombreDeMarca() {
        UUID id = UUID.randomUUID();
        Marca marca = Marca.builder().id(id).nombre("Nombre anterior").build();
        when(repositorio.findById(id)).thenReturn(Optional.of(marca));
        when(repositorio.existsByNombreAndIdNot("Nombre nuevo", id)).thenReturn(false);
        when(repositorio.save(marca)).thenReturn(marca);

        MarcaResponseDTO respuesta = servicio.actualizarMarca(
                id,
                new MarcaRequestActualizarDTO("Nombre nuevo"));

        verify(validacion).validarNombreMarca("Nombre nuevo");
        assertEquals("Nombre nuevo", marca.getNombre());
        assertEquals("Nombre nuevo", respuesta.nombre());
    }

    @Test
    void debeLanzarExcepcionSiMarcaNoExisteAlBuscar() {
        UUID id = UUID.randomUUID();
        when(repositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> servicio.buscarMarcaId(id));
    }

    @Test
    void debeLanzarExcepcionSiMarcaNoExisteAlActualizar() {
        UUID id = UUID.randomUUID();
        when(repositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> servicio.actualizarMarca(id, new MarcaRequestActualizarDTO("Nuevo")));
    }

    @Test
    void debeBuscarUsuariosDeMarca() {
        UUID id = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nombre("Carlos")
                .apellido("Pérez")
                .email("carlos@example.com")
                .build();
        Marca marca = Marca.builder()
                .id(id)
                .nombre("Marca Central")
                .usuarios(List.of(usuario))
                .build();
        when(repositorio.findById(id)).thenReturn(Optional.of(marca));

        List<UsuarioResponseCompleto> usuarios = servicio.buscarUsuariosMarca(id);

        assertEquals(1, usuarios.size());
        assertEquals("Carlos", usuarios.get(0).nombre());
        assertEquals("carlos@example.com", usuarios.get(0).email());
    }

    @Test
    void debeLanzarExcepcionSiMarcaNoExisteAlBuscarUsuarios() {
        UUID id = UUID.randomUUID();
        when(repositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> servicio.buscarUsuariosMarca(id));
    }
}
