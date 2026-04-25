package unlar.edu.ar.model;

public class Estudiante {
    // Atributos de la clase Estudiante
    private String legajo;
    private String nombre;
    private String carrera;
    private String email;

    // Constructor por defecto
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((legajo == null) ? 0 : legajo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Estudiante other = (Estudiante) obj;
        if (legajo == null) {
            if (other.legajo != null)
                return false;
        } else if (!legajo.equals(other.legajo))
            return false;
        return true;
    }

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
