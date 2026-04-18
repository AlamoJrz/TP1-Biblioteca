package unlar.edu.ar.ui;

import unlar.edu.ar.model.*;
import unlar.edu.ar.service.*;
import unlar.edu.ar.exception.*;

public class Main {
    public static void main(String[] args) {
        // Instanciamos tu servicio (nota: mantuve tu nombre de clase 'biblioteca')
        Biblioteca servicio = new Biblioteca();

        System.out.println("--- INICIANDO SISTEMA DE BIBLIOTECA UNLaR ---");

        // 1. Creación de 5 libros
        Libro lib1 = new Libro("111", "Guía de como ser un Wachin", "Javilon", 2023, true);
        Libro lib2 = new Libro("222", "50 Sombras de Alejandro Mercado", "Alejandro Mercado", 2018, true);
        Libro lib3 = new Libro("333", "PapuResumen Informatica II", "Alejo Alamo", 2022, true);
        Libro lib4 = new Libro("444", "CFK LIBRE-Arrojala a los lobos y volvera liderando la manada", "Dalma Espejo", 2021, true);
        Libro lib5 = new Libro("555", "Como coquear", "Gaston de la Rosa", 2024, true);

        servicio.agregarLibro(lib1);
        servicio.agregarLibro(lib2);
        servicio.agregarLibro(lib3);
        servicio.agregarLibro(lib4);
        servicio.agregarLibro(lib5);

        // 2. Creación de 3 estudiantes
        Estudiante est1 = new Estudiante("LEG01", "Alejandro Mercado", "Ing. en Sistemas", "ale@unlar.edu.ar");
        Estudiante est2 = new Estudiante("LEG02", "Juan Perez", "Ing. Informática", "juan@unlar.edu.ar");
        Estudiante est3 = new Estudiante("LEG03", "Lucas Pensa", "Lic. en Sistemas", "lucas@unlar.edu.ar");

        servicio.agregarEstudiante(est1);
        servicio.agregarEstudiante(est2);
        servicio.agregarEstudiante(est3);

        System.out.println("\n--- PRUEBA 1: PRÉSTAMO EXITOSO ---");
        try {
            servicio.registrarPrestamo("111", "LEG01");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        System.out.println("\n--- PRUEBA 2: CAPTURA DE EXCEPCIONES ---");

        // Excepción A: Libro No Disponible (El libro 111 ya lo tiene Alejandro)
        try {
            System.out.println("Intentando prestar libro ya ocupado...");
            servicio.registrarPrestamo("111", "LEG02");
        } catch (LibroNoDisponibleException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Falló: se esperaba LibroNoDisponibleException");
        }

        // Excepción B: Estudiante No Encontrado
        try {
            System.out.println("Intentando prestar a un legajo fantasma...");
            servicio.registrarPrestamo("222", "LEG99");
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Falló: se esperaba EstudianteNoEncontradoException");
        }

        // Excepción C: Límite de Préstamos Excedido
        try {
            System.out.println("Llenando el cupo de Juan (LEG02)...");
            servicio.registrarPrestamo("222", "LEG02");
            servicio.registrarPrestamo("333", "LEG02");
            servicio.registrarPrestamo("444", "LEG02");
            System.out.println("Intentando sacar el 4to libro...");
            servicio.registrarPrestamo("555", "LEG02"); // Este debe fallar
        } catch (LimitePrestamosExcedidoException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Falló: se esperaba LimitePrestamosExcedidoException");
        }

        System.out.println("\n--- PRUEBA 3: DEVOLUCIÓN Y MULTA RECURSIVA ---");
        // Calculamos devolución del libro 111 de Alejanndro con 15 días de retraso.
        // Valor base del libro: $15000
        servicio.registrarDevolucion("111", "LEG01", 15, 15000.0);

        System.out.println("\n--- FIN DEL PROGRAMA ---");
    }
}