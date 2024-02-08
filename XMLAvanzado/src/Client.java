import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Client {
    private String name;
    private String type;
    private String propertyId;

    public Client() {
    }

    public Client(String name, String type, String propertyId) {
        this.name = name;
        this.type = type;
        this.propertyId = propertyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public void saveToDatabase(Connection db) throws SQLException {
        PreparedStatement statement = db.prepareStatement("INSERT INTO clientes (nombre, tipo, propiedad_id) VALUES (?, ?, ?)");

        statement.setString(1, name);
        statement.setString(2, type);
        statement.setString(3, propertyId);

        statement.executeUpdate();
    }
}
