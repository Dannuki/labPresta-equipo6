package com.labpresta.logica;

import com.labpresta.modelo.Equipo;
import com.labpresta.persistencia.EquipoDAO;
import java.util.List;

public class EquipoService {

    private final EquipoDAO equipoDAO;

    public EquipoService() {
        this.equipoDAO = new EquipoDAO();
    }

    public void registrarEquipo(String codigo, String tipo, String descripcion) {
        // 1. Validar campos obligatorios (ni nulos ni vacíos)
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del equipo es obligatorio.");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de equipo.");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del equipo es obligatoria.");
        }

        // Normalizar código (quitar espacios y pasar a mayúsculas para evitar duplicados como lap-01 y LAP-01)
        String codigoLimpio = codigo.trim().toUpperCase();
        String tipoLimpio = tipo.trim();
        String descripcionLimpia = descripcion.trim();

        // 2. Validar regla de negocio: código único
        if (equipoDAO.existeCodigo(codigoLimpio)) {
            throw new IllegalArgumentException("El código '" + codigoLimpio + "' ya está registrado en el inventario.");
        }

        // 3. Crear entidad (el constructor asigna automáticamente estado = "Disponible")
        Equipo nuevoEquipo = new Equipo(codigoLimpio, tipoLimpio, descripcionLimpia);

        // 4. Persistir en SQLite
        boolean guardado = equipoDAO.insertar(nuevoEquipo);
        if (!guardado) {
            throw new RuntimeException("Ocurrió un error técnico al guardar el equipo en la base de datos.");
        }
    }

    public List<Equipo> listarEquipos() {
        return equipoDAO.listarTodos();
    }

    public void cambiarEstadoMantenimiento(String codigo, String estadoActual, String nuevoEstado) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar un equipo válido.");
        }
        if ("Prestado".equalsIgnoreCase(estadoActual)) {
            throw new IllegalArgumentException("No se puede cambiar el estado de un equipo que está actualmente Prestado.");
        }
        if (!"Disponible".equals(nuevoEstado) && !"En mantenimiento".equals(nuevoEstado)) {
            throw new IllegalArgumentException("El nuevo estado solo puede ser 'Disponible' o 'En mantenimiento'.");
        }

        boolean actualizado = equipoDAO.actualizarEstado(codigo.trim().toUpperCase(), nuevoEstado);
        if (!actualizado) {
            throw new RuntimeException("No se pudo actualizar el estado del equipo en la base de datos.");
        }
    }
}
