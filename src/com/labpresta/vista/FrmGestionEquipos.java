package com.labpresta.vista;

import com.labpresta.logica.EquipoService;
import com.labpresta.modelo.Equipo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmGestionEquipos extends JFrame {

    private final EquipoService equipoService;
    private final DefaultTableModel modeloTabla;
    private final JTable tablaEquipos;
    private final JButton btnActualizar;
    private final JButton btnEnMantenimiento;
    private final JButton btnDisponible;
    private final JButton btnNuevoEquipo;

    public FrmGestionEquipos(EquipoService equipoService) {
        this.equipoService = equipoService;

        setTitle("LabPresta - Gestión de Equipos (HU-C1)");
        setSize(760, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Encabezado
        JLabel lblTitulo = new JLabel("Inventario y Estado de Equipos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(12, 10, 4, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Modelo de tabla (celdas de solo lectura)
        String[] columnas = { "Código", "Tipo", "Descripción", "Estado" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEquipos = new JTable(modeloTabla);
        tablaEquipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEquipos.setRowHeight(24);
        tablaEquipos.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tablaEquipos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior de acciones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));

        btnNuevoEquipo = new JButton("Registrar Equipo");
        btnActualizar = new JButton("Actualizar Lista");
        btnEnMantenimiento = new JButton("Marcar 'En mantenimiento'");
        btnDisponible = new JButton("Marcar 'Disponible'");

        pnlBotones.add(btnNuevoEquipo);
        pnlBotones.add(btnActualizar);
        pnlBotones.add(btnEnMantenimiento);
        pnlBotones.add(btnDisponible);

        add(pnlBotones, BorderLayout.SOUTH);

        // Eventos
        btnActualizar.addActionListener(e -> cargarEquipos());
        btnEnMantenimiento.addActionListener(e -> cambiarEstadoSeleccionado("En mantenimiento"));
        btnDisponible.addActionListener(e -> cambiarEstadoSeleccionado("Disponible"));

        // Carga inicial desde SQLite
        cargarEquipos();
    }

    public void cargarEquipos() {
        modeloTabla.setRowCount(0);
        try {
            List<Equipo> equipos = equipoService.listarEquipos();
            for (Equipo eq : equipos) {
                Object[] fila = {
                    eq.getCodigo(),
                    eq.getTipo(),
                    eq.getDescripcion(),
                    eq.getEstado()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar los equipos: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cambiarEstadoSeleccionado(String nuevoEstado) {
        int filaSeleccionada = tablaEquipos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Selecciona un equipo de la tabla primero.",
                "Sin selección",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String codigo = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
        String estadoActual = modeloTabla.getValueAt(filaSeleccionada, 3).toString();

        if (estadoActual.equals(nuevoEstado)) {
            JOptionPane.showMessageDialog(
                this,
                "El equipo " + codigo + " ya tiene el estado '" + nuevoEstado + "'.",
                "Sin cambios",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Cambiar el estado del equipo " + codigo + " de '" + estadoActual + "' a '" + nuevoEstado + "'?",
            "Confirmar cambio de estado",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            equipoService.cambiarEstadoMantenimiento(codigo, estadoActual, nuevoEstado);
            cargarEquipos();
            JOptionPane.showMessageDialog(
                this,
                "Estado actualizado a '" + nuevoEstado + "' para el equipo " + codigo + ".",
                "Actualización exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Validación",
                JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error técnico al actualizar el estado: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public JButton getBtnNuevoEquipo() {
        return btnNuevoEquipo;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnEnMantenimiento() {
        return btnEnMantenimiento;
    }

    public JTable getTablaEquipos() {
        return tablaEquipos;
    }
}
