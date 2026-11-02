package com.project.creditmanagementbackend.app.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Sistema Financiero API", version = "v1", description = "API para el manejo del sistema financiero, incluyendo gestión de usuarios, roles y permisos.", contact = @Contact(name = "Soporte Técnico", email = "soporte@sistema.com", url = "https://www.sistema.com/soporte")), security = @SecurityRequirement(name = "bearerAuth"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", description = "Ingrese el token JWT obtenido en el login para acceder a los endpoints protegidos.")
public class SwaggerConfig {
}
