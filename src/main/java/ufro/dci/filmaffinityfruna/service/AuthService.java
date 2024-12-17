package ufro.dci.filmaffinityfruna.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.dto.LoginRequestDTO;
import ufro.dci.filmaffinityfruna.model.dto.LoginResponseDTO;
import ufro.dci.filmaffinityfruna.utils.JwtUtil;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ResponseEntity<LoginResponseDTO> login(@Valid LoginRequestDTO loginRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

            String token = jwtUtil.generateToken(authentication.getName());

            return ResponseEntity.ok(new LoginResponseDTO(token, "Login exitoso"));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO(null, "Credenciales incorrectas"));
        }
    }
}