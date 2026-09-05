package com.Fidelizacion.Registro_Marca.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fidelizacion.Registro_Marca.modelos.Usuario;

@Repository 
public interface IUsuarioRepositorio extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findById(UUID id);

}
