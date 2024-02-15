import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import java.io.File;

public class App3 {
    public static void main(String[] args) {
        try {
            File inputFile = new File("plant_catalog.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            String expressionNode = "//PLANT[1]";

            Node node = (Node) xpath.compile(expressionNode).evaluate(doc, XPathConstants.NODE);
            System.out.println("Resultado como Node:");
            System.out.println(node.getTextContent());
            System.out.println();

            String expressionString = "//PLANT[1]/COMMON";

            String commonName = (String) xpath.compile(expressionString).evaluate(doc, XPathConstants.STRING);
            System.out.println("Resultado como String:");
            System.out.println(commonName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}