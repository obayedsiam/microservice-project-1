package com.example.Library.config;

import com.example.Library.response.ApiResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Service")
                        .description("Library microservice for book information and details.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Obayed Bin Mahfuz")
                                .url("https://www.obayedsiam.wordpress.com/")
                                .email(";obayed.mahfuz@gmail.com")))
                .components(new Components()
                        .addSchemas("ApiResponse", new Schema<ApiResponse<?>>()
                                .description("Standard API response wrapper")));
    }
}
