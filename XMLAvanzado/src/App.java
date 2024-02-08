import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class App {
    private Connection db;
    private ArrayList<RealEstate> realEstates;

    public App() {
        this.realEstates = new ArrayList<RealEstate>();
    }

    public void readRealEstatesFromXMLNode(Node node) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            switch (node.getNodeName()) {
                case "inmobiliaria":
                    RealEstate library = new RealEstate();

                    this.readRealEstateDataFromXMLNode(node, library);

                    this.realEstates.add(library);
                    break;
                default:
                    NodeList children = node.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        this.readRealEstatesFromXMLNode(children.item(i));
                    }
                    break;
            }
        } else {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                this.readRealEstatesFromXMLNode(children.item(i));
            }
        }
    }

    public void readRealEstateDataFromXMLNode(Node node, RealEstate realEstate) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            switch (node.getNodeName()) {
                case "nombre":
                    String name = node.getTextContent();

                    realEstate.setName(name);
                    break;
                case "direccion":
                    String address = node.getTextContent();

                    realEstate.setAddress(address);
                    break;
                case "ciudad":
                    String city = node.getTextContent();

                    realEstate.setCity(city);
                    break;
                case "cliente":
                    Client client = new Client();
                    
                    NodeList children = node.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        this.readClientDataFromXMLNode(children.item(i), client);
                    }

                    realEstate.addClient(client);
                    break;
                case "propiedad":
                    Property property = new Property();
                    
                    NodeList childrenP = node.getChildNodes();
                    for (int i = 0; i < childrenP.getLength(); i++) {
                        this.readPropertyDataFromXMLNode(childrenP.item(i), property);
                    }

                    realEstate.addProperty(property);
                    break;
                default:
                    NodeList childNodes = node.getChildNodes();
                    for (int i = 0; i < childNodes.getLength(); i++) {
                        this.readRealEstateDataFromXMLNode(childNodes.item(i), realEstate);
                    }
                    break;
            }
        } 
    }

    public void readClientDataFromXMLNode(Node node, Client client) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            switch (node.getNodeName()) {
                case "nombre":
                    String name = node.getTextContent();

                    client.setName(name);
                    break;
                case "tipo":
                    String type = node.getTextContent();

                    client.setType(type);
                    break;
                case "propiedad_id":
                    String propertyId = node.getTextContent();

                    client.setPropertyId(propertyId);
                    break;
                default:
                    NodeList childNodes = node.getChildNodes();
                    for (int i = 0; i < childNodes.getLength(); i++) {
                        this.readClientDataFromXMLNode(childNodes.item(i), client);
                    }
                    break;
            }
        } 
    }

    public void readPropertyDataFromXMLNode(Node node, Property property) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            switch (node.getNodeName()) {
                case "id":
                    String id = node.getTextContent();

                    property.setId(id);
                    break;
                case "tipo":
                    String type = node.getTextContent();

                    property.setType(type);
                    break;
                case "direccion":
                    String address = node.getTextContent();

                    property.setAddress(address);
                    break;
                case "habitaciones":
                    int rooms = Integer.parseInt(node.getTextContent());

                    property.setRooms(rooms);
                    break;
                case "baños":
                    int bathrooms = Integer.parseInt(node.getTextContent());

                    property.setBathrooms(bathrooms);
                    break;
                case "precio":
                    float price = Float.parseFloat(node.getTextContent());

                    property.setPrice(price);
                    break;
                default:
                    NodeList childNodes = node.getChildNodes();
                    for (int i = 0; i < childNodes.getLength(); i++) {
                        this.readPropertyDataFromXMLNode(childNodes.item(i), property);
                    }
                    break;
            }
        } 
    }

    public void connectToDB() throws Exception {
        db = DriverManager.getConnection("jdbc:mariadb://localhost:3306/inmobiliarias", "root", "");
    }

    public void saveRealEstatesToDatabase() throws Exception {
        for (RealEstate realEstate : this.realEstates) {
            realEstate.saveToDatabase(db);
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();

        File file = new File("inmobiliaria.xml");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            app.readRealEstatesFromXMLNode(doc);

            app.connectToDB();

            app.saveRealEstatesToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}