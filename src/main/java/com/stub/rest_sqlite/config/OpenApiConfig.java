package com.stub.rest_sqlite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI bugFactApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cool Bug Fact API")
                        .description("Manage bug facts and render them as images.")
                        .version("v1"));
    }
}
