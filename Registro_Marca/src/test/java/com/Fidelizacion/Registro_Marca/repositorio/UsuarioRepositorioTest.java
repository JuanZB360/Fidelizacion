package com.Fidelizacion.Registro_Marca.repositorio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.Roles;

@SpringBootTest
@Transactional
class UsuarioRepositorioTest {

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IMarcaRepositorio marcaRepositorio;

    @Autowired
    private IUbicacionRepositorio ubicacionRepositorio;

    @Autowired
    private ITipoDocumentoRepositorio tipoDocumentoRepositorio;

    @Test
    void debeGuardarYBuscarUsuarioPorId() {
        Usuario usuario = Usuario.builder()
                .email("test@example.com")
                .constrasena("ClaveSegura1!")
                .rol(Roles.CLIENTE)
                .build();

        Usuario guardado = usuarioRepositorio.save(usuario);

        assertNotNull(guardado.getId());
        Optional<Usuario> encontrado = usuarioRepositorio.findById(guardado.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("test@example.com", encontrado.get().getEmail());
        assertEquals(Roles.CLIENTE, encontrado.get().getRol());
    }

    @Test
    void debeGuardarUsuarioConMarcaYUbicacion() {
        Marca marca = marcaRepositorio.save(Marca.builder().nombre("Marca Repositorio").build());
        Ubicacion ubicacion = ubicacionRepositorio.save(Ubicacion.builder()
                .direccion("Carrera 15 # 85-10")
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build());
        TipoDocumento tipoDocumento = tipoDocumentoRepositorio.save(TipoDocumento.builder()
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build());

        Usuario usuario = Usuario.builder()
                .nombre("Mariana")
                .apellido("López")
                .email("mariana@example.com")
                .constrasena("ClaveSegura1!")
                .rol(Roles.ADMIN)
                .tipoDocumento(tipoDocumento)
                .numeroDocumento("9876543210")
                .fechaNacimiento(LocalDate.of(1998, 5, 20))
                .marca(marca)
                .direccion(ubicacion)
                .build();

        Usuario guardado = usuarioRepositorio.save(usuario);

        assertNotNull(guardado.getId());
        Optional<Usuario> encontrado = usuarioRepositorio.findById(guardado.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Mariana", encontrado.get().getNombre());
        assertEquals("Marca Repositorio", encontrado.get().getMarca().getNombre());
        assertEquals("Carrera 15 # 85-10", encontrado.get().getDireccion().getDireccion());
        assertNotNull(encontrado.get().getTipoDocumento());
        assertEquals("Cédula de Ciudadanía", encontrado.get().getTipoDocumento().getNombre());
        assertEquals("CC", encontrado.get().getTipoDocumento().getAbreviatura());
    }
}
