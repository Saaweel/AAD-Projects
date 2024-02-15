import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;
import java.io.File;

public class App {
    public static void main(String[] args) {
        try {
            File inputFile = new File("plant_catalog.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            System.out.println("Precios de todas las plantas:");
            XPathExpression expr1 = xpath.compile("//PLANT/PRICE/text()");
            NodeList nodeList1 = (NodeList) expr1.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList1.getLength(); i++) {
                System.out.println(nodeList1.item(i).getNodeValue());
            }
            System.out.println();

            System.out.println("Nombre de la última planta:");
            XPathExpression expr2 = xpath.compile("(//PLANT/COMMON)[last()]/text()");
            String commonName = (String) expr2.evaluate(doc, XPathConstants.STRING);
            System.out.println(commonName);
            System.out.println();

            System.out.println("Precios de las plantas cuyo precio sea mayor a 5 e inferior a 10:");
            XPathExpression expr3 = xpath.compile("//PLANT[PRICE > 5 and PRICE < 10]/PRICE/text()");
            NodeList nodeList3 = (NodeList) expr3.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList3.getLength(); i++) {
                System.out.println(nodeList3.item(i).getNodeValue());
            }
            System.out.println();

            System.out.println("Precios de las plantas cuya zona sea 4:");
            XPathExpression expr4 = xpath.compile("//PLANT[ZONE = '4']/PRICE/text()");
            NodeList nodeList4 = (NodeList) expr4.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList4.getLength(); i++) {
                System.out.println(nodeList4.item(i).getNodeValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}