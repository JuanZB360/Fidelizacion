package com.Fidelizacion.Registro_Marca.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;

@Repository 
public interface ITipoDocumentoRepositorio extends JpaRepository<TipoDocumento, UUID> {

    Boolean existsByNombre(String nombre);
    Boolean existsByAbreviatura(String abreviatura);
    boolean existsByNombreAndIdNot(String nombre, UUID id);
    boolean existsByAbreviaturaAndIdNot(String abreviatura, UUID id);
    Optional<TipoDocumento> findByAbreviatura(String abreviatura);
    Optional<TipoDocumento> findByNombre(String nombre);

}

