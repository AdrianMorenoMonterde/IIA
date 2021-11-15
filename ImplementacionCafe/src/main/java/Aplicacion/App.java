package Aplicacion;

import Conectores.ConectorBBDD;
import java.sql.SQLException;

public class App 
{
    public static void main(String []args) throws InstantiationException, IllegalAccessException, SQLException
    {
        ConectorBBDD test = new ConectorBBDD(); 
        test.conexion();
    }
}
