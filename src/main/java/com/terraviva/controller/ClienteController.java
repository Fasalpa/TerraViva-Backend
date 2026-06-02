package com.terraviva.controller;

import com.terraviva.dto.ClienteRequestDTO;
import com.terraviva.dto.ClienteResponseDTO;
import com.terraviva.exception.ResourceNotFoundException;
import com.terraviva.model.Cliente;
import com.terraviva.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/me")
    public ResponseEntity<ClienteResponseDTO> obtenerPerfil(Principal principal) {
        String email = principal.getName();
        Cliente cliente = clienteService.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + email));
        return ResponseEntity.ok(toResponseDTO(cliente));
    }

    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return clienteService.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return ResponseEntity.ok(toResponseDTO(cliente));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClienteRequestDTO dto) {
        if (clienteService.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese email");
        }
        if (clienteService.existsByDocumento(dto.getDocumento())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese documento");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setDocumento(dto.getDocumento());
        cliente.setEmail(dto.getEmail());
        cliente.setPassword(dto.getPassword());
        cliente.setTelefono(dto.getTelefono());
        cliente.setRol(dto.getRol());

        Cliente guardado = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ClienteRequestDTO dto) { // Sin @Valid para evitar validación de password
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));

        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setDocumento(dto.getDocumento());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setRol(dto.getRol());

        // Solo actualiza password si viene con valor de al menos 6 caracteres
        if (dto.getPassword() != null && dto.getPassword().length() >= 6) {
            cliente.setPassword(dto.getPassword());
        }

        Cliente actualizado = clienteService.save(cliente);
        return ResponseEntity.ok(toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        clienteService.delete(cliente.getIdCliente());
        return ResponseEntity.noContent().build();
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setDocumento(cliente.getDocumento());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setRol(cliente.getRol());
        return dto;
    }
}