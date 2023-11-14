import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;

public class App{
	private static Statement s;
	private static Scanner readS = new Scanner(System.in);
	private static Scanner readI = new Scanner(System.in);
	private static LinkedList<Estudiante> estudiantes = new LinkedList<>();
	private static LinkedList<Libro> libros = new LinkedList<>();

	
	public static void agregarLibro() throws SQLException {
		System.out.println("Introduce el ISBN del libro");
		String isbn = readS.nextLine();
		System.out.println("Introduce el título del libro");
		String titulo = readS.nextLine();
		System.out.println("Introduce el autor del libro");
		String autor = readS.nextLine();
		System.out.println("Introduce el número de copias del libro");
		int copias = readI.nextInt();
		Libro libro = new Libro(isbn, titulo, autor, copias);
		libros.add(libro);
		s.executeQuery("INSERT INTO Libros VALUES (isbn, titulo, autor, copias) ('" + isbn + "', '" + titulo + "', '" + autor + "', " + copias + ")");
	}

	public static void buscarLibro() throws SQLException {
		System.out.println("Introduce el título o el autor del libro");
		String busqueda = readS.nextLine();
		s.executeQuery("SELECT * FROM Libros WHERE titulo LIKE '%" + busqueda + "%' OR autor LIKE '%" + busqueda + "%'");
		ResultSet rs = s.getResultSet();
		while(rs.next()) {
			System.out.println("ISBN: " + rs.getString("isbn"));
			System.out.println("Título: " + rs.getString("titulo"));
			System.out.println("Autor: " + rs.getString("autor"));
			System.out.println("Copias: " + rs.getInt("copias"));
		}
	}

	public static void prestamo() throws SQLException {
		System.out.println("Introduce el ISBN del libro");
		String isbn = readS.nextLine();
		System.out.println("Introduce el ID del estudiante");
		int id = readI.nextInt();
		s.executeQuery("SELECT isbn FROM Libros WHERE isbn = '" + isbn + "' AND copias > 0");
		ResultSet rs = s.getResultSet();
		while(rs.next()) {
			s.executeQuery("INSERT INTO Prestamos (libro, estudiante) VALUES ('" + isbn + "', " + id + ")");
			s.executeQuery("UPDATE Libros SET copias = copias - 1 WHERE isbn = '" + isbn + "'");
			return;
		}
		System.out.println("No se ha encontrado el libro o no hay copias disponibles");
	}

	public static void devolucion() throws SQLException {
		System.out.println("Introduce el ID del estudiante");
		int id = readI.nextInt();
		System.out.println("Introduce el ISBN del libro");
		String isbn = readS.nextLine();
		s.executeQuery("SELECT isbn FROM Prestamos WHERE estudiante = " + id + " AND libro = '" + isbn + "'");
		ResultSet rs = s.getResultSet();
		while(rs.next()) {
			s.executeQuery("UPDATE Prestamos SET fechafin = CURDATE() WHERE estudiante = " + id + " AND libro = '" + isbn + "'");
			s.executeQuery("UPDATE Libros SET copias = copias + 1 WHERE isbn = '" + isbn + "'");
			return;
		}
		System.out.println("No se ha encontrado el préstamo");
	}

	public static void mostrarInforme() throws SQLException {
		s.executeQuery("SELECT * FROM Prestamos");
		ResultSet rs = s.getResultSet();
		while(rs.next()) {
			System.out.println("ID: " + rs.getInt("id"));
			System.out.println("Libro: " + rs.getString("libro"));
			System.out.println("Estudiante: " + rs.getInt("estudiante"));
			System.out.println("Fecha inicio: " + rs.getDate("fechainicio"));
			System.out.println("Fecha fin: " + rs.getDate("fechafin"));
		}
	}

	public static void borrarLibro() throws SQLException {
		System.out.println("Introduce el ISBN del libro");
		String isbn = readS.nextLine();
		s.executeQuery("DELETE FROM Libros WHERE isbn = '" + isbn + "'");
	}

	public static void main(String[] args) throws SQLException{		
		estudiantes.add(new Estudiante(1, "Carlos", 29));
		estudiantes.add(new Estudiante(2, "Marina", 33));
		estudiantes.add(new Estudiante(3, "Daniel", 29));
		estudiantes.add(new Estudiante(4, "Verónica", 33));
		estudiantes.add(new Estudiante(5, "Justo", 26));

		// Apartado 1
		Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/instituto", "root", "");
		s = c.createStatement();

		// Apartado 2
		s.executeQuery("CREATE TABLE IF NOT EXISTS Libros (isbn VARCHAR(50), titulo VARCHAR(255), autor VARCHAR(255), copias INT, PRIMARY KEY (isbn))");
		s.executeQuery("CREATE TABLE IF NOT EXISTS Prestamos (id INT AUTO_INCREMENT, libro VARCHAR(50), estudiante INT, fechainicio DATE DEFAULT CURDATE(), fechafin DATE NULL, PRIMARY KEY (id), FOREIGN KEY (libro) REFERENCES libros(isbn)  ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY (estudiante) REFERENCES estudiantes(id) ON UPDATE CASCADE ON DELETE CASCADE)");
	
		// Apartado 3
		agregarLibro();

		// Apartado 4
		buscarLibro();

		// Apartado 5
		prestamo();

		// Apartado 6
		devolucion();

		// Apartado 7
		// 7. Mostrar un informe de libros prestados en este momento y quién los tiene. (Valorar un HashMap para préstamos en Java)
		mostrarInforme();

		// Apartado 8
		// 8. Implementar la opción de eliminar libros de la base de datos si es necesario
		borrarLibro();

		c.close();
	}
}