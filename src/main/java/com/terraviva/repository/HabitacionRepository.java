package com.terraviva.repository;

import com.terraviva.model.EstadoHabitacion;
import com.terraviva.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    List<Habitacion> findByEstado(EstadoHabitacion estado);

    @Query("""
        SELECT h FROM Habitacion h
        WHERE h.estado = 'DISPONIBLE'
        AND h.idHabitacion NOT IN (
            SELECT r.habitacion.idHabitacion FROM Reserva r
            WHERE r.estado = 'RESERVADA'
            AND r.fechaInicio < :fin
            AND r.fechaFin > :inicio
        )
    """)
    List<Habitacion> findDisponibles(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );
}