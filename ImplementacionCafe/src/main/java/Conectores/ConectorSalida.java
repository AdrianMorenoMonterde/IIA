package Conectores;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author adrianmorenomonterde
 */
public class ConectorSalida 
{
    private String nombre_archivo; 
    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder builder;
    private DOMImplementation implementation;
    private Document document;
    private Element raiz;
    
    public ConectorSalida(Document docu)
    {
        this.document = docu;
    }
    
    public ConectorSalida(String name)
    {
        try
        {
            this.builder = factory.newDocumentBuilder();
            this.implementation = builder.getDOMImplementation();
            this.nombre_archivo = name;
            this.document = implementation.createDocument(null, name, null);
            this.document.setXmlVersion("1.0");
            this.raiz = document.getDocumentElement();
        } catch(ParserConfigurationException ex)
        {
            Logger.getLogger(ConectorSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generate(String name) throws TransformerException
    {
        //Generamos XML
        Source source = new DOMSource(document);
        
        //Indicamos donde lo queremos almacenar
        Result result = new StreamResult(new java.io.File(name + ".xml")); //Nombre del archivo
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
    }
}

