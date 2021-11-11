package Puertos;

import org.w3c.dom.Document;

public class PuertoSalida 
{
    private Slot salida;
    
    public void escribirSlotSalida(Document elemento) throws Exception
    {
        salida.write(elemento);
    }
    
    public void doWork(Document elemento) throws Exception
    {
        this.escribirSlotSalida(elemento);
    }
}
