package com.labpresta.modelo;

public class Equipo {
    private String codigo;
    private String tipo;
    private String descripcion;
    private String estado; // Disponible, Prestado, En mantenimiento

    public Equipo(String codigo, String tipo, String descripcion) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estado = "Disponible";
    }

    public Equipo(String codigo, String tipo, String descripcion, String estado) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setEstado(String estado) {
        // Validación básica de los estados permitidos
        if (estado.equals("Disponible") || estado.equals("Prestado") || estado.equals("En mantenimiento")) {
            this.estado = estado;
        } else {
            throw new IllegalArgumentException("Estado no válido");
        }
    }

}