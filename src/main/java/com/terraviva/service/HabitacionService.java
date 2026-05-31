package com.terraviva.service;

import com.terraviva.model.EstadoHabitacion;
import com.terraviva.model.Habitacion;
import com.terraviva.repository.HabitacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    public HabitacionService(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    public List<Habitacion> findAll() {
        return habitacionRepository.findAll();
    }

    public Optional<Habitacion> findById(Long id) {
        return habitacionRepository.findById(id);
    }

    public Habitacion save(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    public Habitacion update(Long id, Habitacion datos) {
        Habitacion existente = habitacionRepository.findById(id).orElse(null);

        if (existente == null) {
            return null;
        }

        existente.setNumero(datos.getNumero());
        existente.setTipo(datos.getTipo());
        existente.setPrecioNoche(datos.getPrecioNoche());
        existente.setEstado(datos.getEstado());

        return habitacionRepository.save(existente);
    }

    public void delete(Long id) {
        habitacionRepository.deleteById(id);
    }

    public List<Habitacion> findByEstado(EstadoHabitacion estado) {
        return habitacionRepository.findByEstado(estado);
    }

    /*
    public List<Habitacion> findDisponibles(LocalDate inicio, LocalDate fin) {
        return habitacionRepository.findDisponibles(inicio, fin);
    }
    */
}