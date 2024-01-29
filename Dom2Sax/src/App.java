import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App extends DefaultHandler {
    private Connection connection;
    private Library currentLibrary;
    private Book currentBook;
    private String currentElement;

    public App() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/libraries", "root", "");
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "library":
                currentLibrary = new Library(Integer.parseInt(attributes.getValue("id")));
                break;
            case "book":
                currentBook = new Book(attributes.getValue("id"));
                break;
            default:
                currentElement = qName;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentElement == null) {
            return;
        }

        if (currentLibrary != null) {
            switch (currentElement) {
                case "address":
                    String address = currentLibrary.getAddress() == null ? "" : currentLibrary.getAddress();
                    currentLibrary.setAddress(address + new String(ch, start, length));
                    break;
            }
        }
        
        if (currentBook != null) {
            switch (currentElement) {
                case "author":
                String author = currentBook.getAuthor() == null ? "" : currentBook.getAuthor();
                currentBook.setAuthor(author + new String(ch, start, length));
                break;
            case "title":
                String title = currentBook.getTitle() == null ? "" : currentBook.getTitle();
                currentBook.setTitle(title + new String(ch, start, length));
                break;
            case "genre":
                String genre = currentBook.getGenre() == null ? "" : currentBook.getGenre();
                currentBook.setGenre(genre + new String(ch, start, length));
                break;
            case "price":
                currentBook.setPrice(Double.parseDouble(new String(ch, start, length)));
                break;
            case "publish_date":
                String publishDate = currentBook.getPublish_date() == null ? "" : currentBook.getPublish_date();
                currentBook.setPublish_date(publishDate + new String(ch, start, length));
                break;
            case "description":
                String description = currentBook.getDescription() == null ? "" : currentBook.getDescription();
                currentBook.setDescription(description + new String(ch, start, length));
                break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            switch (qName) {
                case "library":
                    currentLibrary.saveToDatabase(connection);
                    currentLibrary = null;
                    break;
                case "book":
                    currentBook.saveToDatabase(connection, currentLibrary.getId());
                    currentBook = null;
                    break;
                default:
                    currentElement = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void parseXML(File file) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser parser = factory.newSAXParser();

        parser.parse(file, this);
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            App app = new App();

            app.parseXML(new File("libraries.xml"));

            app.close();
        } catch (ParserConfigurationException | SAXException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}