package com.labpresta.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrmRegistroUsuario extends JFrame {

    private JTextField txtCedula;
    private JTextField txtNombreCompleto;
    private JComboBox<String> cbTipo;
    private JTextField txtTelefono;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    public FrmRegistroUsuario() {
        setTitle("LabPresta - Registro de Usuarios");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Registro de Usuarios del Laboratorio", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelFormulario.add(new JLabel("Cedula:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtCedula = new JTextField(15);
        panelFormulario.add(txtCedula, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        panelFormulario.add(new JLabel("Nombre completo:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtNombreCompleto = new JTextField(20);
        panelFormulario.add(txtNombreCompleto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panelFormulario.add(new JLabel("Tipo de usuario:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cbTipo = new JComboBox<>(new String[]{"Seleccione...", "Estudiante", "Docente"});
        panelFormulario.add(cbTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        panelFormulario.add(new JLabel("Telefono:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtTelefono = new JTextField(15);
        panelFormulario.add(txtTelefono, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnGuardar = new JButton("Guardar Usuario");
        btnLimpiar = new JButton("Limpiar");

        btnLimpiar.addActionListener(e -> limpiarCampos());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void limpiarCampos() {
        txtCedula.setText("");
        txtNombreCompleto.setText("");
        cbTipo.setSelectedIndex(0);
        txtTelefono.setText("");
        txtCedula.requestFocus();
    }

    public String getCedula() {
        return txtCedula.getText();
    }

    public String getNombreCompleto() {
        return txtNombreCompleto.getText();
    }

    public String getTipo() {
        return (String) cbTipo.getSelectedItem();
    }

    public String getTelefono() {
        return txtTelefono.getText();
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}
