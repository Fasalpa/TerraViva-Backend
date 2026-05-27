package com.terraviva.controller;

import com.terraviva.dto.HabitacionRequestDTO;
import com.terraviva.dto.HabitacionResponseDTO;
import com.terraviva.exception.ResourceNotFoundException;
import com.terraviva.model.Habitacion;
import com.terraviva.service.HabitacionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public List<HabitacionResponseDTO> listar() {
        return habitacionService.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionResponseDTO>> listarDisponibles(
            @RequestParam("inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,
            @RequestParam("fin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fin) {

        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException("La fecha fin debe ser posterior a la fecha inicio");
        }

        List<HabitacionResponseDTO> habitaciones = habitacionService.findDisponibles(inicio, fin)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitacionResponseDTO> buscarPorId(@PathVariable Long id) {
        Habitacion habitacion = habitacionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con id: " + id));
        return ResponseEntity.ok(toResponseDTO(habitacion));
    }

    @PostMapping
    public ResponseEntity<HabitacionResponseDTO> crear(@Valid @RequestBody HabitacionRequestDTO dto) {
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(dto.getNumero());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecioNoche(dto.getPrecioNoche());
        habitacion.setEstado(dto.getEstado());

        Habitacion guardada = habitacionService.save(habitacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(guardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitacionResponseDTO> actualizar(@PathVariable Long id,
                                                            @Valid @RequestBody HabitacionRequestDTO dto) {
        Habitacion habitacion = habitacionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con id: " + id));

        habitacion.setNumero(dto.getNumero());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecioNoche(dto.getPrecioNoche());
        habitacion.setEstado(dto.getEstado());

        Habitacion actualizada = habitacionService.save(habitacion);
        return ResponseEntity.ok(toResponseDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Habitacion habitacion = habitacionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con id: " + id));

        habitacionService.delete(habitacion.getIdHabitacion());
        return ResponseEntity.noContent().build();
    }

    private HabitacionResponseDTO toResponseDTO(Habitacion habitacion) {
        HabitacionResponseDTO dto = new HabitacionResponseDTO();
        dto.setIdHabitacion(habitacion.getIdHabitacion());
        dto.setNumero(habitacion.getNumero());
        dto.setTipo(habitacion.getTipo());
        dto.setPrecioNoche(habitacion.getPrecioNoche());
        dto.setEstado(habitacion.getEstado());
        return dto;
    }
}