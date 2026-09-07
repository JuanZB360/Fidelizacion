package com.Fidelizacion.Registro_Marca.validaciones;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void debeAceptarApellidoConLetrasYEspacios() {
        assertDoesNotThrow(() -> usuarioValidacion.validarApellido("Pérez Gómez"));
    }

    @Test
    void debeRechazarApellidoConNumeros() {
        ValidacionExcepcion excepcion = assertThrows(
                ValidacionExcepcion.class,
                () -> usuarioValidacion.validarApellido("Pérez123"));

        assertEquals("apellido", excepcion.getCampo());
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
        ValidacionExcepcion excepcion = assertThrows(
                ValidacionExcepcion.class,
                () -> usuarioValidacion.validarContraseña("clave123"));

        assertEquals("contrasena", excepcion.getCampo());
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

        ValidacionExcepcion excepcion = assertThrows(ValidacionExcepcion.class,
                () -> usuarioValidacion.validacionCompletarInformacion(usuario));
        assertEquals("numeroDocumento", excepcion.getCampo());
    }

    @Test
    void debeRechazarCompletarInformacionConApellidoInvalido() {
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
                .apellido("P1")
                .tipoDocumento(tipoDocumento)
                .rol(Roles.CLIENTE)
                .numeroDocumento("1234567890")
                .fechaNacimiento(LocalDate.now().minusYears(20))
                .marca(marca)
                .direccion(ubicacion)
                .build();

        ValidacionExcepcion excepcion = assertThrows(ValidacionExcepcion.class,
                () -> usuarioValidacion.validacionCompletarInformacion(usuario));
        assertEquals("apellido", excepcion.getCampo());
    }

    @Test
    void debeRechazarCompletarInformacionConMarcaInvalida() {
        Marca marca = Marca.builder().nombre("1").build();
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

        ValidacionExcepcion excepcion = assertThrows(ValidacionExcepcion.class,
                () -> usuarioValidacion.validacionCompletarInformacion(usuario));
        assertEquals("marca", excepcion.getCampo());
    }

    @Test
    void debeRechazarCompletarInformacionConTipoDocumentoInvalido() {
        Marca marca = Marca.builder().nombre("Marca Central").build();
        Ubicacion ubicacion = Ubicacion.builder()
                .direccion("Calle 10#20-30")
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .pais("Colombia")
                .build();
        TipoDocumento tipoDocumento = TipoDocumento.builder()
                .nombre("1")
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

        ValidacionExcepcion excepcion = assertThrows(ValidacionExcepcion.class,
                () -> usuarioValidacion.validacionCompletarInformacion(usuario));
        assertEquals("tipoDocumento", excepcion.getCampo());
    }

    @Test
    void debeValidarCreacionUsuarioCompletaValida() {
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
                .email("carlos@example.com")
                .constrasena("ClaveSegura1!")
                .tipoDocumento(tipoDocumento)
                .rol(Roles.CLIENTE)
                .numeroDocumento("1234567890")
                .fechaNacimiento(LocalDate.now().minusYears(20))
                .marca(marca)
                .direccion(ubicacion)
                .build();

        assertDoesNotThrow(() -> usuarioValidacion.validarCreacionUsuario(usuario));
    }

    @Test
    void debeRechazarCreacionUsuarioConEmailInvalido() {
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
                .email("correo-invalido")
                .constrasena("ClaveSegura1!")
                .tipoDocumento(tipoDocumento)
                .rol(Roles.CLIENTE)
                .numeroDocumento("1234567890")
                .fechaNacimiento(LocalDate.now().minusYears(20))
                .marca(marca)
                .direccion(ubicacion)
                .build();

        assertThrows(ValidacionExcepcion.class,
                () -> usuarioValidacion.validarCreacionUsuario(usuario));
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

    @Test
    void debeAcumularMultiplesErroresEnValidacionLogin() {
        Usuario usuario = Usuario.builder()
                .email("correo-invalido")
                .constrasena("123")
                .rol(null)
                .build();

        ValidacionExcepcion excepcion = assertThrows(
                ValidacionExcepcion.class,
                () -> usuarioValidacion.validacionLogin(usuario));

        assertEquals(3, excepcion.getErrores().size());
        assertTrue(excepcion.getErrores().containsKey("email"));
        assertTrue(excepcion.getErrores().containsKey("contrasena"));
        assertTrue(excepcion.getErrores().containsKey("rol"));
    }

    @Test
    void debeAcumularMultiplesErroresEnValidacionCreacionUsuario() {
        Usuario usuario = Usuario.builder()
                .nombre("12")
                .apellido("34")
                .email("correo-invalido")
                .constrasena("123")
                .numeroDocumento("123")
                .build();

        ValidacionExcepcion excepcion = assertThrows(
                ValidacionExcepcion.class,
                () -> usuarioValidacion.validarCreacionUsuario(usuario));

        assertTrue(excepcion.getErrores().size() >= 5);
        assertTrue(excepcion.getErrores().containsKey("nombre"));
        assertTrue(excepcion.getErrores().containsKey("apellido"));
        assertTrue(excepcion.getErrores().containsKey("email"));
        assertTrue(excepcion.getErrores().containsKey("contrasena"));
        assertTrue(excepcion.getErrores().containsKey("numeroDocumento"));
    }

    @Test
    void debeAcumularMultiplesErroresEnValidacionUbicacion() {
        Ubicacion ubicacion = Ubicacion.builder()
                .direccion("A")
                .ciudad("1")
                .departamento("2")
                .pais("3")
                .build();

        ValidacionExcepcion excepcion = assertThrows(
                ValidacionExcepcion.class,
                () -> ubicacionValidacion.validarUbicacion(ubicacion));

        assertEquals(4, excepcion.getErrores().size());
        assertTrue(excepcion.getErrores().containsKey("direccion"));
        assertTrue(excepcion.getErrores().containsKey("ciudad"));
        assertTrue(excepcion.getErrores().containsKey("departamento"));
        assertTrue(excepcion.getErrores().containsKey("pais"));
    }

    @Test
    void debeAcumularMultiplesErroresEnValidacionTipoDocumento() {
        TipoDocumento tipoDoc = TipoDocumento.builder()
                .nombre("A")
                .abreviatura("1")
                .build();

        ValidacionExcepcion excepcion = assertThrows(
                ValidacionExcepcion.class,
                () -> tipoDocumentoValidacion.validarTipoDocumento(tipoDoc));

        assertEquals(2, excepcion.getErrores().size());
        assertTrue(excepcion.getErrores().containsKey("nombre"));
        assertTrue(excepcion.getErrores().containsKey("abreviatura"));
    }
}
