package com.terraviva.controller;

import com.terraviva.exception.ResourceNotFoundException;
import com.terraviva.model.Reserva;
import com.terraviva.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarPorId(@PathVariable Long id) {
        Reserva reserva = reservaService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        return ResponseEntity.ok(reserva);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Reserva> buscarPorCliente(@PathVariable Long idCliente) {
        return reservaService.findByClienteId(idCliente);
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestParam Long idCliente,
                                                @RequestParam Long idHabitacion,
                                                @RequestParam LocalDate fechaInicio,
                                                @RequestParam LocalDate fechaFin) {
        Reserva reserva = reservaService.crearReserva(idCliente, idHabitacion, fechaInicio, fechaFin);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable Long id) {
        Reserva reserva = reservaService.cancelarReserva(id);
        return ResponseEntity.ok(reserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}