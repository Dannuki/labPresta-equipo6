package com.labpresta.persistencia;

import com.labpresta.modelo.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public boolean registrarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (codigo_equipo, cedula_usuario, fecha_prestamo, fecha_limite, fecha_devolucion) VALUES (?, ?, ?, ?, NULL)";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, prestamo.getCodigoEquipo());
            pstmt.setString(2, prestamo.getCedulaUsuario());
            pstmt.setString(3, prestamo.getFechaPrestamo());
            pstmt.setString(4, prestamo.getFechaLimite());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarDevolucion(int idPrestamo, String fechaDevolucion) {
        String sql = "UPDATE prestamos SET fecha_devolucion = ? WHERE id_prestamo = ? AND fecha_devolucion IS NULL";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fechaDevolucion);
            pstmt.setInt(2, idPrestamo);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar devolución: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> listarActivos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT id_prestamo, codigo_equipo, cedula_usuario, fecha_prestamo, fecha_limite, fecha_devolucion "
                +
                "FROM prestamos WHERE fecha_devolucion IS NULL ORDER BY fecha_prestamo DESC";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPrestamo(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar préstamos activos: " + e.getMessage());
        }
        return lista;
    }

    public List<Prestamo> listarHistorial() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT id_prestamo, codigo_equipo, cedula_usuario, fecha_prestamo, fecha_limite, fecha_devolucion "
                +
                "FROM prestamos ORDER BY id_prestamo DESC";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPrestamo(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar historial de préstamos: " + e.getMessage());
        }
        return lista;
    }

    private Prestamo mapearPrestamo(ResultSet rs) throws SQLException {
        return new Prestamo(
                rs.getInt("id_prestamo"),
                rs.getString("codigo_equipo"),
                rs.getString("cedula_usuario"),
                rs.getString("fecha_prestamo"),
                rs.getString("fecha_limite"),
                rs.getString("fecha_devolucion"));
    }
}