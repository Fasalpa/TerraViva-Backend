package com.terraviva.dto;

import com.terraviva.model.EstadoHabitacion;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class HabitacionRequestDTO {

    @NotBlank(message = "El número de habitación es obligatorio")
    @Size(max = 10, message = "El número no puede superar 10 caracteres")
    private String numero;

    @NotBlank(message = "El tipo de habitación es obligatorio")
    @Size(max = 50, message = "El tipo no puede superar 50 caracteres")
    private String tipo;

    @NotNull(message = "El precio por noche es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio por noche debe ser mayor a 0")
    private BigDecimal precioNoche;

    @NotNull(message = "El estado es obligatorio")
    private EstadoHabitacion estado;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;

    @Size(max = 255, message = "La imagen no puede superar 255 caracteres")
    private String imagen;

    private Integer capacidad;

    @Size(max = 255, message = "La URL de detalle no puede superar 255 caracteres")
    private String urlDetalle;

    private Boolean visible;

    public HabitacionRequestDTO() {
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