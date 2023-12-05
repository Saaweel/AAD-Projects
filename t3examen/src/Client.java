public class Client {
    private int id;

    private String name;

    private String lastname;

    private String street;

    private String email;

    public Client(int id, String name, String lastname, String street, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.street = street;
        this.email = email;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
