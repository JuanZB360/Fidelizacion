package com.Fidelizacion.Registro_Marca.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCompletarInformacioDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestLoginDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseLoginDTO;
import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.repositorio.IMarcaRepositorio;
import com.Fidelizacion.Registro_Marca.repositorio.IUsuarioRepositorio;
import com.Fidelizacion.Registro_Marca.servicios.usuarioServicio.ImpUsuarioServicio;
import com.Fidelizacion.Registro_Marca.utils.Roles;
import com.Fidelizacion.Registro_Marca.utils.TipoDocumento;
import com.Fidelizacion.Registro_Marca.validaciones.usuarioValidacion.IUsuarioValidacion;

@ExtendWith(MockitoExtension.class)
class UsuarioServicioTest {

    @Mock
    private IUsuarioRepositorio repositorioUsuario;

    @Mock
    private IMarcaRepositorio repositorioMarca;

    @Mock
    private IUsuarioValidacion validacion;

    private ImpUsuarioServicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new ImpUsuarioServicio(repositorioUsuario, repositorioMarca, validacion);
    }

    @Test
    void debeCrearUsuarioConRolClientePorDefecto() {
        Usuario usuarioGuardado = Usuario.builder()
                .id(UUID.randomUUID())
                .email("ana@example.com")
                .constrasena("ClaveSegura1!")
                .rol(Roles.CLIENTE)
                .build();
        when(repositorioUsuario.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        UsuarioResponseLoginDTO respuesta = servicio.crearUsuario(
                new UsuarioRequestLoginDTO("ana@example.com", "ClaveSegura1!"));

        verify(validacion).validacionLogin(any(Usuario.class));
        verify(repositorioUsuario).save(any(Usuario.class));
        assertEquals(Roles.CLIENTE, respuesta.rol());
        assertEquals("ana@example.com", respuesta.email());
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
    void debeCompletarInformacionUsuarioExitosamente() {
        UUID usuarioId = UUID.randomUUID();
        UUID marcaId = UUID.randomUUID();

        Usuario usuarioExistente = Usuario.builder()
                .id(usuarioId)
                .email("carlos@example.com")
                .constrasena("ClaveSegura1!")
                .build();

        Marca marcaExistente = Marca.builder()
                .id(marcaId)
                .nombre("Marca Central")
                .build();

        when(repositorioUsuario.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(repositorioMarca.findById(marcaId)).thenReturn(Optional.of(marcaExistente));
        when(repositorioUsuario.save(usuarioExistente)).thenReturn(usuarioExistente);

        UsuarioRequestCompletarInformacioDTO info = new UsuarioRequestCompletarInformacioDTO(
                usuarioId,
                "Carlos",
                "Pérez",
                TipoDocumento.CC,
                "1234567890",
                LocalDate.now().minusYears(25),
                new UbicacionRequestDTO("Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"),
                new MarcaRequestDTO(marcaId));

        UsuarioResponseCompleto respuesta = servicio.completarInformacio(usuarioId, info);

        verify(validacion).validacionCompletarInformacion(usuarioExistente);
        verify(repositorioUsuario).save(usuarioExistente);
        assertEquals("Carlos", respuesta.nombre());
        assertEquals("Pérez", respuesta.apellido());
        assertEquals("1234567890", respuesta.numeroDocumento());
        assertEquals("Marca Central", respuesta.marca().nombre());
        assertEquals("Calle 10 # 20-30", respuesta.direccion().direccion());
    }

    @Test
    void debeLanzarExcepcionSiUsuarioNoExisteAlCompletarInformacion() {
        UUID usuarioId = UUID.randomUUID();
        UUID marcaId = UUID.randomUUID();
        when(repositorioUsuario.findById(usuarioId)).thenReturn(Optional.empty());

        UsuarioRequestCompletarInformacioDTO info = new UsuarioRequestCompletarInformacioDTO(
                usuarioId,
                "Carlos",
                "Pérez",
                TipoDocumento.CC,
                "1234567890",
                LocalDate.now().minusYears(25),
                new UbicacionRequestDTO("Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"),
                new MarcaRequestDTO(marcaId));

        assertThrows(IllegalArgumentException.class,
                () -> servicio.completarInformacio(usuarioId, info));
    }

    @Test
    void debeLanzarExcepcionSiMarcaNoExisteAlCompletarInformacion() {
        UUID usuarioId = UUID.randomUUID();
        UUID marcaId = UUID.randomUUID();

        Usuario usuarioExistente = Usuario.builder()
                .id(usuarioId)
                .email("carlos@example.com")
                .build();

        when(repositorioUsuario.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(repositorioMarca.findById(marcaId)).thenReturn(Optional.empty());

        UsuarioRequestCompletarInformacioDTO info = new UsuarioRequestCompletarInformacioDTO(
                usuarioId,
                "Carlos",
                "Pérez",
                TipoDocumento.CC,
                "1234567890",
                LocalDate.now().minusYears(25),
                new UbicacionRequestDTO("Calle 10 # 20-30", "Bogotá", "Cundinamarca", "Colombia"),
                new MarcaRequestDTO(marcaId));

        assertThrows(IllegalArgumentException.class,
                () -> servicio.completarInformacio(usuarioId, info));
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
