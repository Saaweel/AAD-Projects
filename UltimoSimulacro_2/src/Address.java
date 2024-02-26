import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Address {
    private String street;
    private String city;

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + "]";
    }

    public Node toDomNode(Document doc) {
        Node xmlAddress = doc.createElement("address");

        Node xmlStreet = doc.createElement("street");
        xmlStreet.appendChild(doc.createTextNode(street));
        xmlAddress.appendChild(xmlStreet);

        Node xmlCity = doc.createElement("city");
        xmlCity.appendChild(doc.createTextNode(city));
        xmlAddress.appendChild(xmlCity);

        return xmlAddress;
    }
}