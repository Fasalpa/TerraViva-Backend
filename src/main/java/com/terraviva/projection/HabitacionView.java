package com.terraviva.projection;

import java.math.BigDecimal;

public interface HabitacionView {
    Long getIdHabitacion();
    String getNumero();
    String getTipo();
    BigDecimal getPrecioNoche();
    String getEstado();
    String getDescripcion();
    String getImagen();
    Integer getCapacidad();
    String getUrlDetalle();
    Boolean getVisible();
}