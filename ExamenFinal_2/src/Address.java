import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Address {
    private String street;
    private String city;
    private String country;

    public Address(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Node toDomNode(Document doc) {
        Element xmlAddress = doc.createElement("skill");

        Element xmlStreet = doc.createElement("street");
        xmlStreet.setTextContent(this.street);
        xmlAddress.appendChild(xmlStreet);

        Element xmlCity = doc.createElement("city");
        xmlCity.setTextContent(this.city);
        xmlAddress.appendChild(xmlCity);

        Element xmlCountry = doc.createElement("country");
        xmlCountry.setTextContent(this.country);
        xmlAddress.appendChild(xmlCountry);

        return xmlAddress;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + ", country=" + country + "]";
    }
}