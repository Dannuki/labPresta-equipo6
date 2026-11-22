package com.labpresta.pruebas;

import com.labpresta.modelo.Usuario;
import com.labpresta.persistencia.UsuarioDAO;

public class PruebaPersistenciaUsuarios {
        public static void main(String[] args) {
        System.out.println("\n Pruebas de Usuario ");
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        System.out.println("\n1. Prueba de inserción y bloqueo de cédula duplicada");
        Usuario u1 = new Usuario("1723456789", "Ana María Pérez", "Estudiante", "0991234567");
        Usuario u2 = new Usuario("1710987654", "Ing. Carlos Gómez", "Docente", "0987654321");
        Usuario uDuplicado = new Usuario("1723456789", "Otro Usuario", "Estudiante", "0990000000");

        System.out.println("Insertando u1 (1723456789): " + usuarioDAO.insertar(u1));
        System.out.println("Insertando u2 (1710987654): " + usuarioDAO.insertar(u2));
        System.out.println("Insertando duplicado (1723456789): " + usuarioDAO.insertar(uDuplicado));

        System.out.println("\n2. Prueba de validación de cédula existente");
        System.out.println("¿Existe 1723456789? (Esperado: true): " + usuarioDAO.existeCedula("1723456789"));
        System.out.println("¿Existe 9999999999? (Esperado: false): " + usuarioDAO.existeCedula("9999999999"));

        System.out.println("\n3. Prueba de extracción (listarTodos)");
        for (Usuario u : usuarioDAO.listarTodos()) {
            System.out.println(" -> " + u.getCedula() + " | " + u.getNombreCompleto() + " | " + u.getTipo() + " | " + u.getTelefono());
        }

        System.out.println("\n4. Prueba de búsqueda por cédula o nombre ('Carlos')");
        for (Usuario u : usuarioDAO.buscarPorCedulaONombre("Carlos")) {
            System.out.println(" -> Encontrado: " + u.getCedula() + " | " + u.getNombreCompleto());
        }
        }
}
