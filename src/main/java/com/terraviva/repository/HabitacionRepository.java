package com.terraviva.repository;

import com.terraviva.model.Habitacion;
import com.terraviva.projection.HabitacionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    @Query("""
        SELECT
            h.idHabitacion AS idHabitacion,
            h.numero AS numero,
            h.tipo AS tipo,
            h.precioNoche AS precioNoche,
            h.estado AS estado,
            h.descripcion AS descripcion,
            h.imagen AS imagen,
            h.capacidad AS capacidad,
            h.urlDetalle AS urlDetalle,
            h.visible AS visible
        FROM Habitacion h
    """)
    List<HabitacionView> findAllProjected();

    @Query("""
        SELECT
            h.idHabitacion AS idHabitacion,
            h.numero AS numero,
            h.tipo AS tipo,
            h.precioNoche AS precioNoche,
            h.estado AS estado,
            h.descripcion AS descripcion,
            h.imagen AS imagen,
            h.capacidad AS capacidad,
            h.urlDetalle AS urlDetalle,
            h.visible AS visible
        FROM Habitacion h
        WHERE h.idHabitacion = :id
    """)
    Optional<HabitacionView> findProjectedById(@Param("id") Long id);
}