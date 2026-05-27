package com.terraviva.security;

import com.terraviva.model.Cliente;
import com.terraviva.repository.ClienteRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public CustomUserDetailsService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
                cliente.getEmail(),
                cliente.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + cliente.getRol().name()))
        );
    }


}