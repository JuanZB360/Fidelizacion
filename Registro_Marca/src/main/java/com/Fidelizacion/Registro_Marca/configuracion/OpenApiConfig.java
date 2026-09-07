package com.Fidelizacion.Registro_Marca.configuracion;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Fidelizacion.Registro_Marca.DTOs.errorDTOs.ErrorResponseDTO;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI registroMarcaOpenAPI() {
        var errorSchema = ModelConverters.getInstance().read(ErrorResponseDTO.class).get("ErrorResponse");
        Components components = new Components();
        if (errorSchema != null) {
            components.addSchemas("ErrorResponse", errorSchema);
        }

        return new OpenAPI()
                .components(components)
                .info(new Info()
                        .title("Registro de Marcas API")
                        .description("API para gestionar usuarios, marcas, tipos de documento y ubicaciones")
                        .version("v1.0.0"));
    }

    @Bean
    public OperationCustomizer personalizarRespuestasDeError() {
        return (operation, handlerMethod) -> {
            ApiResponses responses = operation.getResponses();
            if (responses == null) {
                responses = new ApiResponses();
                operation.setResponses(responses);
            }

            Schema<?> errorRefSchema = new Schema<>().$ref("#/components/schemas/ErrorResponse");
            Content errorContent = new Content().addMediaType(
                    org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                    new MediaType().schema(errorRefSchema));

            if (responses.containsKey("400")) {
                responses.get("400").setContent(errorContent);
            } else {
                responses.addApiResponse("400", new ApiResponse()
                        .description("Error de validación o datos de solicitud inválidos")
                        .content(errorContent));
            }

            if (responses.containsKey("404")) {
                responses.get("404").setContent(errorContent);
            }

            if (!responses.containsKey("500")) {
                responses.addApiResponse("500", new ApiResponse()
                        .description("Error interno en el servidor")
                        .content(errorContent));
            }

            return operation;
        };
    }
}
