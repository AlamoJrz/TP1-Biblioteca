package unlar.edu.ar.service;

import unlar.edu.ar.model.*;
import unlar.edu.ar.exception.*;
import java.util.*;
import java.time.LocalDate;


public class Biblioteca {
    private List<Libro> catalogo = new ArrayList<>();
    private Map<String, Estudiante> estudiantes = new HashMap<>();
    private Set<Prestamo> prestamosActivos = new HashSet<>();

    public void agregarLibro(Libro libro) {
        catalogo.add(libro);
    }

    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.put(estudiante.getLegajo(), estudiante);
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro libro : catalogo) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(libro);
            }
        }
        return resultados;
    }

    public void registrarPrestamo(String ibsn, String legajo) throws EstudianteNoEncontradoException,LibroNoDisponibleException,LimitePrestamosExcedidoException{
        Estudiante estudiante = estudiantes.get(legajo);
        if (estudiante == null){
            throw new EstudianteNoEncontradoException("Estudiante con legajo " + legajo + " no encontrado.");
        }

        Libro libroAPrestar = null;
        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(ibsn)) {
                libroAPrestar = libro;
                break;
            }
        }
        if (libroAPrestar == null || !libroAPrestar.isDisponible()) {
            throw new LibroNoDisponibleException("Libro con ISBN " + ibsn + " no disponible.");
        }

        int cantidadPrestamos = 0;
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getEstudiante().getLegajo().equals(legajo)) {
                cantidadPrestamos++;
            }
        }

        if(cantidadPrestamos >= 3) {
            throw new LimitePrestamosExcedidoException("El estudiante con legajo " + legajo + " ha excedido el límite de préstamos.");
        }

        Prestamo nuevoPrestamo = new Prestamo(libroAPrestar, estudiante, LocalDate.now(), null);
        prestamosActivos.add(nuevoPrestamo);
        libroAPrestar.setDisponible(false);
        System.out.println("Préstamo registrado con exito para: " + estudiante.getNombre()  );

    }

    public double calcularMulta(int diasRetraso, double valorLibro){
        if(diasRetraso > 30){
            diasRetraso = 30;
        }

        if(diasRetraso <= 0){
            return 0;
        }

        double multaDeHoy = valorLibro * 0.01;
        return multaDeHoy + calcularMulta (diasRetraso - 1, valorLibro);

    }

    //registro

    public void registrarDevolucion(String isbn, String legajo, int diasRetraso, double valorLibro){
        Prestamo prestamoATerminar = null;

        for (Prestamo p : prestamosActivos) {
            if (p.getLibro().getIsbn().equals(isbn) && p.getEstudiante().getLegajo().equals(legajo)) {
                prestamoATerminar = p;
                break;
            }
        }
        if (prestamoATerminar != null) {
            prestamosActivos.remove(prestamoATerminar);
            prestamoATerminar.getLibro().setDisponible(true);
            System.out.println("Devolución registrada con éxito para: " + prestamoATerminar.getEstudiante().getNombre());
            
            if (diasRetraso > 0) {
                double multa = calcularMulta(diasRetraso, valorLibro);
                System.out.println("El estudiante " + prestamoATerminar.getEstudiante().getNombre() + " tiene una multa de: $" + multa);
            }else{
                System.out.println("El estudiante " + prestamoATerminar.getEstudiante().getNombre() + " no tiene multas.");
            }

        }else {
            System.out.println("No se encontró un préstamo activo para el libro con ISBN " + isbn + " y el estudiante con legajo " + legajo);
        }
    } 



}
