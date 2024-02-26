import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class App {
    private static void saveToXML(List<Worker> workers) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        Element xmlWorkers = doc.createElement("workers");
        doc.appendChild(xmlWorkers);

        for (Worker w : workers) {
            xmlWorkers.appendChild(w.toDomNode(doc));
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("export.xml"));

        transformer.transform(source, result);
    }
    
    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("workers.json")));

            Type listOfWorkers = new TypeToken<List<Worker>>() {}.getType();

            List<Worker> workers = gson.fromJson(reader, listOfWorkers);

            for (Worker worker : workers) {
                System.out.println(worker);
            }

            saveToXML(workers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
