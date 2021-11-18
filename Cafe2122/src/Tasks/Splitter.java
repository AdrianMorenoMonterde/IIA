package Tasks;

import Ports.Slot;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

<<<<<<< HEAD
/**
 *
 * @author adrianmorenomonterde
 */
=======
>>>>>>> c16dd40b6fef7ccf76070446678b513a5889e7c1
public class Splitter 
{
    private Slot input;
    private Slot output;
<<<<<<< HEAD
    private NodeList drinks;
=======
    private NodeList bebidas;
>>>>>>> c16dd40b6fef7ccf76070446678b513a5889e7c1
    
    public Splitter(Slot input, Slot output)
    {
        this.input = input;
        this.output = output;
    }
    
    public Slot getInput()
    {
        return input;
    }
    
    public Slot getOutput()
    {
        return output;
    }
<<<<<<< HEAD
    
    public void setInput(Slot input)
    {
        this.input = input;
    }
    
    public void setOutput(Slot output)
    {
        this.output = output;
    }
    
=======
    
    public void setInput(Slot input)
    {
        this.input = input;
    }
    
    public void setOutput(Slot output)
    {
        this.output = output;
    }
    
>>>>>>> c16dd40b6fef7ccf76070446678b513a5889e7c1
    public void run() throws Exception
    {
        Document doc = input.read(); //Lee el slot de entrada
        XPath xPath = XPathFactory.newInstance().newXPath();
<<<<<<< HEAD
        drinks = (NodeList) xPath.compile("//bebidas/*").evaluate(doc, XPathConstants.NODESET);
        NodeList order = doc.getElementsByTagName("orden_id");
        Node orden = order.item(0);
        
        for(int i=0; i<drinks.getLength(); i++) //Recorrido para cada nodo
        {
            Document doc2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Node b = drinks.item(i);
            
            if(b.getNodeType() == Node.ELEMENT_NODE)
            {
                Node bebida = drinks.item(i);
=======
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
>>>>>>> c16dd40b6fef7ccf76070446678b513a5889e7c1
                
                if(bebida.getNodeType() == Node.ELEMENT_NODE)
                {
                    Node copyNode = doc2.importNode(bebida, true);
                    doc2.appendChild(copyNode);
                    Node TagOrden = doc2.importNode(orden, true);
                    doc2.getDocumentElement().appendChild(TagOrden);
                }
            }
            
            output.write(doc2); //Escribe en el slot de salida
        }
    }
}

