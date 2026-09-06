package com.Fidelizacion.Registro_Marca.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI registroMarcaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Registro de Marcas API")
                        .description("API para gestionar usuarios, marcas y ubicaciones")
                        .version("v1.0.0"));
    }
}
