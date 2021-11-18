/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplication;

import Connectors.ConnectorDB;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;

/**
 *
 * @author adrianmorenomonterde
 */
public class App 
{
    public static void main(String []args) throws InstantiationException, IllegalAccessException, SQLException, InterruptedException
    {
        String usuario = "iia2021cafe";
        String clave = "uhuetsi2021";
        String url = "jdbc:mysql://db4free.net:3306/iia2021";
        ConnectorDB con = new ConnectorDB(); 
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
           con.conexion();
        }catch (SQLException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
