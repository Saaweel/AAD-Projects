import java.util.Arrays;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class User {
    private String name;
    private int age;
    private boolean isStudent;
    private float height;
    private Address address;
    private Contact contact;
    private String[] hobbies;
    private Skill[] skills;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean isStudent) {
        this.isStudent = isStudent;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public Skill[] getSkills() {
        return skills;
    }

    public void setSkills(Skill[] skills) {
        this.skills = skills;
    }

    public Node toDomNode(Document doc) {
        Element xmlUser = doc.createElement("user");

        Element xmlName = doc.createElement("name");
        xmlName.setTextContent(this.name);
        xmlUser.appendChild(xmlName);

        Element xmlAge = doc.createElement("age");
        xmlAge.setTextContent(Integer.toString(this.age));
        xmlUser.appendChild(xmlAge);

        Element xmlStudent = doc.createElement("student");
        xmlStudent.setTextContent(Boolean.toString(this.isStudent));
        xmlUser.appendChild(xmlStudent);

        Element xmlHeight = doc.createElement("height");
        xmlHeight.setTextContent(Float.toString(this.height));
        xmlUser.appendChild(xmlHeight);

        xmlUser.appendChild(this.address.toDomNode(doc));
        xmlUser.appendChild(this.contact.toDomNode(doc));
        
        Element xmlHobbies = doc.createElement("hobbies");
        for (String hobbie : this.hobbies) {
            Element xmlHobbie = doc.createElement("hobbie");
            xmlHobbie.setTextContent(hobbie);
            xmlHobbies.appendChild(xmlHobbie);
        }
        xmlUser.appendChild(xmlHobbies);

        Element xmlSkills = doc.createElement("skills");
        for (Skill skill : this.skills) {
            xmlSkills.appendChild(skill.toDomNode(doc));
        }
        xmlUser.appendChild(xmlSkills);

        return xmlUser;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + ", isStudent=" + isStudent + ", height=" + height + ", address="
                + address + ", contact=" + contact + ", hobbies=" + Arrays.toString(hobbies) + ", skills="
                + Arrays.toString(skills) + "]";
    }
}
