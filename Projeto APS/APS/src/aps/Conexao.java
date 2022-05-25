/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author renat
 */
public class Conexao {
    
    public Connection conectaBD() { 
      Connection conn = null;  
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           String url = "jdbc:mysql://localhost:3306/APS_BANCO";
           conn = DriverManager.getConnection(url, "root", "%%Renato%%1234");
            
        } 
        catch (ClassNotFoundException e) { 
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return conn;
    }
    
}
