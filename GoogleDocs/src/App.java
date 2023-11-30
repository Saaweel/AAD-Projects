import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    private Statement statement;

    private Scanner readN;

    private Scanner readS;

    private LinkedList<Estudiante> estudiantes;

    public static void main(String[] args) throws Exception {
        new App();
    }

    public App() throws SQLException {
        estudiantes = new LinkedList<>();

        readN = new Scanner(System.in);

        readS = new Scanner(System.in);

        estudiantes.add(new Estudiante(1, "Carlos", 29));
        estudiantes.add(new Estudiante(2, "Marina", 33));
        estudiantes.add(new Estudiante(3, "Daniel", 29));
        estudiantes.add(new Estudiante(4, "Verónica", 33));
        estudiantes.add(new Estudiante(5, "Justo", 26));

        exerciseOne();

        exerciseTwo();

        exerciseThree();
    }

    private void exerciseOne() throws SQLException {
        // Crea un programa en Java que usando JDBC sea capaz de realizar las siguientes operaciones:
        // 1. Conectar correctamente a una base de datos MySQL/MariaDB denominada "Instituto" (creada previamente en el SGBD).
        statement = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Instituto", "root", "").createStatement();

        /* Para que las pruebas siempre sean iguales */
        statement.execute("DROP TABLE IF EXISTS Estudiantes");
        statement.execute("DROP TABLE IF EXISTS Libros");
        statement.execute("DROP TABLE IF EXISTS Prestamos");
        statement.execute("DROP TABLE IF EXISTS Profesores");
        /* Para que las pruebas siempre sean iguales */

        // 2. Crear una tabla llamada "Estudiantes" con las siguientes columnas: id, nombre, edad.
        statement.execute("CREATE TABLE IF NOT EXISTS Estudiantes (id INT PRIMARY KEY, nombre VARCHAR(255), edad INT)");

        // 3. Pasar los alumnos de la lista a la BBDD.
        for (Estudiante estudiante : estudiantes) {
            statement.execute("INSERT INTO Estudiantes VALUES (" + estudiante.getId() + ", '" + estudiante.getNombre() + "', " + estudiante.getEdad() + ")");
        }

        // 4. Consultar y mostrar en la consola la información de todos los estudiantes de la tabla.
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Estudiantes");
        
        System.out.println("----------------------");
        System.out.println("Listado de estudiantes");
        System.out.println("----------------------");
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id") + ", Nombre: " + resultSet.getString("nombre") + ", Edad: " + resultSet.getInt("edad"));
        }
        System.out.println();

        // 5. Pedir un id por consola y actualizar la edad del estudiante.
        System.out.println("--------------------------------");
        System.out.println("Actualizar edad de un estudiante");
        System.out.println("--------------------------------");
        System.out.print("Introduce el ID del estudiante: ");
        int id = readN.nextInt();

        System.out.print("Introduce la nueva edad del estudiante: ");
        int edad = readN.nextInt();

        statement.execute("UPDATE Estudiantes SET edad = " + edad + " WHERE id = " + id);
        System.out.println();

        // 6. Pedir un id y eliminar un estudiante de la tabla.
        System.out.println("----------------------");
        System.out.println("Eliminar un estudiante");
        System.out.println("----------------------");
        System.out.print("Introduce el ID del estudiante: ");
        id = readN.nextInt();

        statement.execute("DELETE FROM Estudiantes WHERE id = " + id);

        // 7. Crear un HashMap que posea como clave la edad y como valor los nombres de los estudiantes que poseen esa edad (ojo a este apartado).
        HashMap<Integer, ArrayList<String>> studentsByAge = new HashMap<>();

        resultSet = statement.executeQuery("SELECT * FROM Estudiantes");

        while (resultSet.next()) {
            int age = resultSet.getInt("edad");
            String name = resultSet.getString("nombre");

            if (studentsByAge.containsKey(age)) {
                studentsByAge.get(age).add(name);
            } else {
                ArrayList<String> names = new ArrayList<>();
                names.add(name);
                studentsByAge.put(age, names);
            }
        }

        statement.close();
    }

    private void exerciseTwo() throws SQLException {
        // Usando la misma BBDD del ejercicio anterior, vamos a representar la biblioteca conforme a los siguientes requisitos:
        // 1. Conectar correctamente el programa a la BBDD.
        statement = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Instituto", "root", "").createStatement();
        
        // 2. Crear dos tablas: "Libros" y "Prestamos". La tabla "Libros" debe tener columnas para el el isbn, el título del libro, el autor y el número de copias disponibles. La tabla "Prestamos" debe vincular un estudiante con un libro. los atributos, entre otros, serán "fechainicio" y "fechafin". En caso de que "fechafin" tenga valor nulo, el préstamo estará vigente.
        statement.execute("CREATE TABLE IF NOT EXISTS Libros (isbn INT PRIMARY KEY, titulo VARCHAR(255), autor VARCHAR(255), copias INT)");
        statement.execute("CREATE TABLE IF NOT EXISTS Prestamos (estudiante INT, libro INT, fechainicio DATE, fechafin DATE)");

        // 3. Crear una función para agregar nuevos libros a la tabla "Libros".
        createBook();

        // 4. Permitir a los usuarios buscar libros por título o autor y mostrar los resultados.
        findBooks();
        
        // 5. Implementar un sistema de préstamos que permita a los usuarios tomar prestados libros, reduciendo el número de copias disponibles y registrando la información en la tabla "Prestamos".
        takeBook();
        
        // 6. Implementar la funcionalidad para que los usuarios devuelvan los libros prestados, aumentando el número de copias disponibles y registrando la fecha de devolución en la tabla "Prestamos".
        leaveBook();
        
        // 7. Mostrar un informe de libros prestados en este momento y quién los tiene. (Valorar un HashMap para préstamos en Java)
        showReport();

        // 8. Implementar la opción de eliminar libros de la base de datos si es necesario.
        System.out.println("-----------------");
        System.out.println("Eliminar un libro");
        System.out.println("-----------------");
        System.out.print("Introduce el ISBN del libro: ");
        int isbn = readN.nextInt();
        statement.execute("DELETE FROM Libros WHERE isbn = " + isbn);
        System.out.println();

        statement.close();
    }

    private void createBook() throws SQLException {
        System.out.println("--------------------");
        System.out.println("Crear un nuevo libro");
        System.out.println("--------------------");
        System.out.print("Introduce el ISBN del libro: ");
        int isbn = readN.nextInt();

        System.out.print("Introduce el título del libro: ");
        String title = readS.nextLine();

        System.out.print("Introduce el autor del libro: ");
        String author = readS.nextLine();

        System.out.print("Introduce el número de copias disponibles del libro: ");
        int copies = readN.nextInt();

        statement.execute("INSERT INTO Libros VALUES (" + isbn + ", '" + title + "', '" + author + "', " + copies + ")");

        System.out.println();
    }

    private void findBooks() throws SQLException {
        System.out.println("---------------");
        System.out.println("Buscar un libro");
        System.out.println("---------------");
        System.out.print("Introduce el título o autor del libro: ");
        String titleOrAuthor = readS.nextLine();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM Libros WHERE titulo LIKE '%" + titleOrAuthor + "%' OR autor LIKE '%" + titleOrAuthor + "%'");

        System.out.println("----------");
        System.out.println("Resultados");
        System.out.println("----------");
        while (resultSet.next()) {
            System.out.println("ISBN: " + resultSet.getInt("isbn") + ", Título: " + resultSet.getString("titulo") + ", Autor: " + resultSet.getString("autor") + ", Copias: " + resultSet.getInt("copias"));
        }
        System.out.println();
    }

    private void takeBook() throws SQLException {
        System.out.println("----------------");
        System.out.println("Prestar un libro");
        System.out.println("----------------");
        System.out.print("Introduce el ID del estudiante: ");
        int id = readN.nextInt();

        System.out.print("Introduce el ISBN del libro: ");
        int isbn = readN.nextInt();

        statement.execute("UPDATE Libros SET copias = copias - 1 WHERE isbn = " + isbn);

        statement.execute("INSERT INTO Prestamos (estudiante, libro, fechainicio) VALUES (" + id + ", " + isbn + ", NOW())");
        
        System.out.println();
    }

    private void leaveBook() throws SQLException {
        System.out.println("-----------------");
        System.out.println("Devolver un libro");
        System.out.println("-----------------");
        System.out.print("Introduce el ID del estudiante: ");
        int id = readN.nextInt();

        System.out.print("Introduce el ISBN del libro: ");
        int isbn = readN.nextInt();

        statement.execute("UPDATE Libros SET copias = copias + 1 WHERE isbn = " + isbn);

        statement.execute("UPDATE Prestamos SET fechafin = NOW() WHERE estudiante = " + id + " AND libro = " + isbn);

        System.out.println();
    }

    private void showReport() throws SQLException {
        HashMap<Integer, ArrayList<String>> booksByStudent = new HashMap<>();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM Prestamos");

        while (resultSet.next()) {
            int student = resultSet.getInt("estudiante");
            int book = resultSet.getInt("libro");

            if (booksByStudent.containsKey(student)) {
                booksByStudent.get(student).add(String.valueOf(book));
            } else {
                ArrayList<String> books = new ArrayList<>();
                books.add(String.valueOf(book));
                booksByStudent.put(student, books);
            }
        }

        resultSet = statement.executeQuery("SELECT * FROM Estudiantes");

        System.out.println("---------------------");
        System.out.println("Libros prestados ahora");
        System.out.println("---------------------");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("nombre");
            int age = resultSet.getInt("edad");

            System.out.println("ID: " + id + ", Nombre: " + name + ", Edad: " + age + ", Libros: " + booksByStudent.get(id));
        }

        System.out.println();
    }

    private void exerciseThree() throws SQLException {
        statement = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Instituto", "root", "").createStatement();

        // Usando la misma BBDD del ejercicio anterior (instituto), realizar las siguientes operaciones usando JDBC:
        // 1. Realizar las siguientes estadísticas:
        // Alumno que más libros ha tomado prestado.
        ResultSet resultSet = statement.executeQuery("SELECT estudiante, COUNT(*) AS prestamos FROM Prestamos GROUP BY estudiante ORDER BY prestamos DESC LIMIT 1");

        System.out.println("-------------------------------");
        System.out.println("Alumno que más libros ha tomado");
        System.out.println("-------------------------------");
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("estudiante") + ", Prestamos: " + resultSet.getInt("prestamos"));
        }
        System.out.println();

        // Obtener el libro más prestado.
        resultSet = statement.executeQuery("SELECT libro, COUNT(*) AS prestamos FROM Prestamos GROUP BY libro ORDER BY prestamos DESC LIMIT 1");

        System.out.println("------------------------");
        System.out.println("Libro más prestado ahora");
        System.out.println("------------------------");
        while (resultSet.next()) {
            System.out.println("ISBN: " + resultSet.getInt("libro") + ", Prestamos: " + resultSet.getInt("prestamos"));
        }
        System.out.println();

        // Obtener los libros que aún no se han prestado.
        resultSet = statement.executeQuery("SELECT * FROM Libros RIGHT JOIN Prestamos ON Libros.isbn = Prestamos.libro WHERE Prestamos.libro IS NULL");

        System.out.println("-----------------------------");
        System.out.println("Libros que no se han prestado");
        System.out.println("-----------------------------");
        while (resultSet.next()) {
            System.out.println("ISBN: " + resultSet.getInt("isbn") + ", Título: " + resultSet.getString("titulo") + ", Autor: " + resultSet.getString("autor") + ", Copias: " + resultSet.getInt("copias"));
        }
        System.out.println();

        // Indicar el número el préstamo más largo (mayor diferencia entre fechainicio y fechafin)
        resultSet = statement.executeQuery("SELECT estudiante, libro, DATEDIFF(fechafin, fechainicio) AS dias FROM Prestamos ORDER BY dias DESC LIMIT 1");

        System.out.println("----------------------------");
        System.out.println("Préstamo más largo (en días)");
        System.out.println("----------------------------");
        while (resultSet.next()) {
            System.out.println("Estudiante: " + resultSet.getInt("estudiante") + ", Libro: " + resultSet.getInt("libro") + ", Días: " + resultSet.getInt("dias"));
        }
        System.out.println();

        // 2. Ampliar la BBDD creando una nueva tabla "Profesores" de los que se conocerá id, nombre, edad y telefono.
        statement.execute("CREATE TABLE IF NOT EXISTS Profesores (id INT PRIMARY KEY, nombre VARCHAR(255), edad INT, telefono INT)");
        
        // 3. Introducir dos profesores: Profesor["Emilio",52,123456789], Profesor["Almudena",46,987654321]
        statement.execute("INSERT INTO Profesores VALUES (1, 'Emilio', 52, 123456789)");
        statement.execute("INSERT INTO Profesores VALUES (2, 'Almudena', 46, 987654321)");
        
        // 4. Crear una tabla Horarios que guarda las clases que imparten los profesores a los estudiantes, teniendo en cuenta el módulo, el día, la hora y los estudiantes a los que se imparte clase.	
        statement.execute("CREATE TABLE IF NOT EXISTS Horarios (id INT PRIMARY KEY, modulo VARCHAR(255), dia VARCHAR(255), hora VARCHAR(255), estudiantes VARCHAR(255))");
        
        // 5. Realizar una copia de seguridad (backup) de la BBDD. Este proceso se hará siguiendo los siguientes pasos:
        doBackup();
    }

    private void doBackup() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS EstudiantesBackup (id INT PRIMARY KEY, nombre VARCHAR(255), edad INT)");
        statement.execute("CREATE TABLE IF NOT EXISTS LibrosBackup (isbn INT PRIMARY KEY, titulo VARCHAR(255), autor VARCHAR(255), copias INT)");
        statement.execute("CREATE TABLE IF NOT EXISTS PrestamosBackup (estudiante INT, libro INT, fechainicio DATE, fechafin DATE)");
        statement.execute("CREATE TABLE IF NOT EXISTS ProfesoresBackup (id INT PRIMARY KEY, nombre VARCHAR(255), edad INT, telefono INT)");

        statement.execute("INSERT INTO EstudiantesBackup SELECT * FROM Estudiantes");
        statement.execute("INSERT INTO LibrosBackup SELECT * FROM Libros");
        statement.execute("INSERT INTO PrestamosBackup SELECT * FROM Prestamos");
        statement.execute("INSERT INTO ProfesoresBackup SELECT * FROM Profesores");

        ResultSet resultSet = statement.executeQuery("SELECT * FROM EstudiantesBackup");

        System.out.println("----------------------");
        System.out.println("Listado de estudiantes");
        System.out.println("----------------------");
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id") + ", Nombre: " + resultSet.getString("nombre") + ", Edad: " + resultSet.getInt("edad"));
        }
        System.out.println();

        resultSet = statement.executeQuery("SELECT * FROM LibrosBackup");

        System.out.println("----------------");
        System.out.println("Listado de libros");
        System.out.println("----------------");
        while (resultSet.next()) {
            System.out.println("ISBN: " + resultSet.getInt("isbn") + ", Título: " + resultSet.getString("titulo") + ", Autor: " + resultSet.getString("autor") + ", Copias: " + resultSet.getInt("copias"));
        }
        System.out.println();

        resultSet = statement.executeQuery("SELECT * FROM PrestamosBackup");

        System.out.println("-------------------");
        System.out.println("Listado de préstamos");
        System.out.println("-------------------");
        while (resultSet.next()) {
            System.out.println("Estudiante: " + resultSet.getInt("estudiante") + ", Libro: " + resultSet.getInt("libro") + ", Fecha inicio: " + resultSet.getDate("fechainicio") + ", Fecha fin: " + resultSet.getDate("fechafin"));
        }
        System.out.println();
    }
}
