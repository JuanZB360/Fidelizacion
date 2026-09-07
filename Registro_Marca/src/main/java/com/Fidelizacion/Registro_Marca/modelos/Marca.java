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

    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "marca", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference(value = "MarcaUsuario")
    @Builder.Default
    private List<Usuario> usuarios  = new ArrayList<>();

}
