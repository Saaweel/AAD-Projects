import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Vehicle {
    private String model;
    private int year;
    private String color;
    private int serialNumber;

    public Vehicle(String model, int year, String color, int serialNumber) {
        this.model = model;
        this.year = year;
        this.color = color;
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void saveToDB(Connection connection, int ownerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO vehiculos (modelo, anio, color, numero_serie, propietario_id) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE modelo = ?, anio = ?, color = ?, propietario_id = ?");
    
        statement.setString(1, this.model);
        statement.setInt(2, this.year);
        statement.setString(3, this.color);
        statement.setInt(4, this.serialNumber);
        statement.setInt(5, ownerId);
        statement.setString(6, this.model);
        statement.setInt(7, this.year);
        statement.setString(8, this.color);
        statement.setInt(9, ownerId);

        statement.execute();

        statement.close();
    }
}
