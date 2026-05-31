package com.terraviva.controller;

import com.terraviva.projection.HabitacionView;
import com.terraviva.repository.HabitacionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class HabitacionController {

    private final HabitacionRepository habitacionRepository;

    public HabitacionController(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    @GetMapping
    public ResponseEntity<List<HabitacionView>> getAll() {
        return ResponseEntity.ok(habitacionRepository.findAllProjected());
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        List<HabitacionView> habitaciones = habitacionRepository.findAllProjected();

        System.out.println("=== TEST PROJECTION ===");
        System.out.println("TOTAL = " + habitaciones.size());

        if (!habitaciones.isEmpty()) {
            HabitacionView h = habitaciones.get(0);
            System.out.println("ID = " + h.getIdHabitacion());
            System.out.println("NUMERO = " + h.getNumero());
            System.out.println("TIPO = " + h.getTipo());
            System.out.println("PRECIO = " + h.getPrecioNoche());
            System.out.println("ESTADO = " + h.getEstado());
            System.out.println("DESCRIPCION = " + h.getDescripcion());
            System.out.println("IMAGEN = " + h.getImagen());
            System.out.println("CAPACIDAD = " + h.getCapacidad());
            System.out.println("URL = " + h.getUrlDetalle());
            System.out.println("VISIBLE = " + h.getVisible());
        }

        return ResponseEntity.ok(habitaciones);
    }
}