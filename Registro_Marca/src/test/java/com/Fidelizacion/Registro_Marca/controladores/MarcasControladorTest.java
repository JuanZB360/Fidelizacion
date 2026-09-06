package com.Fidelizacion.Registro_Marca.controladores;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.controladores.MarcaControlador.MarcasControlador;
import com.Fidelizacion.Registro_Marca.servicios.MarcaServicio.IMarcaServicio;
import com.Fidelizacion.Registro_Marca.utils.Roles;

@ExtendWith(MockitoExtension.class)
class MarcasControladorTest {

    private MockMvc mockMvc;

    @Mock
    private IMarcaServicio marcaServicio;

    @InjectMocks
    private MarcasControlador controlador;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controlador).build();
    }

    @Test
    void debeCrearMarca() throws Exception {
        UUID id = UUID.randomUUID();
        when(marcaServicio.crearMarca(any(MarcaRequestCrearDTO.class)))
                .thenReturn(new MarcaResponseDTO(id, "Marca Central"));

        mockMvc.perform(post("/marca")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "nombre": "Marca Central"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nombre").value("Marca Central"));

        verify(marcaServicio).crearMarca(any(MarcaRequestCrearDTO.class));
    }

    @Test
    void debeActualizarMarca() throws Exception {
        UUID id = UUID.randomUUID();
        when(marcaServicio.actualizarMarca(eq(id), any(MarcaRequestActualizarDTO.class)))
                .thenReturn(new MarcaResponseDTO(id, "Nuevo Nombre"));

        mockMvc.perform(patch("/marca/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "nombre": "Nuevo Nombre"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nombre").value("Nuevo Nombre"));

        verify(marcaServicio).actualizarMarca(eq(id), any(MarcaRequestActualizarDTO.class));
    }

    @Test
    void debeBuscarMarcaPorId() throws Exception {
        UUID id = UUID.randomUUID();
        when(marcaServicio.buscarMarcaId(id))
                .thenReturn(new MarcaResponseDTO(id, "Marca Central"));

        mockMvc.perform(get("/marca/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nombre").value("Marca Central"));
    }

    @Test
    void debeListarMarcas() throws Exception {
        UUID id = UUID.randomUUID();
        when(marcaServicio.listarMarcas())
                .thenReturn(List.of(new MarcaResponseDTO(id, "Marca Central")));

        mockMvc.perform(get("/marca"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].nombre").value("Marca Central"));
    }

    @Test
    void debeListarUsuariosDeMarca() throws Exception {
        UUID marcaId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        UsuarioResponseCompleto usuario = new UsuarioResponseCompleto(
                usuarioId, "Carlos", "Pérez", "carlos@example.com",
                Roles.CLIENTE, null, null, null, null, null);

        when(marcaServicio.buscarUsuariosMarca(marcaId))
                .thenReturn(List.of(usuario));

        mockMvc.perform(get("/marca/{id}/usuarios", marcaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(usuarioId.toString()))
                .andExpect(jsonPath("$[0].nombre").value("Carlos"))
                .andExpect(jsonPath("$[0].email").value("carlos@example.com"));
    }
}

