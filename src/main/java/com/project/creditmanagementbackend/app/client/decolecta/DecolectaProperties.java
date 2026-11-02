package com.project.creditmanagementbackend.app.client.decolecta;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "decolecta.api")
public record DecolectaProperties(String baseUrl, String token, Integer timeout) {}