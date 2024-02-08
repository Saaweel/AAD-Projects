import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class RealEstate {
    private String name;
    private String address;
    private String city;
    private ArrayList<Client> clients;
    private ArrayList<Property> properties;

    public RealEstate() {
        this.clients = new ArrayList<Client>();
        this.properties = new ArrayList<Property>();
    }

    public RealEstate(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.clients = new ArrayList<Client>();
        this.properties = new ArrayList<Property>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Client getClient(int index) {
        return clients.get(index);
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeClient(int index) {
        clients.remove(index);
    }

    public Property getProperty(int index) {
        return properties.get(index);
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(int index) {
        properties.remove(index);
    }

    public void saveToDatabase(Connection db) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("INSERT INTO inmobiliarias (nombre, direccion, ciudad) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE direccion = ?, ciudad = ?");

        stmt.setString(1, this.name);
        stmt.setString(2, this.address);
        stmt.setString(3, this.city);
        stmt.setString(4, this.address);
        stmt.setString(5, this.city);

        stmt.executeUpdate();

        for (Property property : properties) {
            property.saveToDatabase(db, this.name);
        }

        for (Client client : clients) {
            client.saveToDatabase(db);
        }
    }
}
