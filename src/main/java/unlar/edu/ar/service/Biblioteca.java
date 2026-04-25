package unlar.edu.ar.service;

import unlar.edu.ar.model.*;
import unlar.edu.ar.exception.*;
import java.util.*;
import java.time.LocalDate;

/**
 * Servicio principal que gestiona la lógica de negocio de la biblioteca de la UNLaR. [cite: 7, 9]
 */
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

    /**
     * Busca libros en el catálogo por coincidencia parcial en el título. 
     */
    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>(); 
        for (Libro libro : catalogo) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(libro); 
            }
        }
        return resultados;
    }

    /**
     * Registra un nuevo préstamo validando disponibilidad y límites. 
     */
    public void registrarPrestamo(String isbn, String legajo) throws EstudianteNoEncontradoException, LibroNoDisponibleException, LimitePrestamosExcedidoException {
        Estudiante estudiante = estudiantes.get(legajo); 
        if (estudiante == null) {
            throw new EstudianteNoEncontradoException("Estudiante con legajo " + legajo + " no encontrado."); 
        }

        Libro libroAPrestar = null;
        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(isbn)) {
                libroAPrestar = libro; 
                break;
            }
        }

        if (libroAPrestar == null || !libroAPrestar.isDisponible()) {
            throw new LibroNoDisponibleException("Libro con ISBN " + isbn + " no disponible."); 
        }

        int cantidadPrestamos = 0;
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getEstudiante().getLegajo().equals(legajo)) {
                cantidadPrestamos++;
            }
        }

        if (cantidadPrestamos >= 3) {
            throw new LimitePrestamosExcedidoException("El estudiante ha excedido el límite de 3 préstamos."); 
        }

        Prestamo nuevoPrestamo = new Prestamo(libroAPrestar, estudiante, LocalDate.now(), null); 
        prestamosActivos.add(nuevoPrestamo); 
        libroAPrestar.setDisponible(false); 
        System.out.println("Préstamo registrado con éxito para: " + estudiante.getNombre()); 
    }

    /**
     * Calcula la multa de forma recursiva aplicando un 1% por día (máx 30 días). 
     */
    public double calcularMulta(int diasRetraso, double valorLibro) {
        if (diasRetraso > 30) {
            diasRetraso = 30; 
        }

        if (diasRetraso <= 0) {
            return 0;
        }

        double multaDeHoy = valorLibro * 0.01; 
        return multaDeHoy + calcularMulta(diasRetraso - 1, valorLibro);
    }

    /**
     * Libera el libro y calcula la multa correspondiente en la devolución. 
     */
    public void registrarDevolucion(String isbn, String legajo, int diasRetraso, double valorLibro) {
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
                System.out.println("Multa total a pagar: $" + multa); 
            } else {
                System.out.println("El estudiante no tiene multas."); 
            }
        } else {
            System.out.println("No se encontró el préstamo activo."); 
        }
    }

    /**
     * Filtra y muestra todos los préstamos activos de un estudiante específico. 
     */
    public List<Prestamo> listarPrestamosPorEstudiante(String legajo) {
        List<Prestamo> prestamosDelEstudiante = new ArrayList<>(); 
        
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getEstudiante().getLegajo().equals(legajo)) {
                prestamosDelEstudiante.add(prestamo);
            }
        }
        
        System.out.println("Préstamos actuales para el legajo " + legajo + ":");
        if (prestamosDelEstudiante.isEmpty()) {
             System.out.println("  - No registra préstamos pendientes.");
        } else {
             for (Prestamo p : prestamosDelEstudiante) {
                 System.out.println("  - " + p.getLibro().getTitulo());
             }
        }
        
        return prestamosDelEstudiante;
    }
}