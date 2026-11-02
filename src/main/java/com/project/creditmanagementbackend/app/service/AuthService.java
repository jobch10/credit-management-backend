package com.project.creditmanagementbackend.app.service;

import com.project.creditmanagementbackend.domain.dto.request.LoginRequestDto;
import com.project.creditmanagementbackend.domain.dto.request.RegisterRequestDto;
import com.project.creditmanagementbackend.domain.dto.response.AuthUserResponseDto;
import com.project.creditmanagementbackend.domain.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import com.project.creditmanagementbackend.domain.dto.*;

public interface AuthService {

    TbUsuarioDto register(RegisterRequestDto request);

    LoginResponse login(LoginRequestDto request);

    AuthUserResponseDto authenticate(HttpServletRequest request);

    boolean validateRefreshToken(String refreshToken);

    LoginResponse refreshToken(String refreshToken, String accessToken);

    void logout(String token);

}