package com.labpresta.vista;

import com.labpresta.logica.PrestamoService;
import com.labpresta.modelo.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;

public class FrmGestionPrestamos extends JFrame {

    private final PrestamoService prestamoService;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    private JCheckBox chkSoloActivos;
    private JTextField txtFechaDevolucion;
    private JButton btnNuevoPrestamo;
    private JButton btnDevolver;
    private JButton btnActualizar;

    public FrmGestionPrestamos(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
        setTitle("LabPresta - Gestión de Préstamos y Devoluciones (HU-C3)");
        setSize(860, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        cargarPrestamos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Panel Norte: Título y filtro
        JPanel panelNorte = new JPanel(new BorderLayout(8, 8));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(12, 12, 4, 12));

        JLabel lblTitulo = new JLabel("Control de Préstamos y Devoluciones", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        chkSoloActivos = new JCheckBox("Mostrar solo préstamos activos", true);
        chkSoloActivos.addActionListener(e -> cargarPrestamos());
        panelFiltro.add(chkSoloActivos);
        panelNorte.add(panelFiltro, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);

        // Panel Centro: Tabla de préstamos
        String[] columnas = {
            "ID", "Código Equipo", "Cédula Usuario",
            "Fecha Préstamo", "Fecha Límite", "Fecha Devolución", "Estado"
        };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrestamos.setRowHeight(24);
        tablaPrestamos.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        add(scrollPane, BorderLayout.CENTER);

        // Panel Sur: Devolución con fecha editable + acciones
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelSur.add(new JLabel("Fecha Devolución (AAAA-MM-DD):"));
        txtFechaDevolucion = new JTextField(LocalDate.now().toString(), 10);
        panelSur.add(txtFechaDevolucion);

        btnDevolver = new JButton("Registrar Devolución");
        btnNuevoPrestamo = new JButton("Nuevo Préstamo");
        btnActualizar = new JButton("Actualizar");

        panelSur.add(btnDevolver);
        panelSur.add(btnNuevoPrestamo);
        panelSur.add(btnActualizar);

        add(panelSur, BorderLayout.SOUTH);

        // Eventos
        btnNuevoPrestamo.addActionListener(e -> abrirRegistroPrestamo());
        btnDevolver.addActionListener(e -> procesarDevolucion());
        btnActualizar.addActionListener(e -> cargarPrestamos());
    }

    public void cargarPrestamos() {
        modeloTabla.setRowCount(0);
        List<Prestamo> lista = chkSoloActivos.isSelected()
                ? prestamoService.listarPrestamosActivos()
                : prestamoService.listarHistorialPrestamos();

        if (lista == null) return;

        for (Prestamo p : lista) {
            String estado = prestamoService.obtenerEstadoPrestamo(p);
            String fechaDev = (p.getFechaDevolucion() == null || p.getFechaDevolucion().isEmpty())
                    ? "Pendiente"
                    : p.getFechaDevolucion();

            Object[] fila = {
                p.getId(),
                p.getCodigoEquipo(),
                p.getCedulaUsuario(),
                p.getFechaPrestamo(),
                p.getFechaLimite(),
                fechaDev,
                estado
            };
            modeloTabla.addRow(fila);
        }
    }

    private void abrirRegistroPrestamo() {
        FrmRegistroPrestamo vistaRegistro = new FrmRegistroPrestamo(prestamoService);
        vistaRegistro.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                cargarPrestamos();
            }
        });
        vistaRegistro.setVisible(true);
    }

    private void procesarDevolucion() {
        int filaSeleccionada = tablaPrestamos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un préstamo de la tabla para registrar su devolución.",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int idPrestamo = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String codigoEquipo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String fechaDevolucion = txtFechaDevolucion.getText().trim();

        try {
            String resultado = prestamoService.devolverEquipo(idPrestamo, codigoEquipo, fechaDevolucion);
            JOptionPane.showMessageDialog(
                    this,
                    "Devolución registrada (" + resultado + "). Equipo " + codigoEquipo + " ahora está Disponible.",
                    "Devolución Exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );
            cargarPrestamos();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error técnico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}