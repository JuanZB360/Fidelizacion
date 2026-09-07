package com.Fidelizacion.Registro_Marca.validaciones;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.Marca;
import com.Fidelizacion.Registro_Marca.modelos.Ubicacion;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import com.Fidelizacion.Registro_Marca.utils.Roles;
import com.Fidelizacion.Registro_Marca.validaciones.marcaValidacion.ImpMarcaValidacion;
import com.Fidelizacion.Registro_Marca.validaciones.tipoDocumentoValidacion.ImpTipoDocumentoValidacion;
import com.Fidelizacion.Registro_Marca.validaciones.usuarioValidacion.ImpUsuarioValidacion;
import com.Fidelizacion.Registro_Marca.validaciones.ubicacionValidacion.ImpUbicacionValidacion;

class ValidacionesTest {

    private ImpUsuarioValidacion usuarioValidacion;
    private ImpMarcaValidacion marcaValidacion;
    private ImpUbicacionValidacion ubicacionValidacion;
    private ImpTipoDocumentoValidacion tipoDocumentoValidacion;

    @BeforeEach
    void setUp() {
        marcaValidacion = new ImpMarcaValidacion();
        ubicacionValidacion = new ImpUbicacionValidacion();
        tipoDocumentoValidacion = new ImpTipoDocumentoValidacion();
        usuarioValidacion = new ImpUsuarioValidacion(marcaValidacion, ubicacionValidacion, tipoDocumentoValidacion);
    }

    @Test
    void debeAceptarNombreConLetrasYEspacios() {
        assertDoesNotThrow(() -> usuarioValidacion.validarNombreApellido("Ana María"));
    }

    @Test
    void debeRechazarNombreConNumeros() {
        ValidacionExcepcion excepcion = assertThrows(
                ValidacionExcepcion.class,
                () -> usuarioValidacion.validarNombreApellido("Ana123"));

        assertEquals("nombre", excepcion.getCampo());
    }

    @Test
    void debeAceptarTipoDocumentoYRolValidos() {
        TipoDocumento tipoDoc = TipoDocumento.builder().nombre("Cédula de Ciudadanía").abreviatura("CC").build();
        assertDoesNotThrow(() -> usuarioValidacion.validarIdentificacion(tipoDoc));
        assertDoesNotThrow(() -> usuarioValidacion.validarRol(Roles.CLIENTE));
    }

    @Test
    void debeRechazarRolNulo() {
        assertThrows(ValidacionExcepcion.class, () -> usuarioValidacion.validarRol(null));
    }

    @Test
    void debeAceptarNumeroDocumentoDeDiezDigitos() {
        assertDoesNotThrow(() -> usuarioValidacion.validarNumeroIdentificacion("1234567890"));
    }

    @Test
    void debeRechazarNumeroDocumentoConLetras() {
        assertThrows(
                ValidacionExcepcion.class,
                () -> usuarioValidacion.validarNumeroIdentificacion("123456789A"));
    }

    @Test
    void debeAceptarPersonaConDieciochoAnios() {
        assertDoesNotThrow(() -> usuarioValidacion.validarFechaNacimiento(
                LocalDate.now().minusYears(18)));
    }

    @Test
    void debeRechazarPersonaMenorDeEdad() {
        assertThrows(
                ValidacionExcepcion.class,
                () -> usuarioValidacion.validarFechaNacimiento(
                        LocalDate.now().minusYears(18).plusDays(1)));
    }

    @Test
    void debeAceptarEmailValido() {
        assertDoesNotThrow(() -> usuarioValidacion.validarEmail("ana@example.com"));
    }

    @Test
    void debeRechazarEmailInvalido() {
        assertThrows(
                ValidacionExcepcion.class,
                () -> usuarioValidacion.validarEmail("correo-invalido"));
    }

    @Test
    void debeAceptarContrasenaCompleja() {
        assertDoesNotThrow(() -> usuarioValidacion.validarContraseña("ClaveSegura1!"));
    }

    @Test
    void debeRechazarContrasenaDebil() {
        assertThrows(
                ValidacionExcepcion.class,
                () -> usuarioValidacion.validarContraseña("clave123"));
    }

    @Test
    void debeAceptarNombreDeMarcaValido() {
        assertDoesNotThrow(() -> marcaValidacion.validarNombreMarca("Marca Central"));
    }

    @Test
    void debeRechazarMarcaDuplicada() {
        assertThrows(
                ValidacionExcepcion.class,
                () -> marcaValidacion.validarQueSeaUnico(true));
    }

    @Test
    void debeAceptarUbicacionValida() {
        assertDoesNotThrow(() -> ubicacionValidacion.validarDireccion("Calle 10#20-30"));
        assertDoesNotThrow(() -> ubicacionValidacion.validarCiudad("Bogotá"));
        assertDoesNotThrow(() -> ubicacionValidacion.validarDepartamento("Cundinamarca"));
        assertDoesNotThrow(() -> ubicacionValidacion.validarPais("Colombia"));
    }

    @Test
    void debeRechazarDireccionInvalida() {
        assertThrows(
                ValidacionExcepcion.class,
                () -> ubicacionValidacion.validarDireccion("A@"));
    }

