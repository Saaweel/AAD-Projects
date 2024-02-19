public class Persona {
    private int id;
    private String nombre;
    private String fecha;
    private Perro perro;

    public Persona(int id, String nombre, String fecha, Perro perro) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.perro = perro;
    }

    @Override
    public String toString() {
        return "Persona [id=" + id + ", nombre=" + nombre + ", fecha=" + fecha + ", perro=" + perro + "]";
    }
}