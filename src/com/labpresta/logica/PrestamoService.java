package com.labpresta.logica;

import com.labpresta.modelo.Equipo;
import com.labpresta.modelo.Prestamo;
import com.labpresta.modelo.Usuario;
import com.labpresta.persistencia.EquipoDAO;
import com.labpresta.persistencia.PrestamoDAO;
import com.labpresta.persistencia.UsuarioDAO;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PrestamoService {

    private final PrestamoDAO prestamoDAO;
    private final EquipoDAO equipoDAO;
    private final UsuarioDAO usuarioDAO;

    public PrestamoService() {
        this.prestamoDAO = new PrestamoDAO();
        this.equipoDAO = new EquipoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public String calcularFechaLimite(String fechaPrestamoStr, String tipoUsuario) {
        if (fechaPrestamoStr == null || fechaPrestamoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de prestamo es obligatoria.");
        }
        LocalDate fechaPrestamo;
        try {
            fechaPrestamo = LocalDate.parse(fechaPrestamoStr.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha invalido. Use AAAA-MM-DD (ej. 2026-11-24).");
        }

        if ("Docente".equalsIgnoreCase(tipoUsuario)) {
            return fechaPrestamo.plusDays(3).toString();
        } else if ("Estudiante".equalsIgnoreCase(tipoUsuario)) {
            return fechaPrestamo.plusDays(1).toString();
        } else {
            throw new IllegalArgumentException("Tipo de usuario no valido para calcular fecha limite.");
        }
    }

    public void registrarPrestamo(String codigoEquipo, String cedulaUsuario, String fechaPrestamoStr) {
        if (codigoEquipo == null || codigoEquipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar o ingresar el codigo del equipo.");
        }
        if (cedulaUsuario == null || cedulaUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar la cedula del usuario.");
        }
        if (fechaPrestamoStr == null || fechaPrestamoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de prestamo es obligatoria.");
        }

        String codigoLimpio = codigoEquipo.trim().toUpperCase();
        String cedulaLimpia = cedulaUsuario.trim();
        String fechaLimpia = fechaPrestamoStr.trim();

        Usuario usuarioEncontrado = buscarUsuarioPorCedula(cedulaLimpia);
        if (usuarioEncontrado == null) {
            throw new IllegalArgumentException("No existe un usuario registrado con la cedula '" + cedulaLimpia + "'.");
        }

        Equipo equipoEncontrado = buscarEquipoPorCodigo(codigoLimpio);
        if (equipoEncontrado == null) {
            throw new IllegalArgumentException("No existe un equipo registrado con el codigo '" + codigoLimpio + "'.");
        }
        if (!"Disponible".equalsIgnoreCase(equipoEncontrado.getEstado())) {
            throw new IllegalArgumentException(
                    "El equipo '" + codigoLimpio + "' no esta disponible (Estado actual: " + equipoEncontrado.getEstado() + ")."
            );
        }

        String fechaLimite = calcularFechaLimite(fechaLimpia, usuarioEncontrado.getTipo());

        Prestamo nuevoPrestamo = new Prestamo(0, codigoLimpio, cedulaLimpia, fechaLimpia, fechaLimite, null);

        boolean registrado = prestamoDAO.registrarPrestamo(nuevoPrestamo);
        if (!registrado) {
            throw new RuntimeException("Error tecnico al registrar el prestamo en la base de datos.");
        }

        boolean estadoActualizado = equipoDAO.actualizarEstado(codigoLimpio, "Prestado");
        if (!estadoActualizado) {
            throw new RuntimeException("El prestamo se guardo, pero no se pudo actualizar el estado del equipo a Prestado.");
        }
    }

    private Usuario buscarUsuarioPorCedula(String cedula) {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            if (u.getCedula().equalsIgnoreCase(cedula)) {
                return u;
            }
        }
        return null;
    }

    private Equipo buscarEquipoPorCodigo(String codigo) {
        List<Equipo> equipos = equipoDAO.listarTodos();
        for (Equipo eq : equipos) {
            if (eq.getCodigo().equalsIgnoreCase(codigo)) {
                return eq;
            }
        }
        return null;
    }
}
