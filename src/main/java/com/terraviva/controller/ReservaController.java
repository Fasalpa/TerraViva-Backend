package com.terraviva.controller;

import com.terraviva.model.Reserva;
import com.terraviva.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        Optional<Reserva> reserva = reservaService.findById(id);
        return reserva.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Reserva> buscarPorCliente(@PathVariable Long idCliente) {
        return reservaService.findByClienteId(idCliente);
    }

    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestParam Long idCliente,
                                          @RequestParam Long idHabitacion,
                                          @RequestParam LocalDate fechaInicio,
                                          @RequestParam LocalDate fechaFin) {
        Reserva reserva = reservaService.crearReserva(idCliente, idHabitacion, fechaInicio, fechaFin);

        if (reserva == null) {
            return ResponseEntity.badRequest().body("No se pudo crear la reserva");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        Reserva reserva = reservaService.cancelarReserva(id);

        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.findById(id);

        if (reserva.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}