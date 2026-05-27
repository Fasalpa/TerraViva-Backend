package com.terraviva.service;

import com.terraviva.exception.ReservaNoDisponibleException;
import com.terraviva.exception.ResourceNotFoundException;
import com.terraviva.model.Cliente;
import com.terraviva.model.EstadoHabitacion;
import com.terraviva.model.EstadoReserva;
import com.terraviva.model.Habitacion;
import com.terraviva.model.Reserva;
import com.terraviva.repository.ClienteRepository;
import com.terraviva.repository.HabitacionRepository;
import com.terraviva.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final HabitacionRepository habitacionRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          ClienteRepository clienteRepository,
                          HabitacionRepository habitacionRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.habitacionRepository = habitacionRepository;
    }

    public List<Reserva> findAll() {
        List<Reserva> reservas = reservaRepository.findAll();
        reservas.forEach(this::calcularDatosReserva);
        return reservas;
    }

    public Optional<Reserva> findById(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        reserva.ifPresent(this::calcularDatosReserva);
        return reserva;
    }

    public List<Reserva> findByClienteId(Long idCliente) {
        List<Reserva> reservas = reservaRepository.findByClienteIdCliente(idCliente);
        reservas.forEach(this::calcularDatosReserva);
        return reservas;
    }

    public Reserva crearReserva(Long idCliente, Long idHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }

        if (!fechaFin.isAfter(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + idCliente));

        Habitacion habitacion = habitacionRepository.findById(idHabitacion)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con id: " + idHabitacion));

        List<Habitacion> disponibles = habitacionRepository.findDisponibles(fechaInicio, fechaFin);
        boolean habitacionDisponible = disponibles.stream()
                .anyMatch(h -> h.getIdHabitacion().equals(idHabitacion));

        if (!habitacionDisponible) {
            throw new ReservaNoDisponibleException("La habitación no está disponible en ese rango de fechas");
        }

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setHabitacion(habitacion);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setEstado(EstadoReserva.RESERVADA);

        calcularDatosReserva(reserva);

        habitacion.setEstado(EstadoHabitacion.RESERVADA);
        habitacionRepository.save(habitacion);

        return reservaRepository.save(reserva);
    }

    public Reserva cancelarReserva(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + idReserva));

        reserva.setEstado(EstadoReserva.CANCELADA);

        Habitacion habitacion = reserva.getHabitacion();
        if (habitacion != null) {
            habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
            habitacionRepository.save(habitacion);
        }

        calcularDatosReserva(reserva);
        return reservaRepository.save(reserva);
    }

    public void delete(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));

        reservaRepository.delete(reserva);
    }

    private void calcularDatosReserva(Reserva reserva) {
        if (reserva == null || reserva.getFechaInicio() == null || reserva.getFechaFin() == null) {
            return;
        }

        long noches = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
        reserva.setCantidadNoches(noches);

        if (reserva.getHabitacion() != null && reserva.getHabitacion().getPrecioNoche() != null) {
            BigDecimal total = reserva.getHabitacion().getPrecioNoche()
                    .multiply(BigDecimal.valueOf(noches));
            reserva.setTotalReserva(total);
        }
    }
}