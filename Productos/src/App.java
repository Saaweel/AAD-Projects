import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    private ArrayList<Product> products;

    public void createProduct(Product product) {
        products.add(product);
    }

    public Product readProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    public void updateProduct(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setStock(product.getStock());
                p.setCategory(product.getCategory());
            }
        }
    }

    public void deleteProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                products.remove(product);
                break;
            }
        }
    }

    public void showProductsByPattern(String pattern) {
        for (Product product : products) {
            if (product.getName().contains(pattern)) {
                System.out.println(product);
            }
        }
    }

    public double getCatAverage(String category) {
        double average = 0.0;
        ArrayList<Product> productsFromCategory = this.getProductsFromCategory(category);

        for (Product product : productsFromCategory) {
            if (product.getCategory().equals(category)) {
                average += product.getPrice();
            }
        }

        return Math.round((average/productsFromCategory.size()) * 100.0) / 100.0;
    }

    public ArrayList<Product> getProductsFromCategory(String category) {
        ArrayList<Product> productsFromCategory = new ArrayList<Product>();

        for (Product product : products) {
            if (product.getCategory().equals(category)) {
                productsFromCategory.add(product);
            }
        }

        return productsFromCategory;
    }

    public void dumpCategoryProducts(String category) {
        File productsCsv = new File("productos_categoría_" + category + ".csv");

        if (!productsCsv.exists()) {
            try {
                productsCsv.createNewFile();
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        if (productsCsv.exists() && productsCsv.canWrite() && productsCsv.isFile() && !productsCsv.isDirectory()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(productsCsv))) {
                for (Product product : this.getProductsFromCategory(category)) {
                    bw.write(product.toCSV() + "\n");
                }
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        } else {
            System.out.println("El fichero no existe o no se puede escribir");
        }
    }

    public void dumpProducts() {
        HashMap<String, ArrayList<Product>> productsByCategory = new HashMap<String, ArrayList<Product>>();

        for (Product product : products) {
            if (productsByCategory.containsKey(product.getCategory())) {
                productsByCategory.get(product.getCategory()).add(product);
            } else {
                ArrayList<Product> productsFromCategory = new ArrayList<Product>();
                productsFromCategory.add(product);
                productsByCategory.put(product.getCategory(), productsFromCategory);
            }
        }

        File productsCsv = new File("todos.csv");

        if (!productsCsv.exists()) {
            try {
                productsCsv.createNewFile();
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        if (productsCsv.exists() && productsCsv.canWrite() && productsCsv.isFile() && !productsCsv.isDirectory()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(productsCsv))) {
                for (String category : productsByCategory.keySet()) {
                    bw.write("Categoria: " + category + "\n");
                    bw.write("--------------------\n");
                    for (Product product : productsByCategory.get(category)) {
                        bw.write(product + "\n");
                    }
                    bw.write("\nNº de productos de la categoria " + category + ": " + productsByCategory.get(category).size() + "\n");
                    bw.write("********************************************************************************\n");
                }
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        } else {
            System.out.println("El fichero no existe o no se puede escribir");
        }
    }

    public App() {
        products = new ArrayList<Product>();
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        App app = new App();

        File productsCsv = new File("productos.csv");

        if (productsCsv.exists() && productsCsv.canRead() && productsCsv.isFile() && !productsCsv.isDirectory()) {
            try (BufferedReader br = new BufferedReader(new FileReader(productsCsv))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] fields = line.split(";");

                    app.createProduct(new Product(Integer.parseInt(fields[0]), fields[1], Double.parseDouble(fields[2]), Integer.parseInt(fields[3]), fields[4]));
                }
            } catch (Exception e) {
                e.fillInStackTrace();
            }

            System.out.print("\nPatrón de búsqueda (exclusivo): ");
            String pattern = sc.nextLine();

            app.showProductsByPattern(pattern);

            System.out.print("\nCategoria: ");
            String category = sc.nextLine();

            double average = app.getCatAverage(category);

            if (average != 0.0) {
                System.out.println("Valor medio de los precios de " + category + ": " + average);
            } else {
                System.out.println("No hay productos de la categoria " + category);
            }

            System.out.print("\nCategoria: ");
            category = sc.nextLine();
            
            ArrayList <Product> productsFromCategory = app.getProductsFromCategory(category);
            
            if (productsFromCategory.size() != 0) {
                System.out.println("Productos de la categoria " + category + ":");
                for (Product product : productsFromCategory) {
                    System.out.println(product);
                }
            } else {
                System.out.println("No hay productos de la categoria " + category);
            }

            System.out.print("\nCategoria: ");
            app.dumpCategoryProducts(sc.nextLine());

            app.dumpProducts();
        } else {
            System.out.println("El fichero no existe o no se puede leer");
        }

        sc.close();
    }
}