import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class App {
    private int bookCount;
    private float totalBooksPrice;
    private List<String> allGenders;
    private HashMap<String, Integer> yearCount;
    
    public String getNode(Node node, int level) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < level; i++) {
            result.append("  ");
        }

        switch (node.getNodeType()) {
            case Node.DOCUMENT_NODE:
                Document doc = (Document) node;
                result.append("Documento DOM. Versión: ").append(doc.getXmlVersion())
                        .append(". Codificación: ").append(doc.getXmlEncoding()).append("\n");
                break;
            case Node.ELEMENT_NODE:
                if (node.getNodeName().equals("book")) {
                    bookCount++;
                } else if (node.getNodeName().equals("price")) {
                    totalBooksPrice += Float.parseFloat(node.getTextContent());
                } else if (node.getNodeName().equals("genre")) {
                    if (!allGenders.contains(node.getTextContent())) {
                        allGenders.add(node.getTextContent());
                    }
                } else if (node.getNodeName().equals("publish_date")) {
                    String year = node.getTextContent().split("-")[0];
                    if (yearCount.containsKey(year)) {
                        yearCount.put(year, yearCount.get(year) + 1);
                    } else {
                        yearCount.put(year, 1);
                    }
                }

                result.append("<").append(node.getNodeName());
                NamedNodeMap attributes = node.getAttributes();
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(i);
                    result.append(" @").append(attribute.getNodeName()).append("[").append(attribute.getNodeValue())
                            .append("]");
                }
                result.append(">").append("\n");
                break;
            case Node.TEXT_NODE:
                String text = node.getNodeValue().trim();
                if (text.length() > 0) {
                    result.append(node.getNodeName()).append("[").append(text).append("]").append("\n");
                }
                break;
        }

        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            result.append(getNode(childNodes.item(i), level + 1));
        }

        return result.toString();
    }

    public App() {
        this.bookCount = 0;
        this.totalBooksPrice = 0;
        this.allGenders = new ArrayList<String>();
        this.yearCount = new HashMap<String, Integer>();

        File file = new File("books.xml");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            String content = getNode(doc, 0);

            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            
            writer.write(content + "\n");

            writer.write("Numero de libros: " + bookCount + "\n");
            writer.write("Precio medio de los libros: " + totalBooksPrice / bookCount + "\n");
            writer.write("Generos: " + String.join(",", allGenders) + "\n");

            String outputYears = "";
            
            for (int i = 0; i < yearCount.size(); i++) {
                String year = (String) yearCount.keySet().toArray()[i];
                int count = yearCount.get(year);
                outputYears += count + " del " + year;

                if (i == yearCount.size() - 1) {
                    outputYears += "";
                } else if (i == yearCount.size() - 2 && yearCount.size() > 1) {
                    outputYears += " y ";
                } else if (i != 0 && yearCount.size() > 1) {
                    outputYears += ", ";
                }
            }
       

            writer.write("Numero de libros por anio: " + outputYears + "");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new App();
    }
}