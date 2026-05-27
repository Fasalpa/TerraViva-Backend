package com.terraviva.exception;

public class ReservaNoDisponibleException extends RuntimeException {
    public ReservaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}