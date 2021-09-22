package com.ciss.banco.config.service;

import com.ciss.banco.model.Usuario;
import com.ciss.banco.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthService implements UserDetailsService  {
    private UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String UserNameOrEmail) throws UsernameNotFoundException {
        var user = usuarioRepository.findByUsuarioOrEmail(UserNameOrEmail, UserNameOrEmail );
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + UserNameOrEmail);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsuario(), user.getSenha(),
                getAuthorities(user));
    }

    public Usuario findUsuarioByUserOrEmail(String userOrEmail) {
        return usuarioRepository.findByUsuarioOrEmail(userOrEmail, userOrEmail);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario user) {
        var authoritie = new SimpleGrantedAuthority("ROLE_ADMIN");
        var authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(authoritie);
        return authorities;
    }

}
