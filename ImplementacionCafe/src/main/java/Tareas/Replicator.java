/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tareas;

import org.w3c.dom.Document;
import Puertos.Slot;
import Conectores.ConectorSalida;

/**
 *
 * @author adrianmorenomonterde
 */
public class Replicator {
    private Slot input;
    private Slot output1;
    private Slot output2;
    
    public Slot getInput(){
        return input;
    }
    
    public void setInput(Slot input){
        this.input=input;
    }
    
    public Slot getOutput1(){
        return output1;
    }
    
    public Slot getOutput2(){
        return output2;
    }
    
    public void setOutput1(Slot output1){
        this.output1=output1;
    }
    
    public void setOutput2(Slot output2){
        this.output2=output2;
    }
    
    public Replicator(Slot input, Slot output1, Slot output2){
        this.input=input;
        this.output1=output1;
        this.output2=output2;
    }
    
    public void run() throws Exception {
        int contador = input.getCola().size();
        for (int i = 0; i < contador; i++) {
            Document doc = input.read(); //leemos del slot de entrada un document
            output1.write(doc); //encolamos el document en cada uno de los slots de salida.
            output2.write(doc); //encolamos el document en cada uno de los slots de salida.
        }
    }
    
    public void generaXML(Slot slot1, Slot slot2) throws Exception {
        int n = slot1.getCola().size(); //aquí se muestra la salida hacia el Traductor
        for (int i = 0; i < n; i++) {
            Document doc = slot1.read();
            slot1.write(doc);
            ConectorSalida cs = new ConectorSalida(doc);  //cambiar el document al obtenido en la salida de la última tarea
            cs.generate("input" + i);
        }
    }
}
