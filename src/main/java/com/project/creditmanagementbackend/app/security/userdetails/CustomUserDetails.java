package com.project.creditmanagementbackend.app.security.userdetails;

import com.project.creditmanagementbackend.domain.entity.TbUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final TbUser usuario;

    public CustomUserDetails(TbUser usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (usuario.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().getCode()));

            if (usuario.getRole().getModules() != null) {
                usuario.getRole().getModules().stream()
                        .filter(p -> Boolean.TRUE.equals(p.isState()))
                        .forEach(p -> {
                            String permission = p.getModule().getCode() + "_" + p.getAction().getCode();
                            authorities.add(new SimpleGrantedAuthority(permission));
                        });
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.isState();
    }
}
