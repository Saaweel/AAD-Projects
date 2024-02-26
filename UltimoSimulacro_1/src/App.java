import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class App extends DefaultHandler {
    private ArrayList<Person> persons;
    private String currentElement;
    private Person currentPerson;
    private Pet currentPet;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "person":
                currentPerson = new Person(attributes.getValue("firstname"), attributes.getValue("lastname"), attributes.getValue("city"), attributes.getValue("country"), attributes.getValue("email"));
                break;
            case "pet":
                currentPet = new Pet();
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
        
        if (currentPet != null) {
            switch (currentElement) {
                case "name":
                    currentPet.setName(new String(ch, start, length));
                    break;
                case "age":
                    currentPet.setAge(new String(ch, start, length));
                    break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "person":
                this.persons.add(currentPerson);
                currentPerson = null;
                break;
            case "pet":
                currentPerson.addPet(currentPet);
                currentPet = null;
                break;
            default:
                currentElement = null;
        }
    }

    public void saveToMongoDB() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("People");

        for (Person p : this.persons) {
            p.saveToMongoDB(database);
        }
    }

    public App() throws SAXException, IOException, ParserConfigurationException {
        this.persons = new ArrayList<>();

        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser parser = factory.newSAXParser();

        parser.parse(new File("people.xml"), this);

        this.saveToMongoDB();
    }

    public static void main(String[] args) throws Exception {
        new App();
    }
}
