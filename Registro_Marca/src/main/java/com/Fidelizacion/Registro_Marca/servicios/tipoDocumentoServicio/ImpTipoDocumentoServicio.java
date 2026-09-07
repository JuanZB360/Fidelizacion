package com.Fidelizacion.Registro_Marca.servicios.tipoDocumentoServicio;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestActualizarDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoRequestCrearDTO;
import com.Fidelizacion.Registro_Marca.DTOs.tipoDocumentoDTOs.TipoDocumentoResponseDTO;
import com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs.UsuarioResponseCompleto;
import com.Fidelizacion.Registro_Marca.exepciones.ValidacionExcepcion;
import com.Fidelizacion.Registro_Marca.modelos.TipoDocumento;
import com.Fidelizacion.Registro_Marca.repositorio.ITipoDocumentoRepositorio;
import com.Fidelizacion.Registro_Marca.validaciones.tipoDocumentoValidacion.ITipoDocumentoValidacion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpTipoDocumentoServicio implements ITipoDocumentoServicio {

    private final ITipoDocumentoRepositorio repositorioTipoDocumento;
    private final ITipoDocumentoValidacion validacionTipoDocumento;

    @Override
    @Transactional(readOnly = true)
    public TipoDocumentoResponseDTO buscarTipoDocumentoId(UUID id) {
        TipoDocumento tipoDocumento = repositorioTipoDocumento.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El tipo de documento no existe: " + id));

        return TipoDocumentoResponseDTO.fromEntity(tipoDocumento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDocumentoResponseDTO> listarTiposDocumento() {
        return repositorioTipoDocumento.findAll()
                .stream()
                .map(TipoDocumentoResponseDTO::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public TipoDocumentoResponseDTO crearTipoDocumento(TipoDocumentoRequestCrearDTO datos) {
        TipoDocumento tipoDocumento = datos.toEntity();

        validacionTipoDocumento.validarCreacionTipoDocumento(
                tipoDocumento,
                repositorioTipoDocumento.existsByNombre(tipoDocumento.getNombre()),
                repositorioTipoDocumento.existsByAbreviatura(tipoDocumento.getAbreviatura()));

        TipoDocumento guardado = repositorioTipoDocumento.save(tipoDocumento);
        return TipoDocumentoResponseDTO.fromEntity(guardado);
    }

    @Override
    @Transactional
    public TipoDocumentoResponseDTO actualizarTipoDocumento(UUID id, TipoDocumentoRequestActualizarDTO datos) {
        TipoDocumento tipoDocumento = repositorioTipoDocumento.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El tipo de documento no existe: " + id));

        Map<String, String> errores = new LinkedHashMap<>();

        if (datos.nombre() != null) {
            String nombre = datos.nombre().trim();
            try {
                validacionTipoDocumento.validarNombre(nombre);
                validacionTipoDocumento.validarQueNombreSeaUnico(
                        repositorioTipoDocumento.existsByNombreAndIdNot(nombre, id));
                tipoDocumento.setNombre(nombre);
            } catch (ValidacionExcepcion ex) {
                errores.put(ex.getCampo(), ex.getMessage());
            }
        }

        if (datos.abreviatura() != null) {
            String abreviatura = datos.abreviatura().trim().toUpperCase();
            try {
                validacionTipoDocumento.validarAbreviatura(abreviatura);
                validacionTipoDocumento.validarQueAbreviaturaSeaUnica(
                        repositorioTipoDocumento.existsByAbreviaturaAndIdNot(abreviatura, id));
                tipoDocumento.setAbreviatura(abreviatura);
            } catch (ValidacionExcepcion ex) {
                errores.put(ex.getCampo(), ex.getMessage());
            }
        }

        if (!errores.isEmpty()) {
            throw new ValidacionExcepcion(errores);
        }

        return TipoDocumentoResponseDTO.fromEntity(repositorioTipoDocumento.save(tipoDocumento));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseCompleto> buscarUsuariosTipoDocumento(UUID id) {
        TipoDocumento tipoDocumento = repositorioTipoDocumento.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El tipo de documento no existe: " + id));

        return tipoDocumento.getUsuarios()
                .stream()
                .map(UsuarioResponseCompleto::fromEntity)
                .toList();
    }

}

