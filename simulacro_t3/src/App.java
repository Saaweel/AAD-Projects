import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class App {
    private LinkedList<Instructor> instructors;

    private LinkedList<Student> students;

    private LinkedList<Dron> drones;

    private Connection connection;

    public static void main(String[] args) throws Exception {
        new App();
    }

    public App() throws Exception {
        this.instructors = new LinkedList<Instructor>();
        this.students = new LinkedList<Student>();
        this.drones = new LinkedList<Dron>();

        this.connectToDB();

        // this.getDataFromDB();

        this.connection.setAutoCommit(false);
        this.generateData();
        this.connection.commit();
    }

    private void connectToDB() throws Exception {
        this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/academia", "root", "");
    }

    private void getDataFromDB() throws Exception {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM instructores");

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            this.instructors.add(new Instructor(result.getInt("id"), result.getString("nss"), result.getString("nombre"), result.getInt("telefono"), result.getString("dirección")));
        }

        statement = this.connection.prepareStatement("SELECT * FROM alumnos");

        result = statement.executeQuery();

        while (result.next()) {
            this.students.add(new Student(result.getInt("id"), result.getString("dni"), result.getString("nombre"), result.getInt("telefono"), result.getString("nivel")));
        }

        statement = this.connection.prepareStatement("SELECsT * FROM drones");

        result = statement.executeQuery();

        while (result.next()) {
            this.drones.add(new Dron(result.getInt("id"), result.getString("marca"), result.getString("modelo"), result.getString("tamaño")));
        }
    }

    private void generateData() throws Exception {
        this.createInstructor(new Instructor("111-25-9788", "Juan Pérez", 924333888, "C/ RioMalo, Mérida"));
        this.createInstructor(new Instructor("358-85-2015", "María Rodríguez", 927123456, "Av. Dos, Cáceres"));
        this.createInstructor(new Instructor("647-69-8417", "Carlos Gómez", 924222555, "C/ LaCorte, Badajoz"));

        this.createStudent( new Student("12345678A", "Laura Sánchez", 612345678, "Principiante"));
        this.createStudent( new Student("87654321B", "Pedro González", 655432189, "Intermedio"));
        this.createStudent( new Student("13579246C", "Ana Martínez", 678901234, "Avanzado"));
        this.createStudent( new Student("98765432D", "Javier López", 699876543, "Principiante"));
        this.createStudent( new Student("45678901E", "Laura Fernández", 634567890, "Intermedio"));
        this.createStudent( new Student("24681357F", "Roberto Ramírez", 677788899, "Avanzado"));
        this.createStudent( new Student("11223344G", "Sara Jiménez", 722568975, "Principiante"));
        this.createStudent( new Student("55443322H", "Pedro Ruiz", 652414152, "Intermedio"));
        this.createStudent( new Student("99887766I", "Elena González", 666111222, "Avanzado"));
        this.createStudent( new Student("66778899J", "Pedro García", 600112233, "Principiante"));

        this.createDron(new Dron("DJI", "Mavic Air 2", "Pequeño"));
        this.createDron(new Dron("Parrot", "Anafi", "Mediano"));
        this.createDron(new Dron("DJI", "Phantom 4 Pro", "Grande"));
        this.createDron(new Dron("Yuneec", "Typhoon H Pro", "Mediano"));
        this.createDron(new Dron("Autel Robotics", "EVO Lite+", "Grande"));
        this.createDron(new Dron("DJI", "Mini 2", "Pequeño"));
    }

    private void createInstructor(Instructor instructor) throws Exception {
        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO instructores (nss, nombre, telefono, dirección) VALUES (?, ?, ?, ?)");

        statement.setString(1, instructor.getNss());
        statement.setString(2, instructor.getName());
        statement.setInt(3, instructor.getPhone());
        statement.setString(4, instructor.getStreet());

        ResultSet result = statement.executeQuery();

        instructor.setId(result.getInt("id"));
        this.instructors.add(instructor);

        statement.close();
    }

    private void createStudent(Student student) throws Exception {
        String last = null;

        for (Student s : this.students) {
            if (s.getName().startsWith(student.getName())) {
                String name = s.getName().split(" ")[0];

                if (name.split("_").length > 0)
                    last = name.split("_")[1];
                else {
                    s.setName(name + "_01");

                    PreparedStatement statement = this.connection.prepareStatement("UPDATE alumnos SET nombre = ? WHERE id = ?");
                    statement.setString(1, name + "_01");
                    statement.setInt(2, s.getId());
                    statement.executeUpdate();

                    student.setName(name + "_02");
                    break;
                }
            }
        }

        if (last != null) {
            student.setName(student.getName() + "_" + String.format("%02d", Integer.parseInt(last) + 1));
        }

        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO alumnos (dni, nombre, telefono, nivel) VALUES (?, ?, ?, ?)");

        statement.setString(1, student.getDni());
        statement.setString(2, student.getName());
        statement.setInt(3, student.getPhone());
        statement.setString(4, student.getLevel());

        ResultSet result = statement.executeQuery();

        student.setId(result.getInt("id"));
        this.students.add(student);

        statement.close();
    }

    private void createDron(Dron dron) throws Exception {
        PreparedStatement statement = this.connection.prepareStatement("SELECT 1 FROM drones WHERE id = ?");
        statement.setInt(1, dron.getId());

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            PreparedStatement statement2 = this.connection.prepareStatement("UPDATE drones SET marca = ?, modelo = ?, tamaño = ? WHERE id = ?");
            statement2.setString(1, dron.getBrand());
            statement2.setString(2, dron.getModel());
            statement2.setString(3, dron.getSize());
            statement2.setInt(4, dron.getId());
            statement2.executeUpdate();

            for (Dron d : this.drones) {
                if (d.equals(dron)) {
                    d.setBrand(dron.getBrand());
                    d.setModel(dron.getModel());
                    d.setSize(dron.getSize());
                    break;
                }
            }
        } else {
            PreparedStatement statement2 = this.connection.prepareStatement("INSERT INTO drones (marca, modelo, tamaño) VALUES (?, ?, ?)");

            statement2.setString(1, dron.getBrand());
            statement2.setString(2, dron.getModel());
            statement2.setString(3, dron.getSize());

            result = statement2.executeQuery();

            dron.setId(result.getInt("id"));
            this.drones.add(dron);
        }

        statement.close();
    }
}
