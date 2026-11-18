package com.labpresta;

import com.labpresta.logica.EquipoService;
import com.labpresta.persistencia.ConexionDB;
import com.labpresta.vista.FrmRegistroEquipo;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 1. Crea la base de datos y tablas en SQLite si no existen
        ConexionDB.inicializarBaseDatos();

        SwingUtilities.invokeLater(() -> {
            FrmRegistroEquipo vista = new FrmRegistroEquipo();
            EquipoService equipoService = new EquipoService();

            // 2. Conecta el clic del botón Guardar con tus validaciones
            vista.getBtnGuardar().addActionListener(e -> {
                try {
                    String codigo = vista.getCodigo();
                    String tipo = vista.getTipo();
                    String descripcion = vista.getDescripcion();

                    equipoService.registrarEquipo(codigo, tipo, descripcion);

                    JOptionPane.showMessageDialog(
                            vista,
                            "Equipo registrado exitosamente en estado Disponible.",
                            "Registro Exitoso",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } catch (IllegalArgumentException ex) {
                    // Muestra alerta si hay campos vacíos o código repetido
                    JOptionPane.showMessageDialog(
                            vista,
                            ex.getMessage(),
                            "Validación",
                            JOptionPane.WARNING_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            vista,
                            "Error técnico: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            });

            vista.setVisible(true);
        });
    }
}
