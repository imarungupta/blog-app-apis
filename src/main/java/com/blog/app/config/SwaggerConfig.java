package com.blog.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(info = @Info(
        title = "Blog API: Backend Service",
        description = "This is Backend Service Crud Operation",
        contact = @Contact(name = "Arun", url = "https://www.youtube.com/watch?v=xZyUOhRpuQ0", email = "arun@gmail.com"),
        summary = "This is my backend service ",
        termsOfService = "T&C",
        license = @License(name = "", url = "", identifier = ""),
        version = "1.0"
),
        servers = {
                @Server(
                        description = "Development",
                        url = "http://localhost:8083"
                ),
                @Server(
                        description = "Test",
                        url = "http://localhost:8083"
                ),
                @Server(
                        description = "Prod",
                        url = "http://localhost:8083"
                )
        },
        security = @SecurityRequirement(name = "auth")
    
)@SecurityScheme(type = SecuritySchemeType.HTTP,
        name = "auth",
        description = "JWT Token Authentication",
        bearerFormat = "JWT",
        scheme = "bearer")
public class SwaggerConfig {

    /*@Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }*/

    /*private ApiInfo getInfo() {
        return null;
    }*/

}
