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

    public String toXML() {
        return "<producto>" +
                "<id>" + id + "</id>" +
                "<nombre>" + name + "</nombre>" +
                "<precio>" + price + "</precio>" +
                "<moneda>" + currency + "</moneda>" +
                "</producto>";
    }
}
