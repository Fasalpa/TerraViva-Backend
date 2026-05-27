package com.terraviva.repository;

import com.terraviva.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByClienteIdCliente(Long idCliente);

    List<Reserva> findByHabitacionIdHabitacion(Long idHabitacion);
}