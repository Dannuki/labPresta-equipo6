package com.labpresta.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.labpresta.modelo.Usuario;

public class UsuarioDAO {

    /**
     * Guarda un usuario nuevo en SQLite.
     */
    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (cedula, nombre_completo, tipo, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getCedula().trim());
            pstmt.setString(2, usuario.getNombreCompleto().trim());
            pstmt.setString(3, usuario.getTipo());
            pstmt.setString(4, usuario.getTelefono() != null ? usuario.getTelefono().trim() : "Sin registrar");

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica directamente en la base de datos si una cédula ya está registrada.
     */
    public boolean existeCedula(String cedula) {
        String sql = "SELECT 1 FROM usuarios WHERE cedula = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cedula.trim());
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar cédula: " + e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve la lista completa de usuarios registrados ordenada por nombre.
     */
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT cedula, nombre_completo, tipo, telefono FROM usuarios ORDER BY nombre_completo ASC";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getString("cedula"),
                    rs.getString("nombre_completo"),
                    rs.getString("tipo"),
                    rs.getString("telefono")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca usuarios cuyo número de cédula o nombre coincida parcialmente con el filtro.
     */
    public List<Usuario> buscarPorCedulaONombre(String filtro) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT cedula, nombre_completo, tipo, telefono FROM usuarios "
                   + "WHERE cedula LIKE ? OR LOWER(nombre_completo) LIKE LOWER(?) "
                   + "ORDER BY nombre_completo ASC";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String patron = "%" + filtro.trim() + "%";
            pstmt.setString(1, patron);
            pstmt.setString(2, patron);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario(
                        rs.getString("cedula"),
                        rs.getString("nombre_completo"),
                        rs.getString("tipo"),
                        rs.getString("telefono")
                    );
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuarios: " + e.getMessage());
        }
        return lista;
    }
}