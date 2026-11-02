package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.AuthService;
import com.project.creditmanagementbackend.app.util.response.ApiResponse;
import com.project.creditmanagementbackend.domain.dto.*;
import com.project.creditmanagementbackend.domain.dto.request.LoginRequestDto;
import com.project.creditmanagementbackend.domain.dto.request.RegisterRequestDto;
import com.project.creditmanagementbackend.domain.dto.response.AuthUserResponseDto;
import com.project.creditmanagementbackend.domain.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticación", description = "Endpoints para registro y login de usuarios")
public class AuthController {

        private final AuthService authService;

        @PostMapping("/register")
        @Operation(summary = "Registrar usuario")
        public ResponseEntity<ApiResponse<TbUsuarioDto>> register(
                        @Valid @RequestBody RegisterRequestDto request,
                        HttpServletRequest httpRequest) {

                log.info("Registration attempt for user: {}", request.getUsername());

                TbUsuarioDto response = authService.register(request);

                return ResponseEntity.status(201)
                                .body(ApiResponse.created(
                                                response,
                                                "Usuario registrado exitosamente",
                                                httpRequest.getRequestURI()));
        }

        @PostMapping("/login")
        @Operation(summary = "Iniciar sesión")
        public ResponseEntity<ApiResponse<LoginResponse>> login(
                        @Valid @RequestBody LoginRequestDto request,
                        HttpServletRequest httpRequest) {

                log.info("Login attempt for user: {}", request.getEmail());

                LoginResponse payload = authService.login(request);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                payload,
                                                "Login Exitoso",
                                                "Bienvenido " + request.getEmail(),
                                                httpRequest.getRequestURI()));
        }

        @GetMapping("/authenticate")
        @Operation(summary = "Validar sesión activa")
        public ResponseEntity<ApiResponse<AuthUserResponseDto>> authenticate(HttpServletRequest request) {
                AuthUserResponseDto usuario = authService.authenticate(request);
                return ResponseEntity.ok(ApiResponse.success(usuario, "Usuario autenticado", request.getRequestURI()));
        }

        @PostMapping("/logout")
        @Operation(summary = "Cerrar sesión y invalidar token")
        public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
                String token = request.getHeader("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                        token = token.substring(7);
                        authService.logout(token);
                }

                return ResponseEntity.ok(
                                ApiResponse.success(null, "Sesión cerrada correctamente", request.getRequestURI()));
        }

        @PostMapping("/refresh")
        @Operation(summary = "Refrescar token de acceso")
        public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(
                        @RequestBody Map<String, String> requestBody,
                        HttpServletRequest httpRequest) {

                String refreshToken = requestBody.get("refreshToken");
                String authHeader = httpRequest.getHeader("Authorization");
                String accessToken = null;

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        accessToken = authHeader.substring(7);
                }

                if (refreshToken == null || !authService.validateRefreshToken(refreshToken)) {
                        return ResponseEntity.status(401)
                                        .body(ApiResponse.<LoginResponse>unauthorized(
                                                        "Refresh token inválido o ya usado",
                                                        httpRequest.getRequestURI()));
                }

                LoginResponse payload = authService.refreshToken(refreshToken, accessToken);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                payload,
                                                "Token renovado correctamente",
                                                "",
                                                httpRequest.getRequestURI()));
        }

}