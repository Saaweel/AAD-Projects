import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Contact {
    private String email;
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Node toDomNode(Document doc) {
        Element xmlContact = doc.createElement("contact");

        xmlContact.setAttribute("email", this.email);
        xmlContact.setAttribute("phone", this.phone);

        return xmlContact;
    }

    @Override
    public String toString() {
        return "Contact [email=" + email + ", phone=" + phone + "]";
    }
}
