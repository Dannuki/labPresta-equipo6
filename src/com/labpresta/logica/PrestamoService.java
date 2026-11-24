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

     public String devolverEquipo(int idPrestamo, String codigoEquipo, String fechaDevolucionStr) {
        if (idPrestamo <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un prestamo activo valido.");
        }
        if (codigoEquipo == null || codigoEquipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo del equipo es obligatorio para procesar la devolucion.");
        }
        if (fechaDevolucionStr == null || fechaDevolucionStr.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de devolucion es obligatoria.");
        }

        LocalDate fechaDevolucion;
        try {
            fechaDevolucion = LocalDate.parse(fechaDevolucionStr.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha de devolucion invalido. Use AAAA-MM-DD.");
        }

        Prestamo prestamoActivo = buscarPrestamoPorId(idPrestamo);
        if (prestamoActivo == null) {
            throw new IllegalArgumentException("No se encontro el prestamo con ID " + idPrestamo + ".");
        }
        if (prestamoActivo.getFechaDevolucion() != null && !prestamoActivo.getFechaDevolucion().trim().isEmpty()) {
            throw new IllegalArgumentException("Este prestamo ya fue devuelto anteriormente.");
        }

        LocalDate fechaPrestamo = LocalDate.parse(prestamoActivo.getFechaPrestamo());
        if (fechaDevolucion.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException("La fecha de devolucion no puede ser anterior a la fecha de prestamo (" + fechaPrestamo + ").");
        }

        String codigoLimpio = codigoEquipo.trim().toUpperCase();

        boolean devuelto = prestamoDAO.registrarDevolucion(idPrestamo, fechaDevolucion.toString());
        if (!devuelto) {
            throw new RuntimeException("No se pudo registrar la devolucion en la base de datos.");
        }

        boolean equipoLiberado = equipoDAO.actualizarEstado(codigoLimpio, "Disponible");
        if (!equipoLiberado) {
            throw new RuntimeException("Se registro la devolucion, pero no se pudo liberar el equipo a Disponible.");
        }

        LocalDate fechaLimite = LocalDate.parse(prestamoActivo.getFechaLimite());
        return fechaDevolucion.isAfter(fechaLimite) ? "Atrasado" : "Devuelto a tiempo";
    }

    public String obtenerEstadoPrestamo(Prestamo prestamo, LocalDate fechaReferencia) {
        LocalDate fechaLimite = LocalDate.parse(prestamo.getFechaLimite());
        String fechaDev = prestamo.getFechaDevolucion();

        if (fechaDev == null || fechaDev.trim().isEmpty()) {
            return fechaReferencia.isAfter(fechaLimite) ? "Vencido" : "Activo";
        } else {
            LocalDate fechaDevolucion = LocalDate.parse(fechaDev.trim());
            return fechaDevolucion.isAfter(fechaLimite) ? "Atrasado" : "Devuelto a tiempo";
        }
    }

    public String obtenerEstadoPrestamo(Prestamo prestamo) {
        return obtenerEstadoPrestamo(prestamo, LocalDate.now());
    }

    public List<Prestamo> listarPrestamosActivos() {
        return prestamoDAO.listarActivos();
    }

    public List<Prestamo> listarHistorialPrestamos() {
        return prestamoDAO.listarHistorial();
    }

    private Prestamo buscarPrestamoPorId(int idPrestamo) {
        for (Prestamo p : prestamoDAO.listarHistorial()) {
            if (p.getId() == idPrestamo) {
                return p;
            }
        }
        return null;
    }
}
