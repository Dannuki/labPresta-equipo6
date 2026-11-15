package com.labpresta.persistencia;

import com.labpresta.modelo.Equipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    /**
     * Guarda un equipo nuevo en SQLite asignándole por defecto el estado "Disponible".
     */
    public boolean insertar(Equipo equipo) {
        String sql = "INSERT INTO equipos (codigo, tipo, descripcion, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, equipo.getCodigo().trim().toUpperCase());
            pstmt.setString(2, equipo.getTipo());
            pstmt.setString(3, equipo.getDescripcion());
            pstmt.setString(4, "Disponible");
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar equipo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica directamente en la base de datos si un código ya está registrado.
     */
    public boolean existeCodigo(String codigo) {
        String sql = "SELECT 1 FROM equipos WHERE UPPER(codigo) = UPPER(?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo.trim());
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar código: " + e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve la lista completa de equipos registrados para llenar el JTable.
     */
    public List<Equipo> listarTodos() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT codigo, tipo, descripcion, estado FROM equipos ORDER BY codigo ASC";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Equipo eq = new Equipo(
                    rs.getString("codigo"),
                    rs.getString("tipo"),
                    rs.getString("descripcion"),
                    rs.getString("estado")
                );
                lista.add(eq);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza el estado de un equipo.
     */
    public boolean actualizarEstado(String codigo, String nuevoEstado) {
        String sql = "UPDATE equipos SET estado = ? WHERE codigo = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nuevoEstado);
            pstmt.setString(2, codigo);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado del equipo: " + e.getMessage());
            return false;
        }
    }
}