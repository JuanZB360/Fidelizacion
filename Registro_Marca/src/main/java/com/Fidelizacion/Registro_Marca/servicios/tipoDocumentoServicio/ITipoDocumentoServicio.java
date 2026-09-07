package com.Fidelizacion.Registro_Marca.servicios.tipoDocumentoServicio;

import java.util.List;
import java.util.UUID;

import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;

public interface ITipoDocumentoServicio {

    TipoDocumentoResponseDTO buscarTipoDocumentoId(UUID id);
    List<TipoDocumentoResponseDTO> listarTiposDocumento();
    TipoDocumentoResponseDTO crearTipoDocumento(TipoDocumentoRequestCrearDTO datos);
    TipoDocumentoResponseDTO actualizarTipoDocumento(UUID id, TipoDocumentoRequestActualizarDTO datos);
    List<UsuarioResponseCompleto> buscarUsuariosTipoDocumento(UUID id);

}

