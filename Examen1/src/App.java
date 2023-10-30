import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Scanner;

public class App {
    private LinkedList<Employee> employees;

    public App() {
        employees = new LinkedList<Employee>();
    }

    public void addEmployee(Employee emp) {
        if (getEmployeeById(emp.getNumber()) != null) {
            System.out.println("No se ha podido añadir el empleado ya que su ID ya está en uso");
            return;
        }

        this.employees.add(emp);
    }

    public void editEmployee(Employee emp) {
        // TODO
    }

    public void removeEmployee(Employee emp) {
        this.employees.remove(emp);
    }

    public Employee createEmployee(Scanner readN, Scanner readS) {
        try {
            System.out.print("Nombre: ");
            String name = readS.nextLine();
            System.out.print("Numero de empleado: ");
            int number = readN.nextInt();
            System.out.print("Salario: ");
            int salary = readN.nextInt();
            System.out.print("Rango (Empleado, Gerente, Director): ");
            String grade = readS.nextLine();
            
            switch (grade) {
                case "Empleado":
                    return new Employee(name, number, salary);
                case "Gerente":
                    System.out.print("Departamento: ");
                    String department = readS.nextLine();

                    return new Manager(name, number, salary, department);
                case "Director":
                    System.out.print("Departamento: ");
                    String dpt = readS.nextLine();

                    System.out.print("Area: ");
                    String area = readS.nextLine();

                    return new Director(name, number, salary, dpt, area);
                default:
                    System.out.println("No ha elegido un tipo de empleado correcto");
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Los datos introducidos no son correctos");
            return null;
        }
    }

    private void showEmployees() {
        for (Employee emp: this.employees) {
            System.out.println(emp);
        }
    }

    private Employee getEmployeeById(int id) {
        for (Employee emp: this.employees) {
            if (emp.getNumber() == id) {
                return emp;
            }
        }

        return null;
    }

    public void saveEmployees(String path) {
        File finalFile = new File(path);
        File dir = new File(finalFile.getParent());

        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(finalFile))) {
            bw.write("Nombre,NumeroEmpleado,Salario,Departamento,AreaResponsable\n");

            for (Employee emp: this.employees) {
                bw.write(emp.toCSV() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportOne(String path) {
        // TODO
    }

    public void generarIndice(String src, String dst) {
        try {
            RandomAccessFile br = new RandomAccessFile(src, "r");
            RandomAccessFile bw = new RandomAccessFile(dst, "rw");
            
            String line = br.readLine();
            
            int pos = (int) br.getFilePointer();

            while ((line = br.readLine()) != null) {
                if (line.contains("Empleados")) {
                    pos = (int) br.getFilePointer();

                    br.readLine();
                
                    bw.writeBytes("Empleados" + ";" + pos + "\n");
                }
                
                if (line.contains("Gerentes")) {
                    pos = (int) br.getFilePointer();
                    
                    br.readLine();
                    
                    bw.writeBytes("Gerentes" + ";" + pos + "\n");
                }

                if (line.contains("Directores")) {
                    pos = (int) br.getFilePointer();
                    
                    br.readLine();
                    
                    bw.writeBytes("Directores" + ";" + pos + "\n");
                }

                pos = (int) br.getFilePointer();
            }

            bw.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtainInfo(String empType) {
        try {
            RandomAccessFile raf = new RandomAccessFile("index.txt", "r");

            String line = raf.readLine();
            String [] lineSplit = line.split(";");

            while (line != null && !lineSplit[0].equals(empType)) {
                line = raf.readLine();
                lineSplit = line.split(";");
            }

            if (line != null) {
                RandomAccessFile br = new RandomAccessFile("exportsOne.txt", "r");
                br.seek(Integer.parseInt(lineSplit[1]));
                
                String ln;

                while (!(ln = br.readLine()).contains("*")) {
                    System.out.println(ln);
                }

                br.close();
            } else {
                System.out.println("No se ha encontrado el dni");
            }

            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();

        File employeesCSV = new File("empleados.csv");

        if (employeesCSV.exists() && employeesCSV.canRead() && employeesCSV.isFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(employeesCSV))) {
                String line = br.readLine();

                while ((line = br.readLine()) != null) {
                    String [] split = line.split(",");

                    switch (split.length) {
                        case 5:
                            app.addEmployee(new Director(split[0], Integer.parseInt(split[1].trim()), Integer.parseInt(split[2].trim()), split[3], split[4]));
                            break;
                        case 4:
                            app.addEmployee(new Manager(split[0], Integer.parseInt(split[1].trim()), Integer.parseInt(split[2].trim()), split[3]));
                            break;
                        default:
                            app.addEmployee(new Employee(split[0], Integer.parseInt(split[1].trim()), Integer.parseInt(split[2].trim())));
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            Scanner readN =  new Scanner(System.in);
            Scanner readS =  new Scanner(System.in);
            int opt;
            
            do {
                try {
                    System.out.println("1. Agregar empleado");
                    System.out.println("2. Modificar empleado");
                    System.out.println("3. Eliminar empleado");
                    System.out.println("4. Mostrar empleados");
                    System.out.println("5. Salir");
                    System.out.print("Introduce una opción: ");
                    opt = readN.nextInt();

                    switch (opt) {
                        case 1:
                            Employee toAdd = app.createEmployee(readN, readS);

                            if (toAdd != null) {
                                app.addEmployee(toAdd);
                                app.saveEmployees("./directorio/salida.csv");
                            }
                            break;
                        case 2:
                            // TODO
                            app.saveEmployees("./directorio/salida.csv");
                            break;
                        case 3:
                            try {
                                System.out.print("Id de empleado: ");
                                int toRemoveId = readS.nextInt();
                                Employee toRemove = app.getEmployeeById(toRemoveId);

                                if (toRemove != null) {
                                    app.removeEmployee(toRemove);
                                    app.saveEmployees("./directorio/salida.csv");
                                    System.out.println("Empleado borrado correctamente");
                                } else {
                                    System.out.println("No se ha encontrado el empleado");
                                }
                            } catch (Exception e) {
                                System.out.println("No es una id de empleado válida");
                            }

                            break;
                        case 4:
                            app.showEmployees();
                            break;
                        default:
                            System.out.println("Esa no es una opción válida");
                            opt = 5;
                            break;
                    }

                    System.out.println("");
                } catch (Exception e) {
                    System.out.println("Esa no es una opción válida");
                    opt = 5;
                }
            } while (opt != 5);

            readN.close();
            readS.close();

            app.exportOne("exportsOne.txt");

            app.generarIndice("exportsOne.txt", "index.txt");

            app.obtainInfo("Directores");
        } else {
            System.out.println("Falta el archivo empleados.csv");
        }
    }
}