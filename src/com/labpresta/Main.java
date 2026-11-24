package com.labpresta;

import com.labpresta.logica.EquipoService;
import com.labpresta.logica.UsuarioService;
import com.labpresta.persistencia.ConexionDB;
import com.labpresta.vista.FrmGestionEquipos;
import com.labpresta.vista.FrmGestionUsuarios;
import com.labpresta.vista.FrmRegistroEquipo;
import com.labpresta.vista.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ConexionDB.inicializarBaseDatos();

        SwingUtilities.invokeLater(() -> {
            EquipoService equipoService = new EquipoService();
            UsuarioService usuarioService = new UsuarioService();
            VentanaPrincipal menu = new VentanaPrincipal();

            menu.getBtnGestionEquipos().addActionListener(e -> abrirGestionEquipos(equipoService));
            menu.getBtnGestionUsuarios().addActionListener(e -> abrirGestionUsuarios(usuarioService));

            menu.setVisible(true);
        });
    }

    private static void abrirGestionEquipos(EquipoService equipoService) {
        FrmGestionEquipos vistaGestion = new FrmGestionEquipos(equipoService);
        vistaGestion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
                            JOptionPane.INFORMATION_MESSAGE);
                    vistaRegistro.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(
                            vistaRegistro,
                            ex.getMessage(),
                            "Validación",
                            JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            vistaRegistro,
                            "Error técnico: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            vistaRegistro.setVisible(true);
        });

        vistaGestion.setVisible(true);
    }

    private static void abrirGestionUsuarios(UsuarioService usuarioService) {
        FrmGestionUsuarios vistaGestion = new FrmGestionUsuarios(usuarioService);
        vistaGestion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vistaGestion.setVisible(true);
    }
}