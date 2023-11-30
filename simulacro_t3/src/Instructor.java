
public class Instructor {
    private int id;

    private String nss;

    private String name;

    private int phone;

    private String street;

    public Instructor(String nss, String name, int phone, String street) {
        this.nss = nss;
        this.name = name;
        this.phone = phone;
        this.street = street;
    }

    public Instructor(int id, String nss, String name, int phone, String street) {
        this.id = id;
        this.nss = nss;
        this.name = name;
        this.phone = phone;
        this.street = street;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Instructor other = (Instructor) obj;
        
        return this.id == other.id;
    }

}
