package Puertos;

import org.w3c.dom.Document;

public class PuertoEntrada 
{
    private Slot entrada = new Slot();
    
    public void setEntrada(Slot entrada)
    {
        this.entrada = entrada;
    }
    
    public Slot getEntrada()
    {
        return entrada;
    }
    
    public void escribirSlotEntrada(Document doc) throws Exception
    {
        entrada.write(doc);
    }
    
    public Document leerSlotEntrada() throws Exception
    {
        return entrada.read();
    }
}
