package com.labpresta.logica;

import java.util.List;

import com.labpresta.modelo.Usuario;
import com.labpresta.persistencia.UsuarioDAO;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void registrarUsuario(String cedula, String nombreCompleto, String tipo, String telefono) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cedula del usuario es obligatoria.");
        }
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo del usuario es obligatorio.");
        }
        if (tipo == null || tipo.trim().isEmpty() || tipo.equalsIgnoreCase("Seleccione...")) {
            throw new IllegalArgumentException("Debe seleccionar el tipo de usuario (Estudiante o Docente).");
        }

        String cedulaLimpia = cedula.trim();
        if (!cedulaLimpia.matches("\\d{10}")) {
            throw new IllegalArgumentException("La cedula debe contener exactamente 10 digitos numericos.");
        }

        String nombreLimpio = nombreCompleto.trim();
        String tipoLimpio = tipo.trim();
        String telefonoLimpio = (telefono != null && !telefono.trim().isEmpty()) ? telefono.trim() : "Sin registrar";

        if (!"Sin registrar".equals(telefonoLimpio) && !telefonoLimpio.matches("\\d{10}")) {
            throw new IllegalArgumentException("El telefono debe contener 10 digitos numericos o dejarse vacio.");
        }

        if (!"Estudiante".equalsIgnoreCase(tipoLimpio) && !"Docente".equalsIgnoreCase(tipoLimpio)) {
            throw new IllegalArgumentException("El tipo de usuario debe ser 'Estudiante' o 'Docente'.");
        }

        String tipoNormalizado = tipoLimpio.substring(0, 1).toUpperCase() + tipoLimpio.substring(1).toLowerCase();
        if (usuarioDAO.existeCedula(cedulaLimpia)) {
            throw new IllegalArgumentException("La cedula '" + cedulaLimpia + "' ya esta registrada.");
        }

        Usuario nuevoUsuario = new Usuario(cedulaLimpia, nombreLimpio, tipoNormalizado, telefonoLimpio);

        boolean guardado = usuarioDAO.insertar(nuevoUsuario);
        if (!guardado) {
            throw new RuntimeException("Ocurrio un error tecnico al guardar el usuario en la base de datos.");
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