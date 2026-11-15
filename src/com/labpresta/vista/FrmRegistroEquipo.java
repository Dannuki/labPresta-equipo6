package com.labpresta.vista;

import javax.swing.*;
import java.awt.*;

public class FrmRegistroEquipo extends JFrame {

    private JTextField txtCodigo;
    private JComboBox<String> cbTipo;
    private JTextArea txtDescripcion;
    private JButton btnGuardar;

    public FrmRegistroEquipo() {
        // Configuración básica de la ventana
        setTitle("LabPresta - Registrar Equipo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel central para el formulario
        JPanel pnlFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campo para el Código
        pnlFormulario.add(new JLabel("Código del Equipo (Ej. LAP-01):"));
        txtCodigo = new JTextField();
        pnlFormulario.add(txtCodigo);

        // Campo para el Tipo
        pnlFormulario.add(new JLabel("Tipo de Equipo:"));
        String[] tipos = { "Laptop", "Proyector", "Kit Arduino", "Otro" };
        cbTipo = new JComboBox<>(tipos);
        pnlFormulario.add(cbTipo);

        // Campo para la Descripción
        pnlFormulario.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        pnlFormulario.add(scrollDescripcion);

        // Estado
        pnlFormulario.add(new JLabel("Estado inicial:"));
        JLabel lblEstado = new JLabel("Disponible");
        lblEstado.setForeground(new Color(0, 153, 51));
        lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
        pnlFormulario.add(lblEstado);

        add(pnlFormulario, BorderLayout.CENTER);

        // Panel inferior para el botón
        JPanel pnlBotones = new JPanel();
        btnGuardar = new JButton("Guardar Equipo");
        pnlBotones.add(btnGuardar);
        add(pnlBotones, BorderLayout.SOUTH);
    }

    // Getters
    public String getCodigo() {
        return txtCodigo.getText().trim();
    }

    public String getTipo() {
        return cbTipo.getSelectedItem().toString();
    }

    public String getDescripcion() {
        return txtDescripcion.getText().trim();
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    // Método para probar la ventana directamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrmRegistroEquipo().setVisible(true);
        });
    }
}