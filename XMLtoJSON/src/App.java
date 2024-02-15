import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class App {

    public static void main(String[] args) {
        String xmlFilePath = "libraries.xml";
        String jsonFilePath = xmlFilePath.replace(".xml", ".json");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(xmlFilePath));

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFilePath))) {
                writer.write("{\n");

                NodeList libraryList = doc.getElementsByTagName("library");

                for (int i = 0; i < libraryList.getLength(); i++) {
                    Element library = (Element) libraryList.item(i);
                    String libraryId = library.getAttribute("id");

                    writer.write("\t\"" + libraryId + "\": {\n");
                    writer.write("\t\t\"address\": \"" + getTextContent(library, "address") + "\",\n");

                    writer.write("\t\t\"books\": [\n");
                    NodeList bookList = library.getElementsByTagName("book");
                    for (int j = 0; j < bookList.getLength(); j++) {
                        Element book = (Element) bookList.item(j);
                        writer.write("\t\t\t{\n");
                        writer.write("\t\t\t\t\"id\": \"" + book.getAttribute("id") + "\",\n");
                        writer.write("\t\t\t\t\"author\": \"" + getTextContent(book, "author") + "\",\n");
                        writer.write("\t\t\t\t\"title\": \"" + getTextContent(book, "title") + "\",\n");
                        writer.write("\t\t\t\t\"genre\": \"" + getTextContent(book, "genre") + "\",\n");
                        writer.write("\t\t\t\t\"price\": " + getTextContent(book, "price") + ",\n");
                        writer.write("\t\t\t\t\"publish_date\": \"" + getTextContent(book, "publish_date") + "\",\n");
                        writer.write("\t\t\t\t\"description\": \"" + getTextContent(book, "description") + "\"\n");
                        writer.write("\t\t\t}");
                        if (j < bookList.getLength() - 1) {
                            writer.write(",");
                        }
                        writer.write("\n");
                    }
                    writer.write("\t\t]\n");

                    writer.write("\t}");
                    if (i < libraryList.getLength() - 1) {
                        writer.write(",");
                    }
                    writer.write("\n");
                }

                writer.write("}");
            }

            System.out.println("JSON generado con éxito en: " + jsonFilePath);
        } catch (Exception e) {
            System.out.println("Error al procesar el XML o generar el JSON: " + e.getMessage());
        }
    }

    private static String getTextContent(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            String text = nodeList.item(0).getTextContent();

            text = text.replace("\n", "");

            text = text.replaceAll("\\s+", " ");

            return text;
        }
        return "";
    }
}