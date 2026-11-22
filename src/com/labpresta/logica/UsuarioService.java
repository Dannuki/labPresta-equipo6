package com.labpresta.logica;

import com.labpresta.modelo.Usuario;
import com.labpresta.persistencia.UsuarioDAO;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void registrarUsuario(String cedula, String nombreCompleto, String tipo, String telefono) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula del usuario es obligatoria.");
        }
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo del usuario es obligatorio.");
        }
        if (tipo == null || tipo.trim().isEmpty() || tipo.equalsIgnoreCase("Seleccione...")) {
            throw new IllegalArgumentException("Debe seleccionar el tipo de usuario (Estudiante o Docente).");
        }

        String cedulaLimpia = cedula.trim();
        String nombreLimpio = nombreCompleto.trim();
        String tipoLimpio = tipo.trim();
        String telefonoLimpio = (telefono != null && !telefono.trim().isEmpty()) ? telefono.trim() : "Sin registrar";

        if (!"Estudiante".equalsIgnoreCase(tipoLimpio) && !"Docente".equalsIgnoreCase(tipoLimpio)) {
            throw new IllegalArgumentException("El tipo de usuario debe ser 'Estudiante' o 'Docente'.");
        }

        String tipoNormalizado = tipoLimpio.substring(0, 1).toUpperCase() + tipoLimpio.substring(1).toLowerCase();
        if (usuarioDAO.existeCedula(cedulaLimpia)) {
            throw new IllegalArgumentException("La cédula '" + cedulaLimpia + "' ya está registrada.");
        }

        Usuario nuevoUsuario = new Usuario(cedulaLimpia, nombreLimpio, tipoNormalizado, telefonoLimpio);

        boolean guardado = usuarioDAO.insertar(nuevoUsuario);
        if (!guardado) {
            throw new RuntimeException("Ocurrió un error técnico al guardar el usuario en la base de datos.");
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }

    public List<Usuario> buscarUsuarios(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return usuarioDAO.listarTodos();
        }
        return usuarioDAO.buscarPorCedulaONombre(filtro);
    }
}
