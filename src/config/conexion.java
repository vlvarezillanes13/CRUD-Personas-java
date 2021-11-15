
package config;

import java.sql.Connection; //Tipo de dato Connection
import java.sql.DriverManager; // Driver Manager
import java.sql.SQLException;

public class conexion {
    
    private static final String URL = "jdbc:mysql://localhost:3306/poo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin123";
    
    
    public static Connection getConnection(){
        Connection conect = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conect = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexión "+e);
            
        }
        
        return conect;
    }
}
