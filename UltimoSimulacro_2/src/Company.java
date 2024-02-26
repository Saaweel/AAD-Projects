import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Company {
    private String name;
    private Address address;
    private boolean isGlobal;
    
    public Company(String name, Address address, boolean isGlobal) {
        this.name = name;
        this.address = address;
        this.isGlobal = isGlobal;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    @Override
    public String toString() {
        return "Company [name=" + name + ", address=" + address + ", isGlobal=" + isGlobal + "]";
    }

    public Node toDomNode(Document doc) {
        Element xmlCompany = doc.createElement("company");

        Element xmlName = doc.createElement("name");
        xmlName.appendChild(doc.createTextNode(name));
        xmlCompany.appendChild(xmlName);

        Element xmlAddress = doc.createElement("address");
        xmlAddress.appendChild(address.toDomNode(doc));
        xmlCompany.appendChild(xmlAddress);

        Element xmlIsGlobal = doc.createElement("isGlobal");
        xmlIsGlobal.appendChild(doc.createTextNode(Boolean.toString(isGlobal)));
        xmlCompany.appendChild(xmlIsGlobal);

        return xmlCompany;
    }
}
