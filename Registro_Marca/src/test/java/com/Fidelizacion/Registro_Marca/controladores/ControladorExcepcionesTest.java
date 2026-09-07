package com.Fidelizacion.Registro_Marca.controladores;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

import com.Fidelizacion.Registro_Marca.controladores.ExcepcionControlador.ControladorExcepciones;
import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;

class ControladorExcepcionesTest {

    private MockMvc mockMvc;

    @RestController
    static class DummyTestController {
        @GetMapping("/test/validacion")
        public void lanzarValidacion() {
            throw new ValidacionExcepcion("email", "El email ya está registrado");
        }

        @GetMapping("/test/validacion-multiple")
        public void lanzarValidacionMultiple() {
            Map<String, String> errores = new LinkedHashMap<>();
            errores.put("nombre", "El nombre es obligatorio");
            errores.put("email", "El email es obligatorio");
            throw new ValidacionExcepcion(errores);
        }

        @GetMapping("/test/no-encontrado")
        public void lanzarNoEncontrado() {
            throw new IllegalArgumentException("La marca no existe: 123");
        }

        @GetMapping("/test/argumento-invalido")
        public void lanzarArgumentoInvalido() {
            throw new IllegalArgumentException("Parámetro inválido");
        }

        @GetMapping("/test/error-generico")
        public void lanzarErrorGenerico() {
            throw new RuntimeException("Fallo inesperado de conexión");
        }

        @PostMapping("/test/json")
        public void recibirJson(@RequestBody DummyPayload payload) {
        }
    }

    record DummyPayload(String valor) {}

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new DummyTestController())
                .setControllerAdvice(new ControladorExcepciones())
                .build();
    }

    @Test
    void debeManejarValidacionExcepcionComoBadRequest() throws Exception {
        mockMvc.perform(get("/test/validacion"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.estado").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.mensaje").value("El email ya está registrado"))
                .andExpect(jsonPath("$.campo").value("email"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void debeManejarValidacionExcepcionConMultiplesErroresComoBadRequest() throws Exception {
        mockMvc.perform(get("/test/validacion-multiple"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.estado").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.mensaje").value("Se encontraron 2 errores de validación"))
                .andExpect(jsonPath("$.errores.nombre").value("El nombre es obligatorio"))
                .andExpect(jsonPath("$.errores.email").value("El email es obligatorio"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void debeManejarRecursoNoEncontradoComoNotFound() throws Exception {
        mockMvc.perform(get("/test/no-encontrado"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.estado").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.mensaje").value("La marca no existe: 123"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void debeManejarIllegalArgumentExceptionComoBadRequest() throws Exception {
        mockMvc.perform(get("/test/argumento-invalido"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.estado").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.mensaje").value("Parámetro inválido"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void debeManejarJsonMalformadoComoBadRequest() throws Exception {
        mockMvc.perform(post("/test/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ invalid json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.estado").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.mensaje").value("El cuerpo de la solicitud no tiene un formato JSON válido o contiene tipos incompatibles"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void debeManejarExcepcionGenericaComoInternalServerError() throws Exception {
        mockMvc.perform(get("/test/error-generico"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.estado").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.mensaje").value("Ha ocurrido un error interno en el servidor"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}

