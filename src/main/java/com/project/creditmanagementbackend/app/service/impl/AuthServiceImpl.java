package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbUserDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import com.project.creditmanagementbackend.app.exception.module.UserErrors;
import com.project.creditmanagementbackend.app.security.jwt.JwtUtil;
import com.project.creditmanagementbackend.app.security.userdetails.CustomUserDetails;
import com.project.creditmanagementbackend.app.service.AuthService;
import com.project.creditmanagementbackend.app.service.TbUserService;
import com.project.creditmanagementbackend.app.service.TokenBlacklistService;
import com.project.creditmanagementbackend.domain.dto.*;
import com.project.creditmanagementbackend.domain.dto.request.LoginRequestDto;
import com.project.creditmanagementbackend.domain.dto.request.RegisterRequestDto;
import com.project.creditmanagementbackend.domain.dto.response.AuthUserResponseDto;
import com.project.creditmanagementbackend.domain.dto.response.LoginResponse;
import com.project.creditmanagementbackend.domain.entity.TbUser;
import com.project.creditmanagementbackend.domain.mapper.custom.AuthUserMapper;
import com.project.creditmanagementbackend.domain.mapper.TbRoleMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TbUserService usuarioService;
    private final TbUserDao usuarioDao;
    private final AuthUserMapper authUserMapper;
    private final TbRoleMapper rolMapper;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    public TbUsuarioDto register(RegisterRequestDto request) {
        return usuarioService.register(request);
    }

    @Override
    public LoginResponse login(LoginRequestDto request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        long expiresIn = jwtUtil.getExpirationTime();
        long expiresRefreshIn = jwtUtil.getExpirationRefreshTime();
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        response.setType("Bearer");
        response.setExpiresIn(expiresIn);
        response.setRefreshExpiresIn( expiresRefreshIn);
        return response;
    }

    @Override
    public AuthUserResponseDto authenticate(HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);

        if (token == null || !jwtUtil.validateToken(token) || tokenBlacklistService.isTokenBlacklisted(token)) {
            throw new BusinessException(GeneralErrors.TOKEN_EXPIRED);
        }

        String username = jwtUtil.extractUsername(token);

        TbUser usuario = usuarioDao.findByEmail(username)
                .orElseThrow(() -> new BusinessException(UserErrors.USER_NOT_FOUND));

        return authUserMapper.toAuthUserResponse(
                usuario,
                rolMapper.toDto(usuario.getRole()),
                usuario.getRole().getModules().stream()
                        .filter(p -> Boolean.TRUE.equals(p.isState()))
                        .toList()
        );
    }

    @Override
    public LoginResponse refreshToken(String refreshToken, String accessToken) {
        String username = jwtUtil.extractUsername(refreshToken);
        Date refreshExpiryDate = jwtUtil.extractExpiration(refreshToken);

        tokenBlacklistService.blacklistToken(
                refreshToken,
                username,
                "REFRESH_TOKEN",
                refreshExpiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        if (accessToken != null && !accessToken.isEmpty()) {
            try {
                String accessUsername = jwtUtil.extractUsername(accessToken);
                Date accessExpiryDate = jwtUtil.extractExpiration(accessToken);
                tokenBlacklistService.blacklistToken(
                        accessToken,
                        accessUsername,
                        "ACCESS_TOKEN_REFRESHED",
                        accessExpiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            } catch (Exception e) {
            }
        }

        String newToken = jwtUtil.generateTokenFromUsername(username);
        String newRefreshToken = jwtUtil.generateRefreshTokenFromUsername(username);

        LoginResponse response = new LoginResponse();
        response.setToken(newToken);
        response.setRefreshToken(newRefreshToken);
        response.setType("Bearer");
        return response;
    }

    @Override
    public boolean validateRefreshToken(String refreshToken) {
        return jwtUtil.validateToken(refreshToken) && !tokenBlacklistService.isTokenBlacklisted(refreshToken);
    }

    @Override
    public void logout(String token) {
        String username = jwtUtil.extractUsername(token);
        Date expiryDate = jwtUtil.extractExpiration(token);
        tokenBlacklistService.blacklistToken(
                token,
                username,
                "LOGOUT",
                expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
