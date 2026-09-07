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

import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.controladores.UbicacionControlador.UbicacionesControlador;
import com.Fidelizacion.Registro_Marca.servicios.ubicacionServicio.IUbicacionServicio;
import com.Fidelizacion.Registro_Marca.utils.Roles;

@ExtendWith(MockitoExtension.class)
class UbicacionesControladorTest {

    private MockMvc mockMvc;

    @Mock
    private IUbicacionServicio ubicacionServicio;

    @InjectMocks
    private UbicacionesControlador controlador;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controlador).build();
    }

    @Test
    void debeCrearUbicacion() throws Exception {
        UUID id = UUID.randomUUID();
        when(ubicacionServicio.crearUbicacion(any(UbicacionRequestDTO.class)))
                .thenReturn(new UbicacionResponseDTO(id, "Calle 10#20-30", "Bogotá", "Cundinamarca", "Colombia"));

        mockMvc.perform(post("/ubicacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "direccion": "Calle 10#20-30",
                            "ciudad": "Bogotá",
                            "departamento": "Cundinamarca",
                            "pais": "Colombia"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.direccion").value("Calle 10#20-30"))
                .andExpect(jsonPath("$.ciudad").value("Bogotá"));

        verify(ubicacionServicio).crearUbicacion(any(UbicacionRequestDTO.class));
    }

    @Test
    void debeActualizarUbicacion() throws Exception {
        UUID id = UUID.randomUUID();
        when(ubicacionServicio.actualizarUbicacion(eq(id), any(UbicacionRequestDTO.class)))
                .thenReturn(new UbicacionResponseDTO(id, "Carrera 7#40-10", "Bogotá", "Cundinamarca", "Colombia"));

        mockMvc.perform(patch("/ubicacion/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "direccion": "Carrera 7#40-10",
                            "ciudad": "Bogotá",
                            "departamento": "Cundinamarca",
                            "pais": "Colombia"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.direccion").value("Carrera 7#40-10"));

        verify(ubicacionServicio).actualizarUbicacion(eq(id), any(UbicacionRequestDTO.class));
    }

    @Test
    void debeBuscarUbicacionPorId() throws Exception {
        UUID id = UUID.randomUUID();
        when(ubicacionServicio.buscarUbicacionId(id))
                .thenReturn(new UbicacionResponseDTO(id, "Calle 10#20-30", "Bogotá", "Cundinamarca", "Colombia"));

        mockMvc.perform(get("/ubicacion/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.ciudad").value("Bogotá"));
    }

    @Test
    void debeListarUbicaciones() throws Exception {
        UUID id = UUID.randomUUID();
        when(ubicacionServicio.listarUbicaciones())
                .thenReturn(List.of(new UbicacionResponseDTO(id, "Calle 10#20-30", "Bogotá", "Cundinamarca", "Colombia")));

        mockMvc.perform(get("/ubicacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].direccion").value("Calle 10#20-30"));
    }

    @Test
    void debeListarUsuariosDeUbicacion() throws Exception {
        UUID ubicacionId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        UsuarioResponseCompleto usuario = new UsuarioResponseCompleto(
                usuarioId, "Laura", "Martínez", "laura@example.com",
                Roles.CLIENTE, null, null, null, null, null);

        when(ubicacionServicio.buscarUsuariosUbicacion(ubicacionId))
                .thenReturn(List.of(usuario));

        mockMvc.perform(get("/ubicacion/{id}/usuarios", ubicacionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(usuarioId.toString()))
                .andExpect(jsonPath("$[0].nombre").value("Laura"))
                .andExpect(jsonPath("$[0].email").value("laura@example.com"));
    }
}

