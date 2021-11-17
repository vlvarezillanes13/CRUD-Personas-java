package action;

import java.awt.Point;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import validador.validador;

public class funcion {

    private persona person = null;
    private validador validador;
    private TableRowSorter trs;

    public funcion() {
        this.person = new persona();
        this.validador = new validador();
    }

    public boolean validarDatos(javax.swing.JTextField RUT, javax.swing.JTextField NOMBRES, javax.swing.JTextField EMAIL) {
        if (!validador.validarRut(RUT.getText()) || validador.validarTexto(NOMBRES.getText()) || !validador.validarCorreo(EMAIL.getText())) {
            return true;
        }
        return false;
    }
    
    public boolean validarRepet(javax.swing.JTextField txtRUT){
        String IN_RUT = txtRUT.getText();
        ResultSet rs = person.getAllRut();
        try {
            while (rs.next()) {
                String RUT = rs.getString("RUT");
                if(RUT.equals(IN_RUT)){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error obtener datos " + e);
        }
        return false;
    }

    public void limpiarDatos(javax.swing.JTable tablaDatos,javax.swing.JTable tablabBusqueda, javax.swing.JTextField ID, javax.swing.JTextField RUT, javax.swing.JTextField NOMBRES, javax.swing.JTextField EMAIL, javax.swing.JTextField BUSQUEDA) {
        DefaultTableModel modeloDatos = (DefaultTableModel) tablaDatos.getModel();
        DefaultTableModel modeloBusqueda = (DefaultTableModel) tablabBusqueda.getModel();

        if (tablaDatos.getRowCount() > 0) {
            for (int i = 0; i < tablaDatos.getRowCount(); i++) {
                modeloDatos.removeRow(i);
                i = i - 1;
            }
        }
        if (tablabBusqueda.getRowCount() > 0) {
            for (int i = 0; i < tablabBusqueda.getRowCount(); i++) {
                modeloBusqueda.removeRow(i);
                i = i - 1;
            }
        }
        ID.setText("");
        RUT.setText("");
        NOMBRES.setText("");
        EMAIL.setText("");
        BUSQUEDA.setText("");
    }

    public void obtenerDatos(javax.swing.JTable tablaDatos) {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        ResultSet rs = person.getSelectAll();
        try {
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String RUT = rs.getString("RUT");
                String NOMBRES = rs.getString("NOMBRES");
                String EMAIL = rs.getString("EMAIL");
                modelo.addRow(new Object[]{ID, RUT, NOMBRES, EMAIL});
            }
        } catch (SQLException e) {
            System.out.println("Error obtener datos " + e);
        }
    }

    public boolean agregarPersona(javax.swing.JTextField txtRUT, javax.swing.JTextField txtNOMBRES, javax.swing.JTextField txtEMAIL) {
        String RUT = txtRUT.getText();
        String NOMBRES = txtNOMBRES.getText();
        String EMAIL = txtEMAIL.getText();

        String sql = "insert into persona (RUT,NOMBRES,EMAIL) values ('" + RUT + "','" + NOMBRES + "','" + EMAIL + "')";

        try {
            return person.setPerson(sql);
        } catch (Exception e) {
            System.out.println("Error en agregar persona " + e);
            return person.setPerson(sql);

        }
    }

    public boolean eliminarPersona(javax.swing.JTextField txtID) {
        String ID = txtID.getText();
        String sql = "delete from persona where ID = " + ID;

        try {
            return person.deletePerson(sql);
        } catch (Exception e) {
            System.out.println("Error en eliminar persona " + e);
            return person.deletePerson(sql);
        }
    }

    public boolean actualizarPersona(javax.swing.JTextField txtID, javax.swing.JTextField txtRUT, javax.swing.JTextField txtNOMBRES, javax.swing.JTextField txtEMAIL) {
        String ID = txtID.getText();
        String RUT = txtRUT.getText();
        String NOMBRES = txtNOMBRES.getText();
        String EMAIL = txtEMAIL.getText();

        String sql = "update persona set "
                + "RUT = '" + RUT
                + "' ,NOMBRES = '" + NOMBRES
                + "' ,EMAIL = '" + EMAIL
                + "' where ID = " + ID;

        try {
            return person.updatePerson(sql);
        } catch (Exception e) {
            System.out.println("Error en actualizar persona " + e);
            return person.updatePerson(sql);
        }
    }

    public void mouseListerner(javax.swing.JTable tablaDatos, javax.swing.JTextField ID, javax.swing.JTextField RUT, javax.swing.JTextField NOMBRES, javax.swing.JTextField EMAIL) {
        tablaDatos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                JTable table = (JTable) Mouse_evt.getSource();
                Point point = Mouse_evt.getPoint();
                int row = table.rowAtPoint(point);
                if (Mouse_evt.getClickCount() == 2) {
                    ID.setText(tablaDatos.getValueAt(row, 0).toString());
                    RUT.setText(tablaDatos.getValueAt(row, 1).toString());
                    NOMBRES.setText(tablaDatos.getValueAt(row, 2).toString());
                    EMAIL.setText(tablaDatos.getValueAt(row, 3).toString());
                }
            }
        });
    }
    
    public void tableFilter(javax.swing.JTable tablaBusqueda, javax.swing.JTextField txtBusqueda, javax.swing.JComboBox opcBusqueda){
        
        DefaultTableModel modelo = (DefaultTableModel) tablaBusqueda.getModel();
        
        txtBusqueda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased( KeyEvent ke){
                String busqueda = txtBusqueda.getText();
                String tipo = opcBusqueda.getSelectedItem().toString();
                int col;
                switch(tipo){
                    case "RUT":
                        col = 1;
                        break;
                    case "ID":
                        col = 0;
                        break;
                    case "NOMBRES":
                        col = 2;
                        break;
                    case "CORREO":
                        col = 3;
                        break;
                    default:
                        col = 1;
                        break;
                }
  
                trs.setRowFilter(RowFilter.regexFilter("(?i)"+busqueda, col));
            }
        });
        
        trs = new TableRowSorter(modelo);
        tablaBusqueda.setRowSorter(trs);
    }
}
