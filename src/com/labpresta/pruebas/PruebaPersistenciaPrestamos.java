package com.labpresta.pruebas;

import com.labpresta.modelo.Prestamo;
import com.labpresta.persistencia.ConexionDB;
import com.labpresta.persistencia.PrestamoDAO;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class PruebaPersistenciaPrestamos {
    public static void main(String[] args) {
        ConexionDB.inicializarBaseDatos();
        prepararDatosPrueba();

        PrestamoDAO prestamoDAO = new PrestamoDAO();

        System.out.println("1. Prueba de inserción de préstamo");
        Prestamo nuevo = new Prestamo("TEST-EQ-01", "9999999999", "2026-11-23", "2026-11-24");
        boolean insertado = prestamoDAO.registrarPrestamo(nuevo);
        System.out.println("Préstamo registrado: " + insertado);

        System.out.println("\n2. Prueba de listar préstamos activos");
        List<Prestamo> activos = prestamoDAO.listarActivos();
        int idPrueba = -1;
        for (Prestamo p : activos) {
            System.out.println("-> ID: " + p.getId() + " | Equipo: " + p.getCodigoEquipo() +
                    " | Usuario: " + p.getCedulaUsuario() + " | Límite: " + p.getFechaLimite());
            if ("TEST-EQ-01".equals(p.getCodigoEquipo())) {
                idPrueba = p.getId();
            }
        }

        System.out.println("\n3. Prueba de registrar devolución");
        if (idPrueba != -1) {
            boolean devuelto = prestamoDAO.registrarDevolucion(idPrueba, "2026-11-24");
            System.out.println("Devolución registrada para ID " + idPrueba + ": " + devuelto);
        } else {
            System.out.println("No se encontró el préstamo de prueba activo.");
        }

        System.out.println("\n4. Prueba de historial completo");
        List<Prestamo> historial = prestamoDAO.listarHistorial();
        for (Prestamo p : historial) {
            System.out.println("-> ID: " + p.getId() + " | Equipo: " + p.getCodigoEquipo() +
                    " | Préstamo: " + p.getFechaPrestamo() +
                    " | Devolución: " + p.getFechaDevolucion());
        }
    }

    private static void prepararDatosPrueba() {
        try (Connection conn = ConexionDB.conectar();
                Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "INSERT OR IGNORE INTO equipos (codigo, tipo, descripcion, estado) VALUES ('TEST-EQ-01', 'Laptop', 'Equipo de prueba', 'Disponible');");
            stmt.execute(
                    "INSERT OR IGNORE INTO usuarios (cedula, nombre_completo, tipo, telefono) VALUES ('9999999999', 'Usuario Prueba', 'Estudiante', '0999999999');");
        } catch (Exception e) {
            System.out.println("Error preparando datos de prueba: " + e.getMessage());
        }
    }
}