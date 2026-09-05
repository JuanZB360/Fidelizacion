package com.Fidelizacion.Registro_Marca.modelos;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*

    datos de marca:
    nombre

*/

@Entity 
@Table(name = "marcas")
@Setter 
@Getter 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
public class Marca {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

}
