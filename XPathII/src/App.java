import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.xpath.*;

import java.io.File;

public class App {
    public static void main(String[] args) {
        try {
            File inputFile = new File("books.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            XPath xpath = XPathFactory.newInstance().newXPath();

            XPathExpression expr1 = xpath.compile("//book[count(author) >= 3]/@category");
            NodeList categoryList = (NodeList) expr1.evaluate(doc, XPathConstants.NODESET);
            System.out.println("Categoría de libros con 3 o más autores:");
            for (int i = 0; i < categoryList.getLength(); i++) {
                System.out.println(categoryList.item(i).getNodeValue());
            }

            XPathExpression expr2 = xpath.compile("//book[@category='web' and price > 30]/title/text()");
            NodeList titleList = (NodeList) expr2.evaluate(doc, XPathConstants.NODESET);
            System.out.println("\nTítulos de libros con precio mayor a 30 y categoría web:");
            for (int i = 0; i < titleList.getLength(); i++) {
                System.out.println(titleList.item(i).getNodeValue());
            }

            XPathExpression expr3 = xpath.compile("//book[title='XQuery Kick Start']/author[last()]/text()");
            String lastAuthor = (String) expr3.evaluate(doc, XPathConstants.STRING);
            System.out.println("\nÚltimo autor de 'XQuery Kick Start': " + lastAuthor);

            XPathExpression expr4 = xpath.compile("//book[not(@category='web')]/title/@lang");
            NodeList langList = (NodeList) expr4.evaluate(doc, XPathConstants.NODESET);
            System.out.println("\nLenguaje de libros con categoría diferente de 'web':");
            for (int i = 0; i < langList.getLength(); i++) {
                System.out.println(langList.item(i).getNodeValue());
            }

            XPathExpression expr5 = xpath.compile("//book[@cover]/year/text()");
            NodeList yearList = (NodeList) expr5.evaluate(doc, XPathConstants.NODESET);
            System.out.println("\nAño de libros con cubierta:");
            for (int i = 0; i < yearList.getLength(); i++) {
                System.out.println(yearList.item(i).getNodeValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}