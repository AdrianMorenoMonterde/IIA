package Conectores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConectorBBDD 
{
    private static Connection conn;
    
    String dbClass = "com.mysql.cj.jdbc.Driver";
    
    public void conexion() throws InstantiationException, IllegalAccessException, SQLException
    {
        System.out.println("Conectar");
        
        try
        {
            Class.forName(dbClass).newInstance();
            System.out.println("Driver loaded");
        }catch(ClassNotFoundException e)
        {
            System.out.println("Unable to load driver " + e);
        }
        
        try
        {
            ConectorBBDD.conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306", "iia2021cafe", "uhuetsi2021");
            System.out.println("Connection established.");
            conn.close();
            System.out.println("Connection closed. ");
        }catch(SQLException e)
        {
            System.out.println("Error connecting to database: " + e);
        }        
    }
}
