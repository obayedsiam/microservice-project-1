package com.micrsoservice.ServiceDiscovery.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Service Discovery - Microservice")
                        .version("1.0.0")
                        .description("API documentation for the Service Discovery microservice.")
                        .contact(new Contact()
                                .name("Obayed Bin Mahfuz")
                                .email("obayed.mahfuz@gmail.com")
                                .url("https://obayedsiam.wordpress.com")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("com.micrsoservice") // fix spelling if needed
                .build();
    }
}
