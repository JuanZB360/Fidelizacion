package com.Fidelizacion.Registro_Marca.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.repositorio.IMarcaRepositorio;
import com.Fidelizacion.Registro_Marca.repositorio.ITipoDocumentoRepositorio;
import com.Fidelizacion.Registro_Marca.repositorio.IUbicacionRepositorio;
import com.Fidelizacion.Registro_Marca.repositorio.IUsuarioRepositorio;
import com.Fidelizacion.Registro_Marca.servicios.usuarioServicio.ImpUsuarioServicio;
import com.Fidelizacion.Registro_Marca.utils.Roles;
import com.Fidelizacion.Registro_Marca.validaciones.usuarioValidacion.IUsuarioValidacion;

@ExtendWith(MockitoExtension.class)
class UsuarioServicioTest {

    @Mock
    private IUsuarioRepositorio repositorioUsuario;

    @Mock
    private IMarcaRepositorio repositorioMarca;

    @Mock
    private ITipoDocumentoRepositorio repositorioTipoDocumento;

    @Mock
    private IUsuarioValidacion validacion;

    @Mock
    private IUbicacionRepositorio repositorioUbicacion;

    private ImpUsuarioServicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new ImpUsuarioServicio(repositorioUsuario, repositorioMarca, repositorioTipoDocumento, validacion, repositorioUbicacion);
    }

    @Test
    void debeCrearUsuarioExitosamenteReutilizandoUbicacion() {
        UUID marcaId = UUID.randomUUID();
        UUID tipoDocumentoId = UUID.randomUUID();

        Marca marcaExistente = Marca.builder()
                .id(marcaId)
                .nombre("Marca Central")
                .build();

        TipoDocumento tipoDocExistente = TipoDocumento.builder()
                .id(tipoDocumentoId)
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();

        Ubicacion ubicacionExistente = Ubicacion.builder()
                .id(UUID.randomUUID())
                .direccion("Calle 10 # 20-30")
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build();

        when(repositorioMarca.findById(marcaId)).thenReturn(Optional.of(marcaExistente));
        when(repositorioTipoDocumento.findById(tipoDocumentoId)).thenReturn(Optional.of(tipoDocExistente));
        when(repositorioUbicacion.findByDireccionAndCiudadAndDepartamentoAndPais(
                "Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"))
                .thenReturn(Optional.of(ubicacionExistente));
        when(repositorioUsuario.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioRequestCrearDTO datos = new UsuarioRequestCrearDTO(
                "Carlos",
                "Pérez",
                "carlos@example.com",
                "ClaveSegura1!",
                Roles.CLIENTE,
                new TipoDocumentoRequestDTO(tipoDocumentoId),
                "1234567890",
                LocalDate.now().minusYears(25),
                new UbicacionRequestDTO("Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"),
                new MarcaRequestDTO(marcaId));

        UsuarioResponseCompleto respuesta = servicio.crearUsuario(datos);

        verify(repositorioUbicacion, never()).save(any(Ubicacion.class));
        verify(validacion).validarCreacionUsuario(any(Usuario.class));
        verify(repositorioUsuario).save(any(Usuario.class));
        assertEquals("Carlos", respuesta.nombre());
        assertEquals("Pérez", respuesta.apellido());
        assertEquals("carlos@example.com", respuesta.email());
        assertEquals("1234567890", respuesta.numeroDocumento());
        assertEquals("Marca Central", respuesta.marca().nombre());
        assertEquals("Cédula de Ciudadanía", respuesta.tipoDocumento().nombre());
        assertEquals("CC", respuesta.tipoDocumento().abreviatura());
        assertEquals("Calle 10 # 20-30", respuesta.direccion().direccion());
    }

    @Test
    void debeCrearUsuarioCreandoNuevaUbicacionSiNoExiste() {
        UUID marcaId = UUID.randomUUID();
        UUID tipoDocumentoId = UUID.randomUUID();

        Marca marcaExistente = Marca.builder()
                .id(marcaId)
                .nombre("Marca Central")
                .build();

        TipoDocumento tipoDocExistente = TipoDocumento.builder()
                .id(tipoDocumentoId)
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();

        Ubicacion nuevaUbicacion = Ubicacion.builder()
                .id(UUID.randomUUID())
                .direccion("Calle 10 # 20-30")
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build();

        when(repositorioMarca.findById(marcaId)).thenReturn(Optional.of(marcaExistente));
        when(repositorioTipoDocumento.findById(tipoDocumentoId)).thenReturn(Optional.of(tipoDocExistente));
        when(repositorioUbicacion.findByDireccionAndCiudadAndDepartamentoAndPais(
                "Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"))
                .thenReturn(Optional.empty());
        when(repositorioUbicacion.save(any(Ubicacion.class))).thenReturn(nuevaUbicacion);
        when(repositorioUsuario.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioRequestCrearDTO datos = new UsuarioRequestCrearDTO(
                "Carlos",
                "Pérez",
                "carlos@example.com",
                "ClaveSegura1!",
                Roles.CLIENTE,
                new TipoDocumentoRequestDTO(tipoDocumentoId),
                "1234567890",
                LocalDate.now().minusYears(25),
                new UbicacionRequestDTO("Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"),
                new MarcaRequestDTO(marcaId));

        UsuarioResponseCompleto respuesta = servicio.crearUsuario(datos);

        verify(repositorioUbicacion).save(any(Ubicacion.class));
        verify(validacion).validarCreacionUsuario(any(Usuario.class));
        verify(repositorioUsuario).save(any(Usuario.class));
        assertEquals("Carlos", respuesta.nombre());
        assertEquals("Calle 10 # 20-30", respuesta.direccion().direccion());
        assertEquals("CC", respuesta.tipoDocumento().abreviatura());
    }

    @Test
    void debeLanzarExcepcionSiMarcaNoExisteAlCrearUsuario() {
        UUID marcaId = UUID.randomUUID();
        UUID tipoDocumentoId = UUID.randomUUID();

        TipoDocumento tipoDocExistente = TipoDocumento.builder()
                .id(tipoDocumentoId)
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();

        when(repositorioTipoDocumento.findById(tipoDocumentoId)).thenReturn(Optional.of(tipoDocExistente));
        when(repositorioMarca.findById(marcaId)).thenReturn(Optional.empty());

        UsuarioRequestCrearDTO datos = new UsuarioRequestCrearDTO(
                "Carlos",
                "Pérez",
                "carlos@example.com",
                "ClaveSegura1!",
                Roles.CLIENTE,
                new TipoDocumentoRequestDTO(tipoDocumentoId),
                "1234567890",
                LocalDate.now().minusYears(25),
                new UbicacionRequestDTO("Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"),
                new MarcaRequestDTO(marcaId));

        ValidacionExcepcion exMarca = assertThrows(ValidacionExcepcion.class, () -> servicio.crearUsuario(datos));
        assertEquals("marca", exMarca.getCampo());
    }

    @Test
    void debeAcumularErroresSiMarcaYTipoDocumentoNoExistenAlCrearUsuario() {
        UUID marcaId = UUID.randomUUID();
        UUID tipoDocumentoId = UUID.randomUUID();

        when(repositorioMarca.findById(marcaId)).thenReturn(Optional.empty());
        when(repositorioTipoDocumento.findById(tipoDocumentoId)).thenReturn(Optional.empty());

        UsuarioRequestCrearDTO datos = new UsuarioRequestCrearDTO(
                "Carlos",
                "Pérez",
                "carlos@example.com",
                "ClaveSegura1!",
                Roles.CLIENTE,
                new TipoDocumentoRequestDTO(tipoDocumentoId),
                "1234567890",
                LocalDate.now().minusYears(25),
                new UbicacionRequestDTO("Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"),
                new MarcaRequestDTO(marcaId));

        ValidacionExcepcion excepcion = assertThrows(ValidacionExcepcion.class, () -> servicio.crearUsuario(datos));
        assertEquals(2, excepcion.getErrores().size());
        assertEquals("Selecciona una marca válida", excepcion.getErrores().get("marca"));
        assertEquals("Selecciona un tipo de documento válido", excepcion.getErrores().get("tipoDocumento"));
    }

    @Test
    void debeLanzarExcepcionSiTipoDocumentoNoExisteAlCrearUsuario() {
        UUID marcaId = UUID.randomUUID();
        UUID tipoDocumentoId = UUID.randomUUID();

        Marca marcaExistente = Marca.builder()
                .id(marcaId)
                .nombre("Marca Central")
                .build();

        when(repositorioMarca.findById(marcaId)).thenReturn(Optional.of(marcaExistente));
        when(repositorioTipoDocumento.findById(tipoDocumentoId)).thenReturn(Optional.empty());

        UsuarioRequestCrearDTO datos = new UsuarioRequestCrearDTO(
                "Carlos",
                "Pérez",
                "carlos@example.com",
                "ClaveSegura1!",
                Roles.CLIENTE,
                new TipoDocumentoRequestDTO(tipoDocumentoId),
                "1234567890",
                LocalDate.now().minusYears(25),
                new UbicacionRequestDTO("Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"),
                new MarcaRequestDTO(marcaId));

        ValidacionExcepcion exTipoDoc = assertThrows(ValidacionExcepcion.class, () -> servicio.crearUsuario(datos));
        assertEquals("tipoDocumento", exTipoDoc.getCampo());
    }

    @Test
    void debeBuscarUsuarioPorId() {
        UUID id = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .id(id)
                .email("ana@example.com")
                .constrasena("ClaveSegura1!")
                .build();
        when(repositorioUsuario.findById(id)).thenReturn(Optional.of(usuario));

        UsuarioResponseCompleto respuesta = servicio.buscarUsuarioId(id);

        assertEquals(id, respuesta.id());
        assertEquals("ana@example.com", respuesta.email());
    }

    @Test
    void debeLanzarExcepcionSiUsuarioNoExisteAlBuscar() {
        UUID id = UUID.randomUUID();
        when(repositorioUsuario.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> servicio.buscarUsuarioId(id));
    }

    @Test
    void debeListarUsuarios() {
        UUID id = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .id(id)
                .nombre("Pedro")
                .apellido("Gómez")
                .email("pedro@example.com")
                .build();
        when(repositorioUsuario.findAll()).thenReturn(List.of(usuario));

        List<UsuarioResponseCompleto> lista = servicio.listarUsuarios();

        assertEquals(1, lista.size());
        assertEquals("Pedro", lista.get(0).nombre());
        assertEquals("pedro@example.com", lista.get(0).email());
    }

    @Test
    void debeActualizarUsuarioExitosamente() {
        UUID usuarioId = UUID.randomUUID();
        Usuario usuarioExistente = Usuario.builder()
                .id(usuarioId)
                .email("antiguo@example.com")
                .constrasena("ClaveSegura1!")
                .build();

        when(repositorioUsuario.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(repositorioUsuario.save(usuarioExistente)).thenReturn(usuarioExistente);

        UsuarioRequestActualizarDTO datos = new UsuarioRequestActualizarDTO(
                null, null, "NuevaClave2!", "nuevo@example.com");

        UsuarioResponseCompleto respuesta = servicio.actualuzarUsuario(usuarioId, datos);

        verify(validacion).validarActualizarUsuario(usuarioExistente);
        verify(repositorioUsuario).save(usuarioExistente);
        assertEquals("nuevo@example.com", respuesta.email());
    }

    @Test
    void debeLanzarExcepcionSiUsuarioNoExisteAlActualizar() {
        UUID usuarioId = UUID.randomUUID();
        when(repositorioUsuario.findById(usuarioId)).thenReturn(Optional.empty());

        UsuarioRequestActualizarDTO datos = new UsuarioRequestActualizarDTO(
                null, null, "NuevaClave2!", "nuevo@example.com");

        assertThrows(IllegalArgumentException.class,
                () -> servicio.actualuzarUsuario(usuarioId, datos));
    }
}
