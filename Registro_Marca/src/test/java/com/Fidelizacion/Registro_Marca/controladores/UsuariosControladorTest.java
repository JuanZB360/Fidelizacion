package com.Fidelizacion.Registro_Marca.controladores;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCompletarInformacioDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestLoginDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseLoginDTO;
import com.Fidelizacion.Registro_Marca.controladores.UsuarioControlador.UsuariosControlador;
import com.Fidelizacion.Registro_Marca.servicios.usuarioServicio.IUsuarioServicio;
import com.Fidelizacion.Registro_Marca.utils.Roles;

@ExtendWith(MockitoExtension.class)
class UsuariosControladorTest {

    private MockMvc mockMvc;

    @Mock
    private IUsuarioServicio usuarioServicio;

    @InjectMocks
    private UsuariosControlador controlador;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controlador).build();
    }

    @Test
    void debeCrearUsuario() throws Exception {
        UUID id = UUID.randomUUID();
        when(usuarioServicio.crearUsuario(any(UsuarioRequestLoginDTO.class)))
                .thenReturn(new UsuarioResponseLoginDTO(id, "ana@example.com", Roles.CLIENTE));

        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "ana@example.com",
                            "contrasena": "ClaveSegura1!"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.email").value("ana@example.com"))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));

        verify(usuarioServicio).crearUsuario(any(UsuarioRequestLoginDTO.class));
    }

    @Test
    void debeCompletarInformacion() throws Exception {
        UUID usuarioId = UUID.randomUUID();
        UUID marcaId = UUID.randomUUID();
        UUID tipoDocumentoId = UUID.randomUUID();
        UsuarioResponseCompleto respuesta = new UsuarioResponseCompleto(
                usuarioId, "Carlos", "Pérez", "carlos@example.com",
                Roles.CLIENTE, null, "1234567890", null, null, null);

        when(usuarioServicio.completarInformacio(eq(usuarioId), any(UsuarioRequestCompletarInformacioDTO.class)))
        when(usuarioServicio.crearUsuario(any(UsuarioRequestCrearDTO.class)))
                .thenReturn(respuesta);

        mockMvc.perform(put("/usuario/{id}/informacion", usuarioId)
        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": "%s",
                            "nombre": "Carlos",
                            "apellido": "Pérez",
                            "email": "carlos@example.com",
                            "contrasena": "ClaveSegura1!",
                            "rol": "CLIENTE",
                            "tipoDocumento": {
                                "id": "%s"
                            },
                            "numeroDocumento": "1234567890",
                            "fechaNacimiento": "2000-01-01",
                            "direccion": {
                                "direccion": "Calle 10 # 20-30",
                                "ciudad": "Bogota",
                                "departamento": "Cundinamarca",
                                "pais": "Colombia"
                            },
                            "marca": {
                                "id": "%s"
                            }
                        }
                        """.formatted(usuarioId, tipoDocumentoId, marcaId)))
                        """.formatted(tipoDocumentoId, marcaId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioId.toString()))
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.email").value("carlos@example.com"))
                .andExpect(jsonPath("$.numeroDocumento").value("1234567890"));

        verify(usuarioServicio).completarInformacio(eq(usuarioId), any(UsuarioRequestCompletarInformacioDTO.class));
        verify(usuarioServicio).crearUsuario(any(UsuarioRequestCrearDTO.class));
    }

    @Test
    void debeBuscarUsuarioPorId() throws Exception {
        UUID id = UUID.randomUUID();
        UsuarioResponseCompleto respuesta = new UsuarioResponseCompleto(
                id, "Carlos", "Pérez", "carlos@example.com",
                Roles.CLIENTE, null, null, null, null, null);

        when(usuarioServicio.buscarUsuarioId(id)).thenReturn(respuesta);

        mockMvc.perform(get("/usuario/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    void debeListarUsuarios() throws Exception {
        UUID id = UUID.randomUUID();
        UsuarioResponseCompleto respuesta = new UsuarioResponseCompleto(
                id, "Carlos", "Pérez", "carlos@example.com",
                Roles.CLIENTE, null, null, null, null, null);

        when(usuarioServicio.listarUsuarios()).thenReturn(List.of(respuesta));

        mockMvc.perform(get("/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].nombre").value("Carlos"));
    }

    @Test
    void debeActualizarUsuario() throws Exception {
        UUID id = UUID.randomUUID();
        UsuarioResponseCompleto respuesta = new UsuarioResponseCompleto(
                id, "Carlos", "Pérez", "nuevo@example.com",
                Roles.CLIENTE, null, null, null, null, null);

        when(usuarioServicio.actualuzarUsuario(eq(id), any(UsuarioRequestActualizarDTO.class)))
                .thenReturn(respuesta);

        mockMvc.perform(patch("/usuario/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "nuevo@example.com"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.email").value("nuevo@example.com"));

        verify(usuarioServicio).actualuzarUsuario(eq(id), any(UsuarioRequestActualizarDTO.class));
    }
}

