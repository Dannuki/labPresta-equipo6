package com.labpresta.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JButton btnGestionEquipos;
    private JButton btnGestionUsuarios;

    public VentanaPrincipal() {
        setTitle("LabPresta - Menú Principal");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Sistema de Préstamos - LabPresta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlBotones = new JPanel(new GridLayout(2, 1, 15, 15));
        pnlBotones.setBorder(BorderFactory.createEmptyBorder(15, 50, 30, 50));

        btnGestionEquipos = new JButton("Gestión de Equipos");
        btnGestionEquipos.setFont(new Font("Arial", Font.PLAIN, 14));

        btnGestionUsuarios = new JButton("Gestión de Usuarios");
        btnGestionUsuarios.setFont(new Font("Arial", Font.PLAIN, 14));

        pnlBotones.add(btnGestionEquipos);
        pnlBotones.add(btnGestionUsuarios);
        add(pnlBotones, BorderLayout.CENTER);
    }

    public JButton getBtnGestionEquipos() {
        return btnGestionEquipos;
    }

    public JButton getBtnGestionUsuarios() {
        return btnGestionUsuarios;
    }
}