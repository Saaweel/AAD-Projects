import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Property {
    private String id;
    private String type;
    private String address;
    private int rooms;
    private int bathrooms;
    private float price;

    public Property() {
    }
    
    public Property(String id, String type, String address, int rooms, int bathrooms, float price) {
        this.id = id;
        this.type = type;
        this.address = address;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void saveToDatabase(Connection db, String name) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO propiedades (id, inmobiliaria_nombre, tipo, direccion, habitaciones, baños, precio) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE inmobiliaria_nombre = ?, tipo = ?, direccion = ?, habitaciones = ?, baños = ?, precio = ?");

        statement.setString(1, id);
        statement.setString(2, name);
        statement.setString(3, type);
        statement.setString(4, address);
        statement.setInt(5, rooms);
        statement.setInt(6, bathrooms);
        statement.setFloat(7, price);
        statement.setString(8, name);
        statement.setString(9, type);
        statement.setString(10, address);
        statement.setInt(11, rooms);
        statement.setInt(12, bathrooms);
        statement.setFloat(13, price);
        
        statement.executeUpdate();
        
    }
}
