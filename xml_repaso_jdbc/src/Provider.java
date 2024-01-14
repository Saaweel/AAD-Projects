import java.util.ArrayList;

public class Provider {
    private int id;
    private String name;
    private String country;
    private ArrayList<Product> products;

    public Provider(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
        products = new ArrayList<>();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Product getProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                products.remove(product);
                break;
            }
        }
    }

    public void updateProduct(int id, Product product) {
        for (Product p : products) {
            if (p.getId() == id) {
                products.set(products.indexOf(p), product);
                break;
            }
        }
    }

    public String toXML() {
        String xml = "<proveedor>" +
                "<id>" + id + "</id>" +
                "<nombre>" + name + "</nombre>" +
                "<pais>" + country + "</pais>" +
                "<productos>";

        for (Product product : products) {
            xml += product.toXML();
        }

        xml += "</productos>" +
                "</proveedor>";

        return xml;
    }
}