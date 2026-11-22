package com.labpresta.modelo;

public class Usuario {
    private String cedula;
    private String nombreCompleto;
    private String tipo;
    private String telefono;

    public Usuario(String cedula, String nombreCompleto, String tipo, String telefono) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.setTipo(tipo);
        this.telefono = telefono;
    }

    public Usuario(String cedula, String nombreCompleto, String tipo) {
        this(cedula, nombreCompleto, tipo, "Sin registrar");
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getNombre() {
        return nombreCompleto;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTipo(String tipo) {
        if (!"Estudiante".equals(tipo) && !"Docente".equals(tipo)) {
            throw new IllegalArgumentException("Tipo de usuario inválido: " + tipo);
        }
        this.tipo = tipo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}