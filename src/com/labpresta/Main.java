package com.labpresta;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.labpresta.persistencia.ConexionDB;

public class Main {
    public static void main(String[] args) {
        ConexionDB.inicializarBaseDatos();
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("LabPresta - Prueba Inicial");
            ventana.setSize(400, 200);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLocationRelativeTo(null); // Centra la ventana
            
            JLabel etiqueta = new JLabel("Entorno configurado correctamente", SwingConstants.CENTER);
            ventana.add(etiqueta);
            
            ventana.setVisible(true);
        });
    }
}
