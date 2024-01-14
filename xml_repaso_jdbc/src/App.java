import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class App {
    private Connection connection;
    private ArrayList<Provider> providers;

    public static void main(String[] args) throws Exception {
        App app = new App();

        app.connectToDB();

        app.loadDB();

        app.exportToXML("xml_repaso_jdbc.xml");

        app.exportDBToXML("xml_repaso_jdbc_db.xml");

        app.closeConnection();
    }

    public App() {
        providers = new ArrayList<>();
    }

    public void connectToDB() throws Exception {
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/xml_repaso_jdbc", "root", "");
    }

    public void loadDB() throws Exception {
        ResultSet providersDB = connection.createStatement().executeQuery("SELECT * FROM proveedores");

        while (providersDB.next()) {
            int id = providersDB.getInt("id");
            String name = providersDB.getString("nombre");
            String country = providersDB.getString("pais");

            Provider provider = new Provider(id, name, country);

            providers.add(provider);
        }

        ResultSet productsDB = connection.createStatement().executeQuery("SELECT * FROM productos");

        while (productsDB.next()) {
            int id = productsDB.getInt("id");
            String name = productsDB.getString("nombre");
            double price = productsDB.getInt("precio");
            String currency = productsDB.getString("moneda");
            int providerId = productsDB.getInt("proveedor");

            Product product = new Product(id, name, price, currency);

            Provider provider = this.getProvider(providerId);

            if (provider != null) {
                provider.addProduct(product);
            }
        }
    }

    private Provider getProvider(int providerId) {
        for (Provider provider : providers) {
            if (provider.getId() == providerId) {
                return provider;
            }
        }
        return null;
    }

    public void exportToXML(String path) throws IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

        xml += "<proveedores>";

        for (Provider provider : providers) {
            xml += provider.toXML();
        }

        xml += "</proveedores>";

        FileWriter writer = new FileWriter(path);

        writer.write(xml);

        writer.close();
    }

    public void exportDBToXML(String path) throws Exception {
        ResultSet providersDB = connection.createStatement().executeQuery("SELECT * FROM proveedores");

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

        xml += "<proveedores>";

        while (providersDB.next()) {
            int id = providersDB.getInt("id");
            String name = providersDB.getString("nombre");
            String country = providersDB.getString("pais");

            xml += "<proveedor>"
                + "<id>" + id + "</id>"
                + "<nombre>" + name + "</nombre>"
                + "<pais>" + country + "</pais>"
                + "<productos>";

            ResultSet productsDB = connection.createStatement().executeQuery("SELECT * FROM productos WHERE proveedor = " + id);

            while (productsDB.next()) {
                int productId = productsDB.getInt("id");
                String productName = productsDB.getString("nombre");
                double price = productsDB.getInt("precio");
                String currency = productsDB.getString("moneda");

                xml += "<producto>"
                    + "<id>" + productId + "</id>"
                    + "<nombre>" + productName + "</nombre>"
                    + "<precio>" + price + "</precio>"
                    + "<moneda>" + currency + "</moneda>"
                    + "</producto>";
            }

            xml += "</productos>"
                + "</proveedor>";
        }

        xml += "</proveedores>";

        FileWriter writer = new FileWriter(path);

        writer.write(xml);

        writer.close();
    }

    public void closeConnection() throws Exception {
        connection.close();
    }
}
