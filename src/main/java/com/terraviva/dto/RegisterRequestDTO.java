package com.terraviva.dto;

import com.terraviva.model.Rol;

public class RegisterRequestDTO {

    private String nombre;
    private String apellido;
    private String documento;
    private String email;
    private String password;
    private String telefono;
    private Rol rol;

    public RegisterRequestDTO () {
    }

    public RegisterRequestDTO (String nombre, String apellido, String documento, String email, String password, String telefono, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}