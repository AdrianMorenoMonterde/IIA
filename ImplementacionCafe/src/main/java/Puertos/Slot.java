package Puertos;

import java.util.LinkedList;
import java.util.Queue;
import org.w3c.dom.Document;

public class Slot 
{
    private Queue<Document> colaPrueba = new LinkedList();
    
    public Queue getCola()
    {
        return colaPrueba;
    }
    
    public Document read() throws Exception
    {
        return colaPrueba.remove();
    }
    
    public void write(Document doc) throws Exception
    {
        colaPrueba.add(doc);
    }
}
