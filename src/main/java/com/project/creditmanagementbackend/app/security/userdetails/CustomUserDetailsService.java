package com.project.creditmanagementbackend.app.security.userdetails;

import com.project.creditmanagementbackend.app.dao.TbUserDao;
import com.project.creditmanagementbackend.domain.entity.TbUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private final TbUserDao usuarioDao;

        @Override
        public UserDetails loadUserByUsername(String username)
                        throws UsernameNotFoundException {

                TbUser usuario = usuarioDao.findByEmail(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

                return new CustomUserDetails(usuario);
        }
}