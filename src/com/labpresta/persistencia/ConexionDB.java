package com.labpresta.persistencia;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    private static final String CARPETA_DATOS = "datos";
    private static final String URL = "jdbc:sqlite:" + CARPETA_DATOS + "/labpresta.db";

    public static Connection conectar() throws SQLException {
        File carpeta = new File(CARPETA_DATOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        return DriverManager.getConnection(URL);
    }

    /**
     * Crea las tablas iniciales si aún no existen en la base de datos.
     * Llamar a este método al iniciar el programa en Main.java.
     */
    public static void inicializarBaseDatos() {
        String sqlEquipos = """
            CREATE TABLE IF NOT EXISTS equipos (
                codigo TEXT PRIMARY KEY,
                tipo TEXT NOT NULL,
                descripcion TEXT NOT NULL,
                estado TEXT NOT NULL
            );
            """;

        String sqlUsuarios = """
            CREATE TABLE IF NOT EXISTS usuarios (
                cedula TEXT PRIMARY KEY,
                nombre_completo TEXT NOT NULL,
                tipo TEXT NOT NULL,
                telefono TEXT NOT NULL
            );
            """;

        String sqlPrestamos = """
            CREATE TABLE IF NOT EXISTS prestamos (
                id_prestamo INTEGER PRIMARY KEY AUTOINCREMENT,
                codigo_equipo TEXT NOT NULL,
                cedula_usuario TEXT NOT NULL,
                fecha_prestamo TEXT NOT NULL,
                fecha_limite TEXT NOT NULL,
                fecha_devolucion TEXT,
                FOREIGN KEY (codigo_equipo) REFERENCES equipos(codigo),
                FOREIGN KEY (cedula_usuario) REFERENCES usuarios(cedula)
            );
            """;

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
            stmt.execute(sqlEquipos);
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlPrestamos);
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos SQLite: " + e.getMessage());
        }
    }
}