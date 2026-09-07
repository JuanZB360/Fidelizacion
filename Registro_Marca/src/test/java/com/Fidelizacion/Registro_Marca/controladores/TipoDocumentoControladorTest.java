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

import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.controladores.TipoDocumentoControlador.TiposDocumentoControlador;
import com.Fidelizacion.Registro_Marca.servicios.tipoDocumentoServicio.ITipoDocumentoServicio;
import com.Fidelizacion.Registro_Marca.utils.Roles;

@ExtendWith(MockitoExtension.class)
class TipoDocumentoControladorTest {

    private MockMvc mockMvc;

    @Mock
    private ITipoDocumentoServicio tipoDocumentoServicio;

    @InjectMocks
    private TiposDocumentoControlador controlador;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controlador).build();
    }

    @Test
    void debeCrearTipoDocumento() throws Exception {
        UUID id = UUID.randomUUID();
        when(tipoDocumentoServicio.crearTipoDocumento(any(TipoDocumentoRequestCrearDTO.class)))
                .thenReturn(new TipoDocumentoResponseDTO(id, "Cédula de Ciudadanía", "CC"));

        mockMvc.perform(post("/tipo-documento")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "nombre": "Cédula de Ciudadanía",
                            "abreviatura": "CC"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nombre").value("Cédula de Ciudadanía"))
                .andExpect(jsonPath("$.abreviatura").value("CC"));

        verify(tipoDocumentoServicio).crearTipoDocumento(any(TipoDocumentoRequestCrearDTO.class));
    }

    @Test
    void debeActualizarTipoDocumento() throws Exception {
        UUID id = UUID.randomUUID();
        when(tipoDocumentoServicio.actualizarTipoDocumento(eq(id), any(TipoDocumentoRequestActualizarDTO.class)))
                .thenReturn(new TipoDocumentoResponseDTO(id, "Cédula de Extranjería", "CE"));

        mockMvc.perform(patch("/tipo-documento/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "nombre": "Cédula de Extranjería",
                            "abreviatura": "CE"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nombre").value("Cédula de Extranjería"))
                .andExpect(jsonPath("$.abreviatura").value("CE"));

        verify(tipoDocumentoServicio).actualizarTipoDocumento(eq(id), any(TipoDocumentoRequestActualizarDTO.class));
    }

    @Test
    void debeBuscarTipoDocumentoPorId() throws Exception {
        UUID id = UUID.randomUUID();
        when(tipoDocumentoServicio.buscarTipoDocumentoId(id))
                .thenReturn(new TipoDocumentoResponseDTO(id, "Cédula de Ciudadanía", "CC"));

        mockMvc.perform(get("/tipo-documento/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nombre").value("Cédula de Ciudadanía"))
                .andExpect(jsonPath("$.abreviatura").value("CC"));
    }

    @Test
    void debeListarTiposDocumento() throws Exception {
        UUID id = UUID.randomUUID();
        when(tipoDocumentoServicio.listarTiposDocumento())
                .thenReturn(List.of(new TipoDocumentoResponseDTO(id, "Cédula de Ciudadanía", "CC")));

        mockMvc.perform(get("/tipo-documento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].nombre").value("Cédula de Ciudadanía"))
                .andExpect(jsonPath("$[0].abreviatura").value("CC"));
    }

    @Test
    void debeListarUsuariosDeTipoDocumento() throws Exception {
        UUID tipoDocId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        UsuarioResponseCompleto usuario = new UsuarioResponseCompleto(
                usuarioId, "Carlos", "Pérez", "carlos@example.com",
                Roles.CLIENTE, null, null, null, null, null);

        when(tipoDocumentoServicio.buscarUsuariosTipoDocumento(tipoDocId))
                .thenReturn(List.of(usuario));

        mockMvc.perform(get("/tipo-documento/{id}/usuarios", tipoDocId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(usuarioId.toString()))
                .andExpect(jsonPath("$[0].nombre").value("Carlos"))
                .andExpect(jsonPath("$[0].email").value("carlos@example.com"));
    }
}

