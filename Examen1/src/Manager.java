public class Manager extends Employee {
    private String department;

    public Manager(String name, int number, int salary, String department) {
        super(name, number, salary);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Gerente [Nombre: " + this.getName() + "; Id: " + this.getNumber() + "; Salary: " + this.getSalary() + "; Departamento: " + this.department + "]";
    }

    @Override
    public String toCSV() {
        return super.toCSV() + "," + this.department;
    }
}
