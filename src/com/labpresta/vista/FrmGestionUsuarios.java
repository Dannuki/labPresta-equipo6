package com.labpresta.vista;

import com.labpresta.logica.UsuarioService;
import com.labpresta.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmGestionUsuarios extends JFrame {

    private final UsuarioService usuarioService;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnNuevoUsuario;

    public FrmGestionUsuarios(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        setTitle("LabPresta - Gestión de Usuarios (HU-C2)");
        setSize(760, 440);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        cargarUsuarios();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Encabezado superior con título y barra de búsqueda
        JPanel panelNorte = new JPanel(new BorderLayout(8, 8));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(12, 12, 4, 12));

        JLabel lblTitulo = new JLabel("Usuarios Registrados en el Laboratorio", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        panelBusqueda.add(new JLabel("Buscar (Cédula o Nombre):"));
        txtBuscar = new JTextField(22);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelNorte.add(panelBusqueda, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);

        // Tabla de usuarios registrados
        String[] columnas = {"Cédula", "Nombre Completo", "Tipo", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.setRowHeight(24);
        tablaUsuarios.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior de acciones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        btnNuevoUsuario = new JButton("Registrar Usuario");
        btnActualizar = new JButton("Actualizar Lista");

        panelBotones.add(btnNuevoUsuario);
        panelBotones.add(btnActualizar);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnBuscar.addActionListener(e -> buscarUsuarios());
        txtBuscar.addActionListener(e -> buscarUsuarios());
        btnActualizar.addActionListener(e -> {
            txtBuscar.setText("");
            cargarUsuarios();
        });
        btnNuevoUsuario.addActionListener(e -> abrirRegistroUsuario());
    }

    public void cargarUsuarios() {
        List<Usuario> lista = usuarioService.listarUsuarios();
        mostrarUsuariosEnTabla(lista);
    }

    private void buscarUsuarios() {
        String filtro = txtBuscar.getText();
        List<Usuario> lista = usuarioService.buscarUsuarios(filtro);
        mostrarUsuariosEnTabla(lista);
    }

    private void mostrarUsuariosEnTabla(List<Usuario> lista) {
        modeloTabla.setRowCount(0);
        if (lista == null) {
            return;
        }
        for (Usuario u : lista) {
            Object[] fila = {
                u.getCedula(),
                u.getNombreCompleto(),
                u.getTipo(),
                u.getTelefono()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void abrirRegistroUsuario() {
        FrmRegistroUsuario vistaRegistro = new FrmRegistroUsuario();

        vistaRegistro.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                cargarUsuarios();
            }
        });

        vistaRegistro.setVisible(true);
    }

    public JTable getTablaUsuarios() {
        return tablaUsuarios;
    }

    public JButton getBtnNuevoUsuario() {
        return btnNuevoUsuario;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }
}