public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private float height;
    private int siblings;

    public Employee(int id, String firstName, String lastName, String birthDate, float height, int siblings) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.height = height;
        this.siblings = siblings;
    }

    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", height=" + height + ", siblings=" + siblings + "]";
    }
}