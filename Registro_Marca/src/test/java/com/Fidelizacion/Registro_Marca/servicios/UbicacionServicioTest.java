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

import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.repositorio.IUbicacionRepositorio;
import com.Fidelizacion.Registro_Marca.servicios.ubicacionServicio.ImpUbicacionServicio;
import com.Fidelizacion.Registro_Marca.validaciones.ubicacionValidacion.IUbicacionValidacion;

@ExtendWith(MockitoExtension.class)
class UbicacionServicioTest {

    @Mock
    private IUbicacionRepositorio repositorio;

    @Mock
    private IUbicacionValidacion validacion;

    private ImpUbicacionServicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new ImpUbicacionServicio(validacion, repositorio);
    }

    @Test
    void debeCrearUbicacionDespuesDeValidarla() {
        Ubicacion ubicacion = ubicacion(UUID.randomUUID(), "Calle 10#20-30");
        when(repositorio.save(any(Ubicacion.class))).thenReturn(ubicacion);

        UbicacionResponseDTO respuesta = servicio.crearUbicacion(request("Calle 10#20-30"));

        verify(validacion).validarUbicacion(any(Ubicacion.class));
        verify(repositorio).save(any(Ubicacion.class));
        assertEquals(ubicacion.getId(), respuesta.id());
    }

    @Test
    void debeBuscarUbicacionPorId() {
        UUID id = UUID.randomUUID();
        Ubicacion ubicacion = ubicacion(id, "Calle 10#20-30");
        when(repositorio.findById(id)).thenReturn(Optional.of(ubicacion));

        UbicacionResponseDTO respuesta = servicio.buscarUbicacionId(id);

        assertEquals(id, respuesta.id());
        assertEquals("Bogotá", respuesta.ciudad());
    }

    @Test
    void debeLanzarErrorSiUbicacionNoExiste() {
        UUID id = UUID.randomUUID();
        when(repositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> servicio.buscarUbicacionId(id));
    }

    @Test
    void debeListarUbicaciones() {
        when(repositorio.findAll()).thenReturn(List.of(ubicacion(UUID.randomUUID(), "Calle 10#20-30")));

        List<UbicacionResponseDTO> respuesta = servicio.listarUbicaciones();

        assertEquals(1, respuesta.size());
    }

    @Test
    void debeActualizarUbicacionYValidarla() {
        UUID id = UUID.randomUUID();
        Ubicacion ubicacion = ubicacion(id, "Calle 10#20-30");
        when(repositorio.findById(id)).thenReturn(Optional.of(ubicacion));
        when(repositorio.save(ubicacion)).thenReturn(ubicacion);

        servicio.actualizarUbicacion(id, request("Carrera 7#40-10"));

        verify(validacion).validarUbicacion(ubicacion);
        assertEquals("Carrera 7#40-10", ubicacion.getDireccion());
    }

    @Test
    void debeLanzarExcepcionSiUbicacionNoExisteAlActualizar() {
        UUID id = UUID.randomUUID();
        when(repositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> servicio.actualizarUbicacion(id, request("Carrera 7#40-10")));
    }

    @Test
    void debeBuscarUsuariosDeUbicacion() {
        UUID id = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nombre("Laura")
                .apellido("Martínez")
                .email("laura@example.com")
                .build();
        Ubicacion ubicacion = ubicacion(id, "Calle 10#20-30");
        ubicacion.setUsuarios(List.of(usuario));
        when(repositorio.findById(id)).thenReturn(Optional.of(ubicacion));

        List<UsuarioResponseCompleto> usuarios = servicio.buscarUsuariosUbicacion(id);

        assertEquals(1, usuarios.size());
        assertEquals("Laura", usuarios.get(0).nombre());
        assertEquals("laura@example.com", usuarios.get(0).email());
    }

    @Test
    void debeLanzarExcepcionSiUbicacionNoExisteAlBuscarUsuarios() {
        UUID id = UUID.randomUUID();
        when(repositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> servicio.buscarUsuariosUbicacion(id));
    }

    private UbicacionRequestDTO request(String direccion) {
        return new UbicacionRequestDTO(direccion, "Bogotá", "Cundinamarca", "Colombia");
    }

    private Ubicacion ubicacion(UUID id, String direccion) {
        return Ubicacion.builder()
                .id(id)
                .direccion(direccion)
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build();
    }
}
