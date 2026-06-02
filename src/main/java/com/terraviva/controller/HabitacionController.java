package com.terraviva.controller;

import com.terraviva.model.Habitacion;
import com.terraviva.projection.HabitacionView;
import com.terraviva.repository.HabitacionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/habitaciones")
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500",
        "https://handrymoran1.github.io"
})
public class HabitacionController {

    private final HabitacionRepository habitacionRepository;

    public HabitacionController(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    @GetMapping
    public ResponseEntity<List<HabitacionView>> getAll() {
        return ResponseEntity.ok(habitacionRepository.findAllProjected());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return habitacionRepository.findProjectedById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/visible")
    public ResponseEntity<?> actualizarVisible(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        return habitacionRepository.findById(id)
                .<ResponseEntity<?>>map(habitacion -> {
                    Boolean visible = body.get("visible");

                    if (visible == null) {
                        return ResponseEntity.badRequest().body("El campo 'visible' es obligatorio");
                    }

                    habitacion.setVisible(visible);
                    habitacionRepository.save(habitacion);

                    return ResponseEntity.ok().body(Map.of(
                            "idHabitacion", habitacion.getIdHabitacion(),
                            "visible", habitacion.getVisible()
                    ));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
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