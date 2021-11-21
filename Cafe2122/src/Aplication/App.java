package Aplication;

import Connectors.*;
import Tasks.*;
import Ports.*;
import java.util.Scanner;
import javax.xml.transform.TransformerException;

public class App 
{
    public static void main(String []args) throws Exception
    { 
        InputConnector ic = new InputConnector();  //CONECTOR ENTRADA
        InputPort ip = new InputPort();     //PUERTO DE ENTRADA

        //SLOTS COMUNES A BEBIDAS FRIAS Y CALIENTES:
        Slot SplitterToDistributor = new Slot();
        Slot slotOutput1Distributor = new Slot(); //por aqui salen las bebidas calientes
        Slot slotOutput2Distributor = new Slot(); //por aqui salen las bebidas frias

        //SLOTS BEBIDAS CALIENTES:
        Slot slotOutputReplicatorHotDrinksToTranslator = new Slot();
        Slot slotOutputReplicatorHotDrinksToCorrelator = new Slot();
        Slot slotOutputTranslatorHotDrinks = new Slot();
        Slot slotOutput1CorrelatorHotDrinks = new Slot();
        Slot slotOutput2CorrelatorHotDrinks = new Slot();
        Slot slotQueryHotDrinks = new Slot();
        Slot slotOutputEnricherHotDrinks = new Slot();

        //SLOTS BEBIDAS FRIAS:
        Slot slotOutputReplicatorColdDrinksToTranslator = new Slot();
        Slot slotOutputReplicatorColdDrinksToCorrelator = new Slot();
        Slot slotOutputTranslatorColdDrinks = new Slot();
        Slot slotOutput1CorrelatorColdDrinks = new Slot();
        Slot slotOutput2CorrelatorColdDrinks = new Slot();
        Slot slotQueryColdDrinks = new Slot();
        Slot slotOutputEnricherColdDrinks = new Slot();

        //OTROS SLOTS COMUNES:
        Slot slotMergerToAgregator = new Slot();
        Slot slotOutputAggregator = new Slot();

        RequestPort rPortHotDrinks = new RequestPort(slotOutputTranslatorHotDrinks, slotQueryHotDrinks); //PUERTO DE SOLICITUD BEBIDAS CALIENTES
        RequestPort rPortColdDrinks = new RequestPort(slotOutputTranslatorColdDrinks, slotQueryColdDrinks); //PUERTO DE SOLICITUD BEBIDAS FRIAS

        //DEFINICIÓN TAREAS COMUNES:
        Splitter ts = new Splitter(ip.getInput(), SplitterToDistributor);
        Distributor td = new Distributor(SplitterToDistributor, slotOutput1Distributor, slotOutput2Distributor);

        //DEFINICIÓN TAREAS BEBIDAS CALIENTES:
        Replicator trHotDrinks = new Replicator(slotOutput1Distributor, slotOutputReplicatorHotDrinksToTranslator, slotOutputReplicatorHotDrinksToCorrelator);
        Translator ttHotDrinks = new Translator(slotOutputReplicatorHotDrinksToTranslator, slotOutputTranslatorHotDrinks);
        Correlator tcHotDrinks = new Correlator(slotQueryHotDrinks, slotOutputReplicatorHotDrinksToCorrelator, slotOutput1CorrelatorHotDrinks, slotOutput2CorrelatorHotDrinks);
        ContextEnricher teHotDrinks = new ContextEnricher(slotOutput1CorrelatorHotDrinks, slotOutput2CorrelatorHotDrinks, slotOutputEnricherHotDrinks);

        //DEFINICIÓN TAREAS BEBIDAS FRIAS:
        Replicator trColdDrinks = new Replicator(slotOutput2Distributor, slotOutputReplicatorColdDrinksToTranslator, slotOutputReplicatorColdDrinksToCorrelator);
        Translator ttColdDrinks = new Translator(slotOutputReplicatorColdDrinksToTranslator, slotOutputTranslatorColdDrinks);
        Correlator tcColdDrinks = new Correlator(slotQueryColdDrinks, slotOutputReplicatorColdDrinksToCorrelator, slotOutput1CorrelatorColdDrinks, slotOutput2CorrelatorColdDrinks);
        ContextEnricher teColdDrinks = new ContextEnricher(slotOutput1CorrelatorColdDrinks, slotOutput2CorrelatorColdDrinks, slotOutputEnricherColdDrinks);

        //DEFINICIÓN OTRAS TAREAS COMUNES:
        Merger tm = new Merger(slotOutputEnricherHotDrinks, slotOutputEnricherColdDrinks, slotMergerToAgregator);
        Aggregator ta = new Aggregator(slotMergerToAgregator, slotOutputAggregator);

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introduzca documento XML de la comanda: ");
            String fichero = sc.nextLine();
            String order_id=ic.run(fichero); //parsea el fichero XML a document
            ip.writeSlotInput(ic.getDocument()); //escribimos en el slot de entrada el document inicial

            //--------------- TAREAS COMUNES BEBIDAS FRIAS Y CALIENTES ------------
            //TASK SPLITTER:
            System.out.println("------------------------------------------");
            System.out.println("Ejecutando task Splitter...");
            ts.run();
            //TASK DISTRIBUTOR:
            System.out.println("Ejecutando task Distributor...");
            System.out.println("------------------------------------------");
            td.run();

            //---------------------------- BEBIDAS CALIENTES ----------------------
            //TASK REPLICATOR BEBIDAS CALIENTES:
            System.out.println("Ejecutando task Replicator para las bebidas calientes...");
            trHotDrinks.run();
            trHotDrinks.generaXML(slotOutputReplicatorHotDrinksToTranslator, slotOutputReplicatorHotDrinksToCorrelator); //genera los xml neicsarios para el traductor
            //TASK TRADUCTOR BEBIDAS CALIENTES:
            System.out.println("Ejecutando traductor para las bebidas calientes...");
            ttHotDrinks.run();
            //CONSULTA BD BEBIDAS CALIENTES:
            System.out.println("Barman bebidas calientes preparando bebidas, consultando stock...");
            rPortHotDrinks.doWork();
            //TASK CORRELATOR BEBIDAS CALIENTES: 
            System.out.println("Ejecutando task Correlator para las bebidas calientes...");
            tcHotDrinks.run();
            //TASK ENRICHER BEBIDAS CALIENTES:
            System.out.println("Ejecutando task Enricher para las bebidas calientes...");
            teHotDrinks.run();

            //---------------------------- BEBIDAS FRIAS --------------------------
            //TASK REPLICATOR BEBIDAS FRIAS:
            System.out.println("------------------------------------------");
            System.out.println("Ejecutando task Replicator para las bebidas frias...");
            trColdDrinks.run();
            trColdDrinks.generaXML(slotOutputReplicatorColdDrinksToTranslator, slotOutputReplicatorColdDrinksToCorrelator); //genera los xml neicsarios para el traductor
            //TASK TRADUCTOR BEBIDAS FRIAS:
            System.out.println("Ejecutando traductor para las bebidas frias...");
            ttColdDrinks.run();
            //CONSULTA BD BEBIDAS FRIAS:
            System.out.println("Barman bebidas frias preparando bebidas, consultando stock...");
            rPortColdDrinks.doWork();
            //TASK CORRELATOR BEBIDAS FRIAS: 
            System.out.println("Ejecutando task Correlator para las bebidas frias...");
            tcColdDrinks.run();
            //TASK ENRICHER BEBIDAS FRIAS:
            System.out.println("Ejecutando task Enricher para las bebidas frias...");
            teColdDrinks.run();

            //--------------------------- OTRAS TAREAS COMUNES --------------------
            //TASK MERGER:
            System.out.println("------------------------------------------");
            System.out.println("Ejecutando task Merger...");
            tm.run();
            //TASK AGGREGATOR:
            System.out.println("Ejecutando task Aggregator...");
            ta.run("1");

            //AL CONECTOR DE SALIDA:
            System.out.println("------------------------------------------");
            System.out.println("Introduzca el nombre del fichero XML de salida: ");
            String f = sc.nextLine();
            OutputConnector cs = new OutputConnector(slotOutputAggregator.read());
            cs.generate(f);

        } catch (TransformerException ex) {
        }
    }
}
