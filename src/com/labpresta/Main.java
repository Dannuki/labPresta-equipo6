package com.labpresta;

import com.labpresta.logica.EquipoService;
import com.labpresta.persistencia.ConexionDB;
import com.labpresta.vista.FrmGestionEquipos;
import com.labpresta.vista.FrmRegistroEquipo;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ConexionDB.inicializarBaseDatos();

        SwingUtilities.invokeLater(() -> {
            EquipoService equipoService = new EquipoService();
            FrmGestionEquipos vistaGestion = new FrmGestionEquipos(equipoService);

            vistaGestion.getBtnNuevoEquipo().addActionListener(e -> {
                FrmRegistroEquipo vistaRegistro = new FrmRegistroEquipo();

                vistaRegistro.getBtnGuardar().addActionListener(ev -> {
                    try {
                        String codigo = vistaRegistro.getCodigo();
                        String tipo = vistaRegistro.getTipo();
                        String descripcion = vistaRegistro.getDescripcion();

                        equipoService.registrarEquipo(codigo, tipo, descripcion);
                        vistaGestion.cargarEquipos();

                        JOptionPane.showMessageDialog(
                            vistaRegistro,
                            "Equipo registrado exitosamente en estado Disponible.",
                            "Registro Exitoso",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        vistaRegistro.dispose();
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(
                            vistaRegistro,
                            ex.getMessage(),
                            "Validación",
                            JOptionPane.WARNING_MESSAGE
                        );
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                            vistaRegistro,
                            "Error técnico: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                });

                vistaRegistro.setVisible(true);
            });

            vistaGestion.setVisible(true);
        });
    }
}