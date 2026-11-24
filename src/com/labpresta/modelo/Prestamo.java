package com.labpresta.modelo;

public class Prestamo {
    private int id;
    private String codigoEquipo;
    private String cedulaUsuario;
    private String fechaPrestamo;
    private String fechaLimite;
    private String fechaDevolucion;

    public Prestamo(String codigoEquipo, String cedulaUsuario, String fechaPrestamo, String fechaLimite) {
        this.codigoEquipo = codigoEquipo;
        this.cedulaUsuario = cedulaUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = null;
    }

    public Prestamo(int id, String codigoEquipo, String cedulaUsuario, String fechaPrestamo, String fechaLimite,
            String fechaDevolucion) {
        this.id = id;
        this.codigoEquipo = codigoEquipo;
        this.cedulaUsuario = cedulaUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(String cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}