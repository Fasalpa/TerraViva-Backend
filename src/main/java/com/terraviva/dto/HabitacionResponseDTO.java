package com.terraviva.dto;

import com.terraviva.model.EstadoHabitacion;

import java.math.BigDecimal;

public class HabitacionResponseDTO {

    private Long idHabitacion;
    private String numero;
    private String tipo;
    private BigDecimal precioNoche;
    private EstadoHabitacion estado;
    private String descripcion;
    private String imagen;
    private Integer capacidad;
    private String urlDetalle;
    private Boolean visible;

    public HabitacionResponseDTO() {
    }

    public Long getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Long idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(BigDecimal precioNoche) {
        this.precioNoche = precioNoche;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getUrlDetalle() {
        return urlDetalle;
    }

    public void setUrlDetalle(String urlDetalle) {
        this.urlDetalle = urlDetalle;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}