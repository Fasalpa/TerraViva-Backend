package com.terraviva.auth;

import com.terraviva.dto.AuthRequestDTO;
import com.terraviva.dto.AuthResponseDTO;
import com.terraviva.dto.RegisterRequestDTO;
import com.terraviva.model.Cliente;
import com.terraviva.repository.ClienteRepository;
import com.terraviva.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(ClienteRepository clienteRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        String email = request.getEmail().trim().toLowerCase();

        if (clienteRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado");
        }

        if (clienteRepository.existsByDocumento(request.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El documento ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setDocumento(request.getDocumento());
        cliente.setEmail(email);
        cliente.setPassword(passwordEncoder.encode(request.getPassword()));
        cliente.setTelefono(request.getTelefono());
        cliente.setRol(request.getRol());

        clienteRepository.save(cliente);

        String token = jwtService.generateToken(cliente.getEmail());
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        String email = request.getEmail().trim().toLowerCase();

        Cliente cliente = clienteRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), cliente.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        String token = jwtService.generateToken(cliente.getEmail());
        return new AuthResponseDTO(token);
    }
}