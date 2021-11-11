package Tareas;

import Puertos.Slot;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Splitter 
{
    private Slot entrada;
    private Slot salida;
    private NodeList bebidas;
    
    public Splitter(Slot entrada, Slot salida)
    {
        this.entrada = entrada;
        this.salida = salida;
    }
    
    public Slot getEntrada()
    {
        return entrada;
    }
    
    public Slot getSalida()
    {
        return salida;
    }
    
    public void setEntrada(Slot entrada)
    {
        this.entrada = entrada;
    }
    
    public void setSalida(Slot salida)
    {
        this.salida = salida;
    }
    
    public void run() throws Exception
    {
        Document doc = entrada.read(); //Lee el slot de entrada
        XPath xPath = XPathFactory.newInstance().newXPath();
        bebidas = (NodeList) xPath.compile("//bebidas/*").evaluate(doc, XPathConstants.NODESET);
        NodeList order = doc.getElementsByTagName("orden_id");
        Node orden = order.item(0);
        
        for(int i=0; i<bebidas.getLength(); i++) //Recorrido para cada nodo
        {
            Document doc2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Node b = bebidas.item(i);
            
            if(b.getNodeType() == Node.ELEMENT_NODE)
            {
                Node bebida = bebidas.item(i);
                
                if(bebida.getNodeType() == Node.ELEMENT_NODE)
                {
                    Node copyNode = doc2.importNode(bebida, true);
                    doc2.appendChild(copyNode);
                    Node TagOrden = doc2.importNode(orden, true);
                    doc2.getDocumentElement().appendChild(TagOrden);
                }
            }
            
            salida.write(doc2); //Escribe en el slot de salida
        }
    }
}
