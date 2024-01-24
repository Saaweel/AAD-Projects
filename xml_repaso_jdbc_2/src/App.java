import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

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

    public void exportToXML(String path) throws IOException, ParserConfigurationException, TransformerException {
        // String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

        // xml += "<proveedores>";

        // for (Provider provider : providers) {
        //     xml += provider.toXML();
        // }

        // xml += "</proveedores>";

        // FileWriter writer = new FileWriter(path);

        // writer.write(xml);

        // writer.close();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element proveedores = doc.createElement("proveedores");
        doc.appendChild(proveedores);

        for (Provider provider : providers) {
            proveedores.appendChild(provider.toXML(doc));
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));

        transformer.transform(source, result);
    }

    public void exportDBToXML(String path) throws Exception {
        // ResultSet providersDB = connection.createStatement().executeQuery("SELECT * FROM proveedores");

        // String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

        // xml += "<proveedores>";

        // while (providersDB.next()) {
        //     int id = providersDB.getInt("id");
        //     String name = providersDB.getString("nombre");
        //     String country = providersDB.getString("pais");

        //     xml += "<proveedor>"
        //         + "<id>" + id + "</id>"
        //         + "<nombre>" + name + "</nombre>"
        //         + "<pais>" + country + "</pais>"
        //         + "<productos>";

        //     ResultSet productsDB = connection.createStatement().executeQuery("SELECT * FROM productos WHERE proveedor = " + id);

        //     while (productsDB.next()) {
        //         int productId = productsDB.getInt("id");
        //         String productName = productsDB.getString("nombre");
        //         double price = productsDB.getInt("precio");
        //         String currency = productsDB.getString("moneda");

        //         xml += "<producto>"
        //             + "<id>" + productId + "</id>"
        //             + "<nombre>" + productName + "</nombre>"
        //             + "<precio>" + price + "</precio>"
        //             + "<moneda>" + currency + "</moneda>"
        //             + "</producto>";
        //     }

        //     xml += "</productos>"
        //         + "</proveedor>";
        // }

        // xml += "</proveedores>";

        // FileWriter writer = new FileWriter(path);

        // writer.write(xml);

        // writer.close();

        ResultSet providersDB = connection.createStatement().executeQuery("SELECT * FROM proveedores");

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        
        Element proveedores = doc.createElement("proveedores");
        doc.appendChild(proveedores);

        while (providersDB.next()) {
            int id = providersDB.getInt("id");
            String name = providersDB.getString("nombre");
            String country = providersDB.getString("pais");

            Element proveedor = doc.createElement("proveedor");
            proveedores.appendChild(proveedor);

            Element idElement = doc.createElement("id");
            idElement.setTextContent(String.valueOf(id));
            proveedor.appendChild(idElement);

            Element nombre = doc.createElement("nombre");
            nombre.setTextContent(name);
            proveedor.appendChild(nombre);

            Element pais = doc.createElement("pais");
            pais.setTextContent(country);
            proveedor.appendChild(pais);

            Element productos = doc.createElement("productos");
            proveedor.appendChild(productos);

            ResultSet productsDB = connection.createStatement().executeQuery("SELECT * FROM productos WHERE proveedor = " + id);

            while (productsDB.next()) {
                int productId = productsDB.getInt("id");
                String productName = productsDB.getString("nombre");
                double price = productsDB.getInt("precio");
                String currency = productsDB.getString("moneda");

                Element producto = doc.createElement("producto");
                productos.appendChild(producto);

                Element idProducto = doc.createElement("id");
                idProducto.setTextContent(String.valueOf(productId));
                producto.appendChild(idProducto);

                Element nombreProducto = doc.createElement("nombre");
                nombreProducto.setTextContent(productName);
                producto.appendChild(nombreProducto);

                Element precio = doc.createElement("precio");
                precio.setTextContent(String.valueOf(price));
                producto.appendChild(precio);

                Element moneda = doc.createElement("moneda");
                moneda.setTextContent(currency);
                producto.appendChild(moneda);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));

        transformer.transform(source, result);
    }

    public void closeConnection() throws Exception {
        connection.close();
    }
}
