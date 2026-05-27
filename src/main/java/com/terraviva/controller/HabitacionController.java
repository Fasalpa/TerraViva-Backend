package com.terraviva.controller;

import com.terraviva.model.EstadoHabitacion;
import com.terraviva.model.Habitacion;
import com.terraviva.service.HabitacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }
    
    @GetMapping
    public List<Habitacion> listar() {
        return habitacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> buscarPorId(@PathVariable Long id) {
        Optional<Habitacion> habitacion = habitacionService.findById(id);
        return habitacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public List<Habitacion> buscarPorEstado(@PathVariable EstadoHabitacion estado) {
        return habitacionService.findByEstado(estado);
    }

    @GetMapping("/disponibles")
    public List<Habitacion> buscarDisponibles(@RequestParam LocalDate inicio,
                                              @RequestParam LocalDate fin) {
        return habitacionService.findDisponibles(inicio, fin);
    }

    @PostMapping
    public ResponseEntity<Habitacion> guardar(@RequestBody Habitacion habitacion) {
        Habitacion nueva = habitacionService.save(habitacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        Habitacion actualizada = habitacionService.update(id, habitacion);

        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Habitacion> habitacion = habitacionService.findById(id);

        if (habitacion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        habitacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}