import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        if (args.length == 1) {
            String tableName = args[0];

            Statement db = DriverManager.getConnection("jdbc:mariadb://localhost:3306/w3schools", "root", "").createStatement();

            ResultSet result = db.executeQuery("SELECT * FROM " + tableName);

            ResultSetMetaData metaData = result.getMetaData();

            System.out.println("Tabla " + tableName);
            System.out.println("Número de columnas: " + metaData.getColumnCount());

            System.out.print("Columnas: (");
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.print(metaData.getColumnName(i));
                if (i < metaData.getColumnCount()) {
                    System.out.print(", ");
                }
            }
            System.out.println(")");

            int rowNumber = 1;
            while (result.next()) {
                System.out.print("Fila " + rowNumber + " (");
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.print(result.getString(i));
                    if (i < metaData.getColumnCount()) {
                        System.out.print(", ");
                    }
                }
                System.out.println(")");
                rowNumber++;
            }

            db.close();
        } else {
            System.out.println("Uso: java App <nombre_tabla>");
        }
    }
}
