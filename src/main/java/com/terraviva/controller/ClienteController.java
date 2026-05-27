package com.terraviva.controller;

import com.terraviva.model.Cliente;
import com.terraviva.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        if (clienteService.existsByEmail(cliente.getEmail())) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        if (clienteService.existsByDocumento(cliente.getDocumento())) {
            return ResponseEntity.badRequest().body("El documento ya está registrado");
        }

        Cliente nuevo = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente actualizado = clienteService.update(id, cliente);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);

        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}