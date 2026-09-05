package com.Fidelizacion.Registro_Marca.modelos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*

    datos de la ubicacion:
     dirección,
     ciudad,
     departamento,
     país 

*/

@Entity 
@Table(name = "ubicaciones")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor 
@Builder
public class Ubicacion {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    @Column(name = "pais", nullable = false)
    private String pais;

    @OneToMany(mappedBy = "direccion", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference(value = "UbicacionUsuario")
    @Builder.Default
    private List<Usuario> usuarios  = new ArrayList<>();

}
