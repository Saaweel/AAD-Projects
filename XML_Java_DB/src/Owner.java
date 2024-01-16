import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Owner {
    private int id;
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> vehicles;

    public Owner(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.vehicles = new ArrayList<Vehicle>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Vehicle getVehicle(int serialNumber) {
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle.getSerialNumber() == serialNumber) {
                return vehicle;
            }
        }

        return null;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void saveToDB(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO propietarios (id, nombre, direccion, telefono) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE nombre = ?, direccion = ?, telefono = ?");

        statement.setInt(1, this.id);
        statement.setString(2, this.name);
        statement.setString(3, this.address);
        statement.setString(4, this.phone);
        statement.setString(5, this.name);
        statement.setString(6, this.address);
        statement.setString(7, this.phone);

        statement.execute();

        statement.close();

        for (Vehicle vehicle : this.vehicles) {
            vehicle.saveToDB(connection, this.id);
        }
    }
}
