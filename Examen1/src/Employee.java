public class Employee {
    private String name;
    private int number;
    private int salary;

    public Employee(String name, int number, int salary) {
        this.name = name;
        this.number = number;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Empleado [Nombre: " + this.name + "; Id: " + this.number + " Salary: " + this.salary + "]";
    }

    public String toCSV() {
        return this.name + "," + this.number + "," + this.salary;
    }
}
