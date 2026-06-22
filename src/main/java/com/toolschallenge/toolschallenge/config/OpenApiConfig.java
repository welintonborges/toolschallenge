package com.toolschallenge.toolschallenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI toolsChallengeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pagamentos - ToolsChallenge")
                        .description("API REST para processamento de pagamentos, estornos e consultas")
                        .version("v1")
                        .license(new License().name("MIT")));
    }
}
