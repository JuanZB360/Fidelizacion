package com.Fidelizacion.Registro_Marca.modelos;

import java.time.LocalDate;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.utils.TipoDocumento;
import com.Fidelizacion.Registro_Marca.utils.Roles;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*

    Datos del usuario:
    tipo de identificación,
    número de identificación,
    nombres,
    apellidos,
    fecha de nacimiento

*/

@Entity 
@Table(name = "usuarios")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor 
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String constrasena;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    @Builder.Default
    private Roles rol = Roles.CLIENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento", unique = true)
    private String numeroDocumento;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "direccion_id")
    @JsonBackReference(value = "UbicacionUsuario")
    private Ubicacion direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "marca_id")
    @JsonBackReference(value = "MarcaUsuario")
    private Marca marca;

}
