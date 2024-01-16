import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    private Connection connection;
    private ArrayList<Owner> owners;

    public static void main(String[] args) throws Exception {
        App app = new App();

        app.readXml("vehiculos.xml");

        app.saveToDB();
    }

    public App() throws SQLException {
        this.owners = new ArrayList<Owner>();

        this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/xml_java_db", "root", "");

        Statement statement = this.connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS propietarios (id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(255), direccion VARCHAR(255), telefono VARCHAR(255))");
    
        statement.execute("CREATE TABLE IF NOT EXISTS vehiculos (modelo VARCHAR(255), anio INT, color VARCHAR(255), numero_serie INT PRIMARY KEY, propietario_id INT)");
    
        statement.close();
    }

    public void readXml(String path) throws Exception {
        File file = new File(path);
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(file);

        Element root = document.getDocumentElement();

        NodeList owners = root.getElementsByTagName("propietario");

        for (int i = 0; i < owners.getLength(); i++) {
            Element owner = (Element) owners.item(i);

            int id = Integer.parseInt(owner.getElementsByTagName("id").item(0).getTextContent());
            String name = owner.getElementsByTagName("nombre").item(0).getTextContent();
            String address = owner.getElementsByTagName("direccion").item(0).getTextContent();
            String phone = owner.getElementsByTagName("telefono").item(0).getTextContent();

            Owner newOwner = new Owner(id, name, address, phone);

            this.owners.add(newOwner);
        }

        NodeList vehicles = root.getElementsByTagName("vehiculo");

        for (int i = 0; i < vehicles.getLength(); i++) {
            Element vehicle = (Element) vehicles.item(i);

            String model = vehicle.getElementsByTagName("modelo").item(0).getTextContent();
            int year = Integer.parseInt(vehicle.getElementsByTagName("anio").item(0).getTextContent());
            String color = vehicle.getElementsByTagName("color").item(0).getTextContent();
            int serialNumber = Integer.parseInt(vehicle.getElementsByTagName("numero_serie").item(0).getTextContent());
            int ownerId = Integer.parseInt(vehicle.getElementsByTagName("propietario_id").item(0).getTextContent());

            Vehicle newVehicle = new Vehicle(model, year, color, serialNumber);

            this.getOwnerById(ownerId).addVehicle(newVehicle);
        }
    }

    private Owner getOwnerById(int ownerId) {
        for (Owner owner : this.owners) {
            if (owner.getId() == ownerId) {
                return owner;
            }
        }

        return null;
    }

    private void saveToDB() throws SQLException {
        for (Owner owner : this.owners) {
            owner.saveToDB(this.connection);
        }
    }
}
