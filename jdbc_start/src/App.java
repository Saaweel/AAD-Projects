import java.sql.*;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;

public class App {
	public static void main(String[] args) throws SQLException{
		Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jdbc_start", "root", "");

		Statement s = c.createStatement();

		ResultSet rsUsers = s.executeQuery("SELECT * FROM usuarios");

        ResultSet rsProducts = s.executeQuery("SELECT * FROM productos");
		
        HashMap<User, Product> pedidos = new HashMap<>();

        LinkedList<User> usuarios = new LinkedList<>();

        while(rsUsers.next()) {
            usuarios.push(new User(rsUsers.getInt("id"), rsUsers.getString("nombre"), rsUsers.getString("email"), rsUsers.getString("fecha_registro")));
		}

        System.out.println(usuarios);
	}
} 