package action;

import config.conexion;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class persona {

    private Connection conect;

    public persona() {
        conect = conexion.getConnection();
    }

    public ResultSet getSelectAll() {
        ResultSet rs = null;
        try {
            Statement stm = (Statement) conect.createStatement();
            rs = stm.executeQuery("select * from persona");
        } catch (SQLException e) {
            System.out.println("Error en consulta select " + e);
        }
        return rs;
    }

    public void setPerson(String sql) {
        try {
            Statement stm = (Statement) conect.createStatement();
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error en set person " + e);
        }
    }
    
    public void deletePerson(String sql) {
        try {
            Statement stm = (Statement) conect.createStatement();
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error en delete person " + e);
        }
    }
    
    public void updatePerson(String sql) {
        try {
            Statement stm = (Statement) conect.createStatement();
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error en update person " + e);
        }
    }
    
    
}
