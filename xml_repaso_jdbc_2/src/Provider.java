import java.util.ArrayList;

import org.w3c.dom.*;

public class Provider {
    private int id;
    private String name;
    private String country;
    private ArrayList<Product> products;

    public Provider(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
        products = new ArrayList<>();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Product getProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                products.remove(product);
                break;
            }
        }
    }

    public void updateProduct(int id, Product product) {
        for (Product p : products) {
            if (p.getId() == id) {
                products.set(products.indexOf(p), product);
                break;
            }
        }
    }

    public Element toXML(Document doc) {
        Element proveedor = doc.createElement("proveedor");

        Element id = doc.createElement("id");
        id.setTextContent(String.valueOf(this.id));
        proveedor.appendChild(id);

        Element nombre = doc.createElement("nombre");
        nombre.setTextContent(this.name);
        proveedor.appendChild(nombre);

        Element pais = doc.createElement("pais");
        pais.setTextContent(this.country);
        proveedor.appendChild(pais);

        Element productos = doc.createElement("productos");

        for (Product product : this.products) {
            productos.appendChild(product.toXML(doc));
        }

        proveedor.appendChild(productos);

        return proveedor;
    }
}