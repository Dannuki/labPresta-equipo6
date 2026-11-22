package com.labpresta.pruebas;

import java.util.List;

import com.labpresta.modelo.Equipo;
import com.labpresta.persistencia.ConexionDB;
import com.labpresta.persistencia.EquipoDAO;


public class PruebaPersistencia {
    public static void main(String[] args) {
        System.out.println("1.  Inicializar Base de Datos");
        ConexionDB.inicializarBaseDatos();

        EquipoDAO dao = new EquipoDAO();

        System.out.println("\n 2. Prueba de Insersión - el estado por defecto es: Disponible");
        Equipo eq1 = new Equipo("LAP-01", "Laptop", "Dell Latitude i7 16GB RAM");
        Equipo eq2 = new Equipo("PROY-01", "Proyector", "Epson PowerLite HDMI");
        Equipo eq3 = new Equipo("PROY-01", "Proyector", "Epson Power HDMI");
        
        System.out.println("Insertando LAP-01: " + dao.insertar(eq1));
        System.out.println("Insertando PROY-01: " + dao.insertar(eq2));
        System.out.println("Insertando PROY-01: " + dao.insertar(eq3));

        System.out.println("\n 3. Prueba de validacion de codigo de producto duplicado");
        System.out.println("¿Existe LAP-01? (Esperado: true): " + dao.existeCodigo("LAP-01"));
        System.out.println("¿Existe COD-99? (Esperado: false): " + dao.existeCodigo("COD-99"));

        System.out.println("\n 4. Prueba de extraccion");
        List<Equipo> lista = dao.listarTodos();
        for (Equipo e : lista) {
            System.out.println(" -> " + e.getCodigo() + " | " + e.getTipo() + " | " + e.getDescripcion() + " | Estado: " + e.getEstado());
        }

        System.out.println("\n 5. Prueba de cambio de estado");
        boolean cambiado = dao.actualizarEstado("LAP-01", "En mantenimiento");
        System.out.println("¿Estado de LAP-01 actualizado?: " + cambiado);

        System.out.println("\n6. Verificacion del SQLite final");
        for (Equipo e : dao.listarTodos()) {
            System.out.println(" -> " + e.getCodigo() + " -> " + e.getEstado());
        }
    }
}