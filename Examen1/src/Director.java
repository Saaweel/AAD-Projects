public class Director extends Manager {
    private String area;

    public Director(String name, int number, int salary, String department, String area) {
        super(name, number, salary, department);
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Director [Nombre: " + this.getName() + "; Id: " + this.getNumber() + "; Salary: " + this.getSalary() + "; Departamento: " + this.getDepartment() + "; Area: " + this.area  + "]";
    }

    @Override
    public String toCSV() {
        return super.toCSV() + "," + this.area;
    }
}
