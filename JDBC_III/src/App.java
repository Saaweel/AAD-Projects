import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws Exception {
        int selectedRow = 1;
        
        try {
            selectedRow = Integer.parseInt(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Statement sql = DriverManager.getConnection("jdbc:mariadb://localhost:3306/w3schools", "root", "").createStatement()) {
            // 1
            sql.executeQuery("SELECT ProductName, Price FROM products WHERE ProductName LIKE 'T%' AND Price < 10");
            ResultSet rs = sql.getResultSet();

            System.out.println("1. Productos que empiezan por T y cuestan menos de 10€");
            rs.last();
            while (rs.previous()) {
                System.out.println(rs.getString("ProductName") + " " + rs.getString("Price"));
            }
            System.out.println();
            rs.close();
            
            // 2
            sql.executeQuery("SELECT SupplierName FROM suppliers WHERE Country = 'USA' OR Country = 'Italy'");
            rs = sql.getResultSet();

            System.out.println("2. Proveedores de USA o Italia");
            rs.last();
            while (rs.previous()) {
                System.out.println(rs.getString("SupplierName"));
            }
            System.out.println();
            rs.close();
            
            // 3
            sql.executeQuery("SELECT * FROM customers WHERE Country LIKE '%n' AND CustomerName LIKE '%a%'");
            rs = sql.getResultSet();

            System.out.println("3. Clientes cuyo país termina en n y su nombre contiene una a");
            rs.last();
            while (rs.previous()) {
                System.out.println(rs.getString("CustomerName") + " " + rs.getString("Country"));
            }
            System.out.println();
            rs.close();
            
            // 4
            sql.executeQuery("SELECT CategoryID, AVG(Price) AS MEDIA FROM products GROUP BY CategoryID");
            rs = sql.getResultSet();

            System.out.println("4. Media de los precios de los productos de cada categoría");
            rs.first();
            System.out.println(rs.getString("CategoryID") + " " + rs.getString("MEDIA"));
            rs.last();
            System.out.println(rs.getString("CategoryID") + " " + rs.getString("MEDIA"));
            rs.absolute(rs.getRow() / 2);
            System.out.println(rs.getString("CategoryID") + " " + rs.getString("MEDIA"));
            System.out.println();
            rs.close();
            
            // 5
            sql.executeQuery("SELECT DISTINCT CategoryID FROM products");
            rs = sql.getResultSet();

            System.out.println("5. Ids de las categorías para las que hay productos asociados");
            rs.first();
            System.out.println(rs.getString("CategoryID"));
            rs.last();
            System.out.println(rs.getString("CategoryID"));
            rs.absolute(rs.getRow() / 2);
            System.out.println(rs.getString("CategoryID"));
            System.out.println();
            rs.close();
            
            // 6
            sql.executeQuery("SELECT * FROM orders WHERE OrderDate BETWEEN '1996-07-01' AND '1996-07-31'");
            rs = sql.getResultSet();

            System.out.println("6. Pedidos realizados en Julio de 1996");
            rs.first();
            System.out.println(rs.getString("OrderID") + " " + rs.getString("OrderDate"));
            rs.last();
            System.out.println(rs.getString("OrderID") + " " + rs.getString("OrderDate"));
            rs.absolute(rs.getRow() / 2);
            System.out.println(rs.getString("OrderID") + " " + rs.getString("OrderDate"));
            System.out.println();
            rs.close();
            
            // 7
            sql.executeQuery("SELECT AVG(Price) AS MEDIA, Country FROM products INNER JOIN suppliers ON products.SupplierID = suppliers.SupplierID GROUP BY Country");
            rs = sql.getResultSet();

            System.out.println("7. Media de los precios de los productos de cada país");
            rs.last();
            rs.absolute(rs.getRow() / 2 - 1);
            for (int i = 0; i < 3; i++) {
                System.out.println(rs.getString("Country") + " " + rs.getString("MEDIA"));
                if (!rs.next()) {
                    break;
                }
            }
            rs.absolute(selectedRow);
            System.out.println(rs.getString("Country") + " " + rs.getString("MEDIA"));
            System.out.println();
            rs.close();
            
            // 8
            sql.executeQuery("SELECT ProductID, SUM(Quantity) AS TOTAL FROM order_details INNER JOIN orders ON order_details.OrderID = orders.OrderID WHERE OrderDate BETWEEN '1996-01-01' AND '1996-12-31' GROUP BY ProductID ORDER BY TOTAL DESC LIMIT 10");
            rs = sql.getResultSet();

            System.out.println("8. Los 10 productos más vendidos del año 1996");
            rs.last();
            rs.absolute(rs.getRow() / 2 - 1);
            for (int i = 0; i < 3; i++) {
                System.out.println(rs.getString("ProductID") + " " + rs.getString("TOTAL"));
                if (!rs.next()) {
                    break;
                }
            }
            rs.absolute(selectedRow);
            System.out.println(rs.getString("ProductID") + " " + rs.getString("TOTAL"));
            System.out.println();
            rs.close();

            // 9
            sql.executeQuery("SELECT * FROM orders WHERE OrderID IN (SELECT OrderID FROM order_details INNER JOIN products ON order_details.ProductID = products.ProductID WHERE order_details.Quantity * products.Price > 300)");
            rs = sql.getResultSet();

            System.out.println("9. Pedidos cuyo precio sea mayor a 300€");
            rs.last();
            rs.absolute(rs.getRow() / 2 - 1);
            for (int i = 0; i < 3; i++) {
                System.out.println(rs.getString("OrderID") + " " + rs.getString("OrderDate"));
                if (!rs.next()) {
                    break;
                }
            }
            rs.absolute(selectedRow);
            System.out.println(rs.getString("OrderID") + " " + rs.getString("OrderDate"));
            rs.close();

            // Indicar cómo se podría hacer para obtener el número de filas sin recorrer todo el ResultSet usando los métodos de la diapositiva 19 del Tema 3
            // Para obtener el numero total lo que he hecho es mover el cursor hasta la última fila y hacer .getRow(), ya que las filas empiezan a contar desde 1 y no desde 0, en este caso simplemente habria que sumarle 1
            // Para obtener la fila del medio he dividido entre 2 el numero total de filas
            // Para obtener las 3 filas del medio he dividido entre 2 el numero total de filas (obtener la del medio) y restado 1
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