    @Test
    void debeValidarCreacionMarcaValida() {
        Marca marca = Marca.builder().nombre("Marca Central").build();
        assertDoesNotThrow(() -> marcaValidacion.validarCreacionMarca(marca, false));
    }

    @Test
    void debeRechazarCreacionMarcaDuplicada() {
        Marca marca = Marca.builder().nombre("Marca Central").build();
        assertThrows(ValidacionExcepcion.class,
                () -> marcaValidacion.validarCreacionMarca(marca, true));
    }

    @Test
    void debeValidarUbicacionCompleta() {
        Ubicacion ubicacion = Ubicacion.builder()
                .direccion("Calle 10#20-30")
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build();
        assertDoesNotThrow(() -> ubicacionValidacion.validarUbicacion(ubicacion));
    }

    @Test
    void debeRechazarUbicacionConCampoInvalido() {
        Ubicacion ubicacion = Ubicacion.builder()
                .direccion("Calle 10#20-30")
                .ciudad("123")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build();
        assertThrows(ValidacionExcepcion.class,
                () -> ubicacionValidacion.validarUbicacion(ubicacion));
    }

    @Test
    void debeValidarLoginUsuarioValido() {
        Usuario usuario = Usuario.builder()
                .email("ana@example.com")
                .constrasena("ClaveSegura1!")
                .rol(Roles.CLIENTE)
                .build();
        assertDoesNotThrow(() -> usuarioValidacion.validacionLogin(usuario));
    }

    @Test
    void debeRechazarLoginUsuarioConContrasenaInvalida() {
        Usuario usuario = Usuario.builder()
                .email("ana@example.com")
                .constrasena("debil")
                .rol(Roles.CLIENTE)
                .build();
        assertThrows(ValidacionExcepcion.class,
                () -> usuarioValidacion.validacionLogin(usuario));
    }

    @Test
    void debeValidarCompletarInformacionValida() {
        Marca marca = Marca.builder().nombre("Marca Central").build();
        Ubicacion ubicacion = Ubicacion.builder()
                .direccion("Calle 10#20-30")
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build();
        TipoDocumento tipoDocumento = TipoDocumento.builder()
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();
        Usuario usuario = Usuario.builder()
                .nombre("Carlos")
                .apellido("Pérez")
                .tipoDocumento(tipoDocumento)
                .rol(Roles.CLIENTE)
                .numeroDocumento("1234567890")
                .fechaNacimiento(LocalDate.now().minusYears(20))
                .marca(marca)
                .direccion(ubicacion)
                .build();

        assertDoesNotThrow(() -> usuarioValidacion.validacionCompletarInformacion(usuario));
    }

    @Test
    void debeRechazarCompletarInformacionConDocumentoInvalido() {
        Marca marca = Marca.builder().nombre("Marca Central").build();
        Ubicacion ubicacion = Ubicacion.builder()
                .direccion("Calle 10#20-30")
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build();
        TipoDocumento tipoDocumento = TipoDocumento.builder()
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();
        Usuario usuario = Usuario.builder()
                .nombre("Carlos")
                .apellido("Pérez")
                .tipoDocumento(tipoDocumento)
                .rol(Roles.CLIENTE)
                .numeroDocumento("123")
                .fechaNacimiento(LocalDate.now().minusYears(20))
                .marca(marca)
                .direccion(ubicacion)
                .build();

        assertThrows(ValidacionExcepcion.class,
                () -> usuarioValidacion.validacionCompletarInformacion(usuario));
    }

    @Test
    void debeValidarTipoDocumentoValido() {
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .nombre("Cédula de Extranjería")
                .abreviatura("CE")
                .build();
        assertDoesNotThrow(() -> tipoDocumentoValidacion.validarCreacionTipoDocumento(tipoDoc, false, false));
    }

    @Test
    void debeRechazarTipoDocumentoConNombreDuplicado() {
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();
        assertThrows(ValidacionExcepcion.class,
                () -> tipoDocumentoValidacion.validarCreacionTipoDocumento(tipoDoc, true, false));
    }

    @Test
    void debeRechazarTipoDocumentoConAbreviaturaDuplicada() {
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .nombre("Cédula de Ciudadanía")
                .abreviatura("CC")
                .build();
        assertThrows(ValidacionExcepcion.class,
                () -> tipoDocumentoValidacion.validarCreacionTipoDocumento(tipoDoc, false, true));
    }

    @Test
    void debeValidarActualizarUsuarioValido() {
        Marca marca = Marca.builder().nombre("Marca Central").build();
        Usuario usuario = Usuario.builder()
                .rol(Roles.CLIENTE)
                .email("nuevo@example.com")
                .constrasena("NuevaClave1!")
                .marca(marca)
                .build();

        assertDoesNotThrow(() -> usuarioValidacion.validarActualizarUsuario(usuario));
    }

    @Test
    void debeRechazarActualizarUsuarioConContrasenaInvalida() {
        Usuario usuario = Usuario.builder()
                .rol(Roles.CLIENTE)
                .constrasena("123")
                .build();

        assertThrows(ValidacionExcepcion.class,
                () -> usuarioValidacion.validarActualizarUsuario(usuario));
    }
}
