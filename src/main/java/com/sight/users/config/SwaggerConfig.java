package com.sight.users.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .packagesToScan("com.sight.users.controllers")
                // .pathsToMatch("/users/**") // Solo incluirá endpoints que coincidan con /users/**
                .build();
    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sight's API")
                        .version("1.0.0")
                        .description("This is an API used for managing an organization's users.")
                        .contact(new Contact()
                                .name("@Juanfe092")
                                .email("juanfe092@gmail.com")));
    }
}