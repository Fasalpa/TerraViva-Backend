package com.terraviva.repository;

import com.terraviva.model.EstadoHabitacion;
import com.terraviva.model.Habitacion;
import com.terraviva.projection.HabitacionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    List<Habitacion> findByEstado(EstadoHabitacion estado);

    @Query(value = """
        SELECT
            h.id_habitacion AS idHabitacion,
            h.numero AS numero,
            h.tipo AS tipo,
            h.precio_noche AS precioNoche,
            h.estado AS estado,
            h.descripcion AS descripcion,
            h.imagen AS imagen,
            h.capacidad AS capacidad,
            h.url_detalle AS urlDetalle,
            h.visible AS visible
        FROM habitaciones h
        ORDER BY h.id_habitacion
        """, nativeQuery = true)
    List<HabitacionView> findAllProjected();

    @Query("""
        SELECT h
        FROM Habitacion h
        WHERE h.idHabitacion NOT IN (
            SELECT r.habitacion.idHabitacion
            FROM Reserva r
            WHERE r.estado <> com.terraviva.model.EstadoReserva.CANCELADA
              AND r.fechaInicio < :fin
              AND r.fechaFin > :inicio
        )
        """)
    List<Habitacion> findDisponibles(@Param("inicio") LocalDate inicio,
                                     @Param("fin") LocalDate fin);
}