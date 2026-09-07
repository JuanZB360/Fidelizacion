package com.Fidelizacion.Registro_Marca.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
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

import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.repositorio.ITipoDocumentoRepositorio;
import com.Fidelizacion.Registro_Marca.servicios.tipoDocumentoServicio.ImpTipoDocumentoServicio;
import com.Fidelizacion.Registro_Marca.validaciones.tipoDocumentoValidacion.ITipoDocumentoValidacion;

@ExtendWith(MockitoExtension.class)
class TipoDocumentoServicioTest {

    @Mock
    private ITipoDocumentoRepositorio repositorio;

    @Mock
    private ITipoDocumentoValidacion validacion;

    private ImpTipoDocumentoServicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new ImpTipoDocumentoServicio(repositorio, validacion);
    }

    @Test
    void debeCrearTipoDocumentoDespuesDeValidarlo() {
        UUID id = UUID.randomUUID();
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .id(id)
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();

        when(repositorio.existsByNombre("Cédula de Ciudadanía")).thenReturn(false);
        when(repositorio.existsByAbreviatura("CC")).thenReturn(false);
        when(repositorio.save(any(TipoDocumento.class))).thenReturn(tipoDoc);

        TipoDocumentoResponseDTO respuesta = servicio.crearTipoDocumento(
                new TipoDocumentoRequestCrearDTO("Cédula de Ciudadanía", "CC"));

        verify(validacion).validarCreacionTipoDocumento(any(TipoDocumento.class), eq(false), eq(false));
        verify(repositorio).save(any(TipoDocumento.class));
        assertEquals(id, respuesta.id());
        assertEquals("Cédula de Ciudadanía", respuesta.nombre());
        assertEquals("CC", respuesta.abreviatura());
    }

    @Test
    void debeRechazarTipoDocumentoDuplicado() {
        when(repositorio.existsByNombre("Cédula de Ciudadanía")).thenReturn(true);
        when(repositorio.existsByAbreviatura("CC")).thenReturn(false);
        doThrow(new ValidacionExcepcion("nombre", "duplicado"))
                .when(validacion)
                .validarCreacionTipoDocumento(any(TipoDocumento.class), eq(true), eq(false));

        assertThrows(
                ValidacionExcepcion.class,
                () -> servicio.crearTipoDocumento(new TipoDocumentoRequestCrearDTO("Cédula de Ciudadanía", "CC")));
    }

    @Test
    void debeBuscarTipoDocumentoPorId() {
        UUID id = UUID.randomUUID();
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .id(id)
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();
        when(repositorio.findById(id)).thenReturn(Optional.of(tipoDoc));

        TipoDocumentoResponseDTO respuesta = servicio.buscarTipoDocumentoId(id);

        assertEquals(id, respuesta.id());
        assertEquals("Cédula de Ciudadanía", respuesta.nombre());
        assertEquals("CC", respuesta.abreviatura());
    }

    @Test
    void debeLanzarExcepcionSiTipoDocumentoNoExisteAlBuscarPorId() {
        UUID id = UUID.randomUUID();
        when(repositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> servicio.buscarTipoDocumentoId(id));
    }

    @Test
    void debeListarTiposDocumento() {
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .id(UUID.randomUUID())
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();
        when(repositorio.findAll()).thenReturn(List.of(tipoDoc));

        List<TipoDocumentoResponseDTO> respuesta = servicio.listarTiposDocumento();

        assertEquals(1, respuesta.size());
        assertEquals("Cédula de Ciudadanía", respuesta.get(0).nombre());
    }

    @Test
    void debeActualizarTipoDocumento() {
        UUID id = UUID.randomUUID();
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .id(id)
                .nombre("Nombre Anterior")
                .abreviatura("NA")
                .build();

        when(repositorio.findById(id)).thenReturn(Optional.of(tipoDoc));
        when(repositorio.existsByNombreAndIdNot("Nombre Nuevo", id)).thenReturn(false);
        when(repositorio.existsByAbreviaturaAndIdNot("NN", id)).thenReturn(false);
        when(repositorio.save(tipoDoc)).thenReturn(tipoDoc);

        TipoDocumentoResponseDTO respuesta = servicio.actualizarTipoDocumento(
                id, new TipoDocumentoRequestActualizarDTO("Nombre Nuevo", "NN"));

        verify(validacion).validarNombre("Nombre Nuevo");
        verify(validacion).validarQueNombreSeaUnico(false);
        verify(validacion).validarAbreviatura("NN");
        verify(validacion).validarQueAbreviaturaSeaUnica(false);
        verify(repositorio).save(tipoDoc);
        assertEquals("Nombre Nuevo", respuesta.nombre());
        assertEquals("NN", respuesta.abreviatura());
    }

    @Test
    void debeListarUsuariosDeTipoDocumento() {
        UUID id = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nombre("Carlos")
                .apellido("Pérez")
                .email("carlos@example.com")
                .build();
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .id(id)
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .usuarios(List.of(usuario))
                .build();

        when(repositorio.findById(id)).thenReturn(Optional.of(tipoDoc));

        List<UsuarioResponseCompleto> respuesta = servicio.buscarUsuariosTipoDocumento(id);

        assertEquals(1, respuesta.size());
        assertEquals("Carlos", respuesta.get(0).nombre());
    }
}

