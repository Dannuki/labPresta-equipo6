package com.labpresta;

import javax.swing.SwingUtilities;
import com.labpresta.persistencia.ConexionDB;
import com.labpresta.vista.FrmRegistroEquipo;

public class Main {
    public static void main(String[] args) {
        ConexionDB.inicializarBaseDatos();

        SwingUtilities.invokeLater(() -> {
            FrmRegistroEquipo ventanaRegistro = new FrmRegistroEquipo();
            ventanaRegistro.setVisible(true);
        });
    }
}