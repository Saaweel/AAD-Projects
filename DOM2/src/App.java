import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class App {
    private Connection db;
    private List<Library> libraries;

    public App() {
        this.libraries = new ArrayList<Library>();
    }

    public void readLibrariesFromXMLNode(Node node) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            switch (node.getNodeName()) {
                case "library":
                    Library library = new Library(Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue()));

                    this.readLibraryDataFromXMLNode(node, library);

                    this.libraries.add(library);
                    break;
                default:
                    NodeList children = node.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        this.readLibrariesFromXMLNode(children.item(i));
                    }
                    break;
            }
        } else {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                this.readLibrariesFromXMLNode(children.item(i));
            }
        }
    }

    public void readLibraryDataFromXMLNode(Node node, Library library) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            switch (node.getNodeName()) {
                case "book":
                    Book book = new Book(node.getAttributes().getNamedItem("id").getNodeValue());
                    
                    NodeList children = node.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        this.readBookDataFromXMLNode(children.item(i), book);
                    }

                    library.addBook(book);
                    break;
                case "address":
                    String address = node.getTextContent();

                    library.setAddress(address);
                    break;
                default:
                    NodeList childNodes = node.getChildNodes();
                    for (int i = 0; i < childNodes.getLength(); i++) {
                        this.readLibraryDataFromXMLNode(childNodes.item(i), library);
                    }
                    break;
            }
        } 
    }

    public void readBookDataFromXMLNode(Node node, Book book) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            switch (node.getNodeName()) {
                case "author":
                    String author = node.getTextContent();

                    book.setAuthor(author);
                    break;
                case "title":
                    String title = node.getTextContent();

                    book.setTitle(title);
                    break;
                case "genre":
                    String genre = node.getTextContent();

                    book.setGenre(genre);
                    break;
                case "price":
                    String price = node.getTextContent();

                    book.setPrice(Double.parseDouble(price));
                    break;
                case "publish_date":
                    String publish_date = node.getTextContent();

                    book.setPublish_date(publish_date);
                    break;
                case "description":
                    String description = node.getTextContent();

                    book.setDescription(description);
                    break;
                default:
                    NodeList childNodes = node.getChildNodes();
                    for (int i = 0; i < childNodes.getLength(); i++) {
                        this.readBookDataFromXMLNode(childNodes.item(i), book);
                    }
                    break;
            }
        } 
    }

    public void connectToDB() throws Exception {
        db = DriverManager.getConnection("jdbc:mariadb://localhost:3306/libraries", "root", "");
    }

    public void saveLibrariesToDatabase() throws Exception {
        for (Library library : this.libraries) {
            library.saveToDatabase(db);
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();

        File file = new File("libraries.xml");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            app.readLibrariesFromXMLNode(doc);

            app.connectToDB();

            app.saveLibrariesToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}