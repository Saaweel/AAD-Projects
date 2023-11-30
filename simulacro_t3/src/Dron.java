
public class Dron {
    private int id;

    private String model;

    private String brand;

    private String size;

    public Dron(String model, String brand, String size) {
        this.model = model;
        this.brand = brand;
        this.size = size;
    }

    public Dron(int id, String model, String brand, String size) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Dron other = (Dron) obj;
        
        return this.id == other.id;
    }
}
