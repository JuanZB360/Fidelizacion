package com.Fidelizacion.Registro_Marca.DTOs.usuarioDTOs;


import com.Fidelizacion.Registro_Marca.DTOs.marcaDTOs.MarcaRequestDTO;
import com.Fidelizacion.Registro_Marca.DTOs.ubicacionDTOs.UbicacionRequestDTO;
import com.Fidelizacion.Registro_Marca.modelos.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioRequestActualizar", description = "Datos opcionales para actualizar un usuario")
public record UsuarioRequestActualizarDTO(
    @Schema(description = "Dirección existente que se asociará al usuario")
    UbicacionRequestDTO direccion,
    @Schema(description = "Marca existente que se asociará al usuario")
    MarcaRequestDTO marca,
    @Schema(description = "Nueva contraseña del usuario", example = "NuevaClave1!", accessMode = Schema.AccessMode.WRITE_ONLY)
    String contrasena,
    @Schema(description = "Nuevo correo electrónico", example = "nuevo@example.com")
    String email
) {

    public Usuario toEntity(Usuario usuario){

        if (direccion != null) {
            usuario.setDireccion(direccion.toEntity());    
        }

        if (marca != null) {
            usuario.setMarca(marca.toEntity());
        }

        if (contrasena != null) {
            usuario.setConstrasena(contrasena);
        }

        if (email != null) {
            usuario.setEmail(email);
        }

        return usuario;
    }

}
