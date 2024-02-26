import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Person {
    private String firstname;
    private String lastname;
    private String city;
    private String country;
    private String email;
    private ArrayList<Pet> pets;

    public Person(String firstname, String lastname, String city, String country, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
        this.email = email;
        this.pets = new ArrayList<>();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Pet getPet(int i) {
        return this.pets.get(i);
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public void removePet(Pet pet) {
        this.pets.remove(pet);
    }

    public void saveToMongoDB(MongoDatabase mongodb) {
        BasicDBObject dbObject = new BasicDBObject();

        dbObject.append("firstname", this.firstname);
        dbObject.append("lastname", this.lastname);
        dbObject.append("city", this.city);
        dbObject.append("country", this.country);
        dbObject.append("email", this.email);

        BasicDBObject[] petsObject = new BasicDBObject[this.pets.size()];

        for (int i = 0; i < this.pets.size(); i++) {
            petsObject[i] = this.pets.get(i).toBasicDBObject();
        }

        dbObject.append("pets", petsObject);

        MongoCollection<BasicDBObject> collection = mongodb.getCollection("persons", BasicDBObject.class);

        collection.insertOne(dbObject);
    }
}
