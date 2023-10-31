public class User {
    private int id;
    private String name;
    private String email;
    private String registerDate;
    
    public User(int id, String name, String email, String registerDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registerDate = registerDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "Usuario [Id: " + this.id + "; Nombre: " + this.name + "; Email: " + this.email + "; Fecha de registro " + this.registerDate + "]";
    }
}
