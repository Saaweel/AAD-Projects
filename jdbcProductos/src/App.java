import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class App {
    private Statement s;

    public App(String ip, String port, String db, String user, String password) throws SQLException {
        s = connectToDB(ip, port, db, user, password).createStatement();
    }

    public Connection connectToDB(String ip, String port, String db, String user, String password) throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://" + ip + ":" + port + "/" + db, user, password);
    }

    public void createTable(String name, String [] fileds) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + name + " (";

        for (int i = 0; i < fileds.length; i++) {
            query += fileds[i];
            if (i < fileds.length - 1) {
                query += ", ";
            } else {
                query += ");";
            }
        }

        s.execute(query);
    }

    public void addData(String table, String [] fields, String [] values) throws SQLException {
        String query = "INSERT INTO " + table + " (";

        for (int i = 0; i < fields.length; i++) {
            query += fields[i];
            if (i < fields.length - 1) {
                query += ", ";
            } else {
                query += ") VALUES (";
            }
        }

        for (int i = 0; i < values.length; i++) {
            query += "'" + values[i] + "'";
            if (i < values.length - 1) {
                query += ", ";
            } else {
                query += ");";
            }
        }

        s.execute(query);
    }

    public void deleteData(String table, String whereClause) throws SQLException {
        String query = "DELETE FROM " + table + " WHERE " + whereClause + ";";

        s.execute(query);
    }

    public void updateData(String table, String [] fields, String [] values, String whereClause) throws SQLException {
        String query = "UPDATE " + table + " SET ";

        for (int i = 0; i < fields.length; i++) {
            query += fields[i] + " = '" + values[i] + "'";
            if (i < fields.length - 1) {
                query += ", ";
            } else {
                query += " WHERE " + whereClause + ";";
            }
        }

        s.execute(query);
    }

    public static void main(String[] args) throws Exception {
        App app = new App("localhost", "3306", "dbproductos", "root", "");

        // Crear tablas
        app.createTable("tamano", new String[] {
            "tamano_id INT NOT NULL AUTO_INCREMENT",
            "codigo_tamano VARCHAR(10)",
            "clasificacion VARCHAR(50)",
            "PRIMARY KEY (tamano_id)",
        });

        app.createTable("producto", new String [] {
            "producto_id INT NOT NULL AUTO_INCREMENT",
            "nombre_producto VARCHAR(50)",
            "precio FLOAT",
            "tamano_id INT",
            "PRIMARY KEY (producto_id)",
        });

        app.createTable("color", new String [] {
            "color_id INT NOT NULL AUTO_INCREMENT",
            "codigo_color VARCHAR(10)",
            "nombre_color VARCHAR(50)",
            "PRIMARY KEY (color_id)",
        });

        app.createTable("categoria", new String [] {
            "categoria_id INT NOT NULL AUTO_INCREMENT",
            "categoria_principal VARCHAR(50)",
            "nombre_categoria VARCHAR(50)",
            "PRIMARY KEY (categoria_id)",
        });

        app.createTable("categoria_producto", new String [] {
            "categoria_id INT",
            "producto_id INT",
        });

        app.createTable("producto_color", new String [] {
            "producto_id INT",
            "color_id INT",
        });


        // Insertar datos
        app.addData("tamano", new String [] {
            "codigo_tamano",
            "clasificacion",
        }, new String [] {
            "S",
            "Ropa",
        });

        app.addData("tamano", new String [] {
            "codigo_tamano",
            "clasificacion",
        }, new String [] {
            "M",
            "Ropa",
        });

        app.addData("producto", new String [] {
            "nombre_producto",
            "precio",
            "tamano_id",
        }, new String [] {
            "Camisa",
            "100",
            "1",
        });

        app.addData("producto", new String [] {
            "nombre_producto",
            "precio",
            "tamano_id",
        }, new String [] {
            "Gorro",
            "200",
            "2",
        });

        // Actualizar datos
        app.updateData("producto", new String[] {"precio"}, new String[] {"150"}, "nombre_producto = 'Gorro'");

        // Eliminar datos
        app.deleteData("producto", "nombre_producto LIKE '%a%'");
    }
}