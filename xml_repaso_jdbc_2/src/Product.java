import org.w3c.dom.*;

public class Product {
    private int id;
    private String name;
    private double price;
    private String currency;

    public Product(int id, String name, double price, String currency) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Element toXML(Document doc) {
        Element product = doc.createElement("product");

        Element id = doc.createElement("id");
        id.setTextContent(String.valueOf(this.id));
        product.appendChild(id);

        Element name = doc.createElement("name");
        name.setTextContent(this.name);
        product.appendChild(name);

        Element price = doc.createElement("price");
        price.setTextContent(String.valueOf(this.price));
        product.appendChild(price);

        Element currency = doc.createElement("currency");
        currency.setTextContent(this.currency);
        product.appendChild(currency);

        return product;
    }
}
