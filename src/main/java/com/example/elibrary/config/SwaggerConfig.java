package com.example.elibrary.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(name = "Authorization",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.APIKEY)
@OpenAPIDefinition(
        info = @Info(
                title = "API documentation for online e-Book store",
                description = " ...",
                termsOfService = "",
                contact = @Contact(name = "Avazbek Yusupov"),
                version = "1.0.0"
        )
)
public class SwaggerConfig {
}
