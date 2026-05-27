package com.terraviva.service;

import com.terraviva.model.Cliente;
import com.terraviva.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente update(Long id, Cliente datos) {
        Cliente existente = clienteRepository.findById(id).orElse(null);

        if (existente == null) {
            return null;
        }

        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setDocumento(datos.getDocumento());
        existente.setEmail(datos.getEmail());
        existente.setTelefono(datos.getTelefono());
        existente.setRol(datos.getRol());

        return clienteRepository.save(existente);
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    public boolean existsByDocumento(String documento) {
        return clienteRepository.existsByDocumento(documento);
    }
}