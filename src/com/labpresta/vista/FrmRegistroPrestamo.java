package com.labpresta.vista;

import com.labpresta.modelo.Prestamo;
import com.labpresta.persistencia.PrestamoDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FrmRegistroPrestamo extends JFrame {

    private final PrestamoDAO prestamoDAO;

    private JTextField txtCodigoEquipo;
    private JTextField txtCedulaUsuario;
    private JTextField txtFechaPrestamo;
    private JTextField txtFechaLimite;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnCancelar;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

    public FrmRegistroPrestamo() {
        this(new PrestamoDAO());
    }

    public FrmRegistroPrestamo(PrestamoDAO prestamoDAO) {
        this.prestamoDAO = prestamoDAO != null ? prestamoDAO : new PrestamoDAO();
        setTitle("LabPresta - Registrar Préstamo (HU-C3)");
        setSize(460, 290);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        establecerFechasPorDefecto();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Registro de Préstamo de Equipo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 4, 12));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelCampos.add(new JLabel("Código de Equipo:"));
        txtCodigoEquipo = new JTextField();
        panelCampos.add(txtCodigoEquipo);

        panelCampos.add(new JLabel("Cédula de Usuario:"));
        txtCedulaUsuario = new JTextField();
        panelCampos.add(txtCedulaUsuario);

        panelCampos.add(new JLabel("Fecha Préstamo (YYYY-MM-DD):"));
        txtFechaPrestamo = new JTextField();
        txtFechaPrestamo.setEditable(true);
        panelCampos.add(txtFechaPrestamo);

        panelCampos.add(new JLabel("Fecha Límite (YYYY-MM-DD):"));
        txtFechaLimite = new JTextField();
        txtFechaLimite.setEditable(true);
        panelCampos.add(txtFechaLimite);

        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnGuardar = new JButton("Registrar Préstamo");
        btnLimpiar = new JButton("Limpiar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarPrestamo());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void establecerFechasPorDefecto() {
        LocalDate hoy = LocalDate.now();
        txtFechaPrestamo.setText(hoy.format(FORMATO_FECHA));
        txtFechaLimite.setText(hoy.plusDays(7).format(FORMATO_FECHA));
    }

    private void limpiarCampos() {
        txtCodigoEquipo.setText("");
        txtCedulaUsuario.setText("");
        establecerFechasPorDefecto();
        txtCodigoEquipo.requestFocus();
    }

    private void guardarPrestamo() {
        String codigoEquipo = txtCodigoEquipo.getText().trim();
        String cedulaUsuario = txtCedulaUsuario.getText().trim();
        String fechaPrestamoTexto = txtFechaPrestamo.getText().trim();
        String fechaLimiteTexto = txtFechaLimite.getText().trim();

        if (codigoEquipo.isEmpty() || cedulaUsuario.isEmpty()
                || fechaPrestamoTexto.isEmpty() || fechaLimiteTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Todos los campos son obligatorios.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        LocalDate fechaPrestamo;
        LocalDate fechaLimite;
        try {
            fechaPrestamo = LocalDate.parse(fechaPrestamoTexto, FORMATO_FECHA);
            fechaLimite = LocalDate.parse(fechaLimiteTexto, FORMATO_FECHA);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Las fechas deben tener el formato válido YYYY-MM-DD (ejemplo: 2026-11-24).",
                    "Formato de fecha inválido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (fechaLimite.isBefore(fechaPrestamo)) {
            JOptionPane.showMessageDialog(
                    this,
                    "La fecha límite no puede ser anterior a la fecha de préstamo.",
                    "Fechas inválidas",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Prestamo prestamo = new Prestamo(
                codigoEquipo,
                cedulaUsuario,
                fechaPrestamo.format(FORMATO_FECHA),
                fechaLimite.format(FORMATO_FECHA)
        );

        boolean guardado = prestamoDAO.registrarPrestamo(prestamo);
        if (guardado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Préstamo registrado exitosamente.",
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el préstamo. Verifique que el código de equipo y la cédula existan y no haya restricción de base de datos.",
                    "Error al guardar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public String getCodigoEquipo() {
        return txtCodigoEquipo.getText().trim();
    }

    public String getCedulaUsuario() {
        return txtCedulaUsuario.getText().trim();
    }

    public String getFechaPrestamo() {
        return txtFechaPrestamo.getText().trim();
    }

    public String getFechaLimite() {
        return txtFechaLimite.getText().trim();
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }
}