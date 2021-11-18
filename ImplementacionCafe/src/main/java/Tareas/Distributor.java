package Tareas;

import Puertos.Slot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Distributor 
{
    //Para el distributor necesitamos dos slots de salida
    private Slot entrada;
    private Slot salida1;
    private Slot salida2;
    
    public Distributor(Slot entrada, Slot salida1, Slot salida2)
    {
        this.entrada = entrada;
        this.salida1 = salida1;
        this.salida2 = salida2;
    }
    
    public Slot getEntrada()
    {
        return entrada;
    }
      
    public Slot getSalida1()
    {
        return salida1;
    }
    
    public Slot getSalida2()
    {
        return salida2;
    }
    
    public void setEntrada(Slot entrada)
    {
        this.entrada = entrada;
    }
    
    public void setSalida1(Slot salida1)
    {
        this.salida1 = salida1;
    }
    
    public void setSalida2(Slot salida2)
    {
        this.salida2 = salida2;
    }
    
    public void setSalida(Slot s)
    {
        //?????
    }
    
    public void run() throws Exception
    {
        try
        {
            int contador = entrada.getCola().size();
            
            for(int i=0; i<contador; i++)
            {
                Document doc = entrada.read();
                XPath xPath = XPathFactory.newInstance().newXPath();
                NodeList nodo = (NodeList) xPath.compile("/bebida/tipo").evaluate(doc, XPathConstants.NODESET);
                
                Node beb = nodo.item(0);
                
                if(beb.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element tipo = (Element) beb; 
                    
                    if(tipo.getTextContent().equalsIgnoreCase("caliente"))
                        salida1.write(doc);
                    else
                        salida2.write(doc);
                }
            }
        } catch(XPathExpressionException ex)
        {
            Logger.getLogger(Distributor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
