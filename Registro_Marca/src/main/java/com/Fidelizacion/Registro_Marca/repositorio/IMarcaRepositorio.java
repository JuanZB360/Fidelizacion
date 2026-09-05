package com.Fidelizacion.Registro_Marca.repositorio;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fidelizacion.Registro_Marca.modelos.Marca;

import java.util.List;
import java.util.Optional;


@Repository 
public interface IMarcaRepositorio extends JpaRepository<Marca, UUID> {

    Optional<Marca> findById(UUID id);
}
