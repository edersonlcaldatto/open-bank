package com.ciss.banco.api.Impl;

import com.ciss.banco.config.auth.JwtTokenUtil;
import com.ciss.banco.config.service.AuthService;
import com.ciss.banco.model.auth.JwtRequest;
import com.ciss.banco.model.auth.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthService authService;


    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authService = authService;
    }

    @PostMapping(value = "/autenticar")
    public ResponseEntity<JwtResponse> autenticar(@RequestBody JwtRequest authenticationRequest) {
        try {
            authenticate(authenticationRequest.getEmail(), authenticationRequest.getSenha());

            final var usuario = authService.findUsuarioByUserOrEmail(authenticationRequest.getEmail());
            final var userDetails = authService.loadUserByUsername(authenticationRequest.getEmail());
            final var token = jwtTokenUtil.generateToken(userDetails, usuario);
            return ResponseEntity.ok(new JwtResponse(token, null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(null, ex.getMessage()));
        }
    }

    private void authenticate(String usuario, String senha) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, senha));
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<JwtResponse> refreshtoken(HttpServletRequest request) {
        try {
            var oldToken = request.getHeader("Authorization").substring(7);
            String newToken = jwtTokenUtil.generateRefreshToken(jwtTokenUtil.getUsernameFromToken(oldToken));
            return ResponseEntity.ok(new JwtResponse(newToken, null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(null, ex.getMessage()));
        }
    }

}
