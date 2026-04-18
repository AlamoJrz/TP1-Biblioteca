package unlar.edu.ar.model;

public class Estudiante {
    // Atributos de la clase Estudiante
    private String legajo;
    private String nombre;
    private String carrera;
    private String email;

    // Constructor por defecto??
    public Estudiante() {
    }

    // Constructor parametrizado
    public Estudiante(String legajo, String nombre, String carrera, String email) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.carrera = carrera;
        this.email = email;
    }

    // Getters y Setters
    public String getLegajo() {
        return legajo;
    }

    // Setter de legajo agregado (Requisito del TP)
    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // mostrar nombre en mayusculas
    // public String getNombreMayusculas() {
    // return nombre.toUpperCase();
    //}

    @Override
    public String toString() {
        // return "Legajo: " + legajo + "\nNombre: " + nombre + "\nCarrera: " + carrera
        // + "\nEmail: " + email;
        return "Estudiante:{" +
                "Legajo='" + legajo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", carrera='" + carrera + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
