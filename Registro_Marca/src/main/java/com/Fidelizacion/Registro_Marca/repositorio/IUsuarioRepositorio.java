package com.Fidelizacion.Registro_Marca.repositorio;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fidelizacion.Registro_Marca.modelos.Usuario;

@Repository 
public interface IUsuarioRepositorio extends JpaRepository<Usuario, UUID> {

    boolean existsByEmail(String email);

    boolean existsByNumeroDocumento(String numeroDocumento);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByNumeroDocumentoAndIdNot(String numeroDocumento, UUID id);
}
