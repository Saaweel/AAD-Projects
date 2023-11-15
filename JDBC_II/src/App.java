import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

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

    public ArrayList<String []> getTableInfo(String table) throws SQLException {
        ArrayList<String []> data = new ArrayList<>();

        ResultSet result = s.executeQuery("SHOW COLUMNS FROM " + table);

        while(result.next()) {
            String [] row = new String[result.getMetaData().getColumnCount()];

            for (int i = 0; i < row.length; i++) {
                row[i] = result.getString(i + 1);
            }

            data.add(row);
        }

        return data;
    }

    // Crear un método que obtenga todos los datos de una tabla que se pase por parámetro
    public ArrayList<String []> getAllData(String table) throws SQLException {
        ArrayList<String []> data = new ArrayList<>();

        ResultSet result = s.executeQuery("SELECT * FROM " + table);

        while(result.next()) {
            String [] row = new String[result.getMetaData().getColumnCount()];

            for (int i = 0; i < row.length; i++) {
                row[i] = result.getString(i + 1);
            }

            data.add(row);
        }

        return data;
    }

    public ArrayList<String []> getData(String table, String [] columns, String whereClause) throws SQLException {
        ArrayList<String []> data = new ArrayList<>();

        String query = "SELECT ";

        for (int i = 0; i < columns.length; i++) {
            query += columns[i];
            if (i < columns.length - 1) {
                query += ", ";
            } else {
                query += " FROM " + table + " WHERE " + whereClause + ";";
            }
        }

        ResultSet result = s.executeQuery(query);

        while(result.next()) {
            String [] row = new String[result.getMetaData().getColumnCount()];

            for (int i = 0; i < row.length; i++) {
                row[i] = result.getString(i + 1);
            }

            data.add(row);
        }

        return data;
    }

    public ArrayList<String []> getInnerJoin(String table1, String table2, String column1, String column2) throws SQLException {
        ArrayList<String []> data = new ArrayList<>();

        String query = "SELECT * FROM " + table1 + " INNER JOIN " + table2 + " ON " + table1 + "." + column1 + " = " + table2 + "." + column2 + ";";

        ResultSet result = s.executeQuery(query);

        while(result.next()) {
            String [] row = new String[result.getMetaData().getColumnCount()];

            for (int i = 0; i < row.length; i++) {
                row[i] = result.getString(i + 1);
            }

            data.add(row);
        }

        return data;
    }

    public ArrayList<String []> findPatternInAllDataBases(String ip, String port, String user, String password, String pattern, String ignore) throws SQLException {
        // Vamos a evitar las bases de datos del sistema por que si no se tira 5 años analizando
        ignore += "information_schema performance_schema sys mysql";

        System.out.println();
        System.out.println("Analizando el patrón en todas las bases de datos...");
        System.out.println("Esto puede tardar un poco");
        
        ArrayList<String []> data = new ArrayList<>();

        Statement noDbConn = DriverManager.getConnection("jdbc:mariadb://" + ip + ":" + port, user, password).createStatement();

        if (noDbConn.execute("SHOW DATABASES;")) {
            ResultSet result = noDbConn.getResultSet();

            while(result.next()) {
                String db = result.getString(1);

                if (!ignore.contains(db)) {
                    Statement dbConn = this.connectToDB(ip, port, db, user, password).createStatement();

                    ResultSet tables = dbConn.executeQuery("SHOW TABLES;");

                    while(tables.next()) {
                        String table = tables.getString(1);

                        ResultSet columns = dbConn.executeQuery("SHOW COLUMNS FROM " + table + ";");

                        while(columns.next()) {
                            String column = columns.getString(1);
                            
                            ResultSet columnData = dbConn.executeQuery("SELECT * FROM " + table + " WHERE `" + column + "` LIKE '%" + pattern + "%';");
                            
                            while(columnData.next()) {
                                String [] row = new String[4];

                                row[0] = db;
                                row[1] = table;
                                row[2] = column;
                                row[3] = columnData.getString(column);

                                data.add(row);
                            }
                        }
                    }
                }
            }
        }

        return data;
    }


    public void importFromCSV(String table, String csvPath) throws Exception {
        File csv = new File(csvPath);

        if (csv.exists() && csv.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(csv));

            String line = reader.readLine();
            StringBuilder query = new StringBuilder("INSERT INTO producto (nombre_producto, precio, tamano_id) VALUES");

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 3) {
                    query.append(" ('" + data[0] + "'," + data[1] + "," + data[2] + "),");
                }
            }

            reader.close();

            if (query.charAt(query.length() - 1) == ',') {
                query.deleteCharAt(query.length() - 1);
            } else {
                return;
            }

            s.execute(query.toString());
        }
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

        // Obtener la informacion de las columnas de la tabla
        ArrayList<String []> tableInfo = app.getTableInfo("producto");
        System.out.println();
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ | ------------------ | ------------------ |");
        System.out.printf("| %-18s | %-18s | %-18s | %-18s | %-18s | %-18s |\n", "Nombre", "Tipo", "Null", "Key", "Por defecto", "Extras");
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ | ------------------ | ------------------ |");
        for (String [] row : tableInfo) {
            System.out.printf("| %-18s | %-18s | %-18s | %-18s | %-18s | %-18s |\n", row[0], row[1], row[2], row[3], row[4], row[5]);
        }
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ | ------------------ | ------------------ |");

        // Obtener todos los datos de una tabla
        ArrayList<String []> data = app.getAllData("producto");
        System.out.println();
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ |");
        System.out.printf("| %-18s | %-18s | %-18s | %-18s |\n", "producto_id", "nombre_producto", "precio", "tamano_id");
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ |");
        for (String [] row : data) {
            System.out.printf("| %-18s | %-18s | %-18s | %-18s |\n", row[0], row[1], row[2], row[3]);
        }
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ |");

        // Obtener datos de las columnas de una tabla con un where
        ArrayList<String []> dataWithWhere = app.getData("producto", new String [] {"nombre_producto", "precio"}, "nombre_producto = 'Gorro'");
        System.out.println();
        System.out.println("| ------------------ | ------------------ |");
        System.out.printf("| %-18s | %-18s |\n", "nombre_producto", "precio");
        System.out.println("| ------------------ | ------------------ |");
        for (String [] row : dataWithWhere) {
            System.out.printf("| %-18s | %-18s |\n", row[0], row[1]);
        }
        System.out.println("| ------------------ | ------------------ |");

        // Obtener datos de un inner join
        ArrayList<String []> innerJoin = app.getInnerJoin("producto", "tamano", "tamano_id", "tamano_id");
        System.out.println();
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ |");
        System.out.printf("| %-18s | %-18s | %-18s | %-18s |\n", "producto_id", "nombre_producto", "precio", "clasificacion");
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ |");
        for (String [] row : innerJoin) {
            System.out.printf("| %-18s | %-18s | %-18s | %-18s |\n", row[0], row[1], row[2], row[5]);
        }
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ |");

        // Buscar un dato en todas las bases de datos
        // He puesto un 5 parametro que son las bases de datos a ignorar (separadas por espacios en blanco) ya que por ejemplo yo tengo bases de datos de mi trabajo que son gigantescas y se podia tirar como 5 minutos analizandolas todas
        // Tambien el propio metodo le añade las bases de datos del sistema ya que en esas bases de datos por algun motivo las consultas son mas lentas y se tira como otros 2 minutos
        ArrayList<String []> matchs = app.findPatternInAllDataBases("localhost", "3306", "root", "", "Gorro", "origen2023 wordpress zona");
        System.out.println();
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ |");
        System.out.printf("| %-18s | %-18s | %-18s | %-18s |\n", "Base de datos", "Tabla", "Columna", "Dato");
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ |");
        for (String [] row : matchs) {
            System.out.printf("| %-18s | %-18s | %-18s | %-18s |\n", row[0], row[1], row[2], row[3]);
        }
        System.out.println("| ------------------ | ------------------ | ------------------ | ------------------ |");

        // Leer datos de un CSV y guardarlos en la tabla productos
        app.importFromCSV("productos", "productos.csv");
    }
}