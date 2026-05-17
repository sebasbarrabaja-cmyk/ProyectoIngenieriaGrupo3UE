package fp_CRUD;

import java.util.Scanner;
import java.util.ArrayList;


public class R_M_calificaiones {
	
    Scanner sc = new Scanner(System.in);
	
    private ArrayList<Matricula> News_Matricula = new ArrayList<>();
	
	public Matricula Search_for_ID() {
		System.out.print("Introduzca id de la Matricula del Alumno:");
		int idBuscar = sc.nextInt();
		
		for (Matricula matriculas : News_Matricula) {
			if (matriculas.getID_Matricula() == idBuscar) {
				System.out.println("Alumno encontrado:");
				System.out.print(matriculas);
				return matriculas;
			}
		}
		
		System.out.println("Matricula no encontrado, no existe una Matricula con ID: " + idBuscar );
		return null;
    }
	
	
	public Matricula Search_for_Name() {
		System.out.print("Introduzca nombre de la Matricula del Alumno:");
		String nameBuscar = sc.next();
		for (Matricula matriculas : News_Matricula) {
			if (matriculas.getNombre_Matricula().equals(nameBuscar)) {
				System.out.println("Alumno encontrado:");
				System.out.println(matriculas);
				return matriculas;
			}
		}
		System.out.println("Matricula no encontrado, no existe una Matricula con Nombre : " + nameBuscar);
		return null;
    }
	
	// Comprueba si ya existe una calificación ordinaria para ese alumno y módulo
	public boolean ExisteOrdinaria(String nombre, String modulo) {
		for (Matricula matriculas : News_Matricula) {
			if (matriculas.getNombre_Matricula().equals(nombre) && matriculas.getModulo().equals(modulo) && matriculas.getConvocatoria().equalsIgnoreCase("Ordinaria")) {
				return true;
			}
		}
		return false;
	}
	
	public void Registrar_Calificacion() {

	    System.out.println("\n--- Registrar Calificación ---");

	    System.out.print("Introduce el ID de la Matricula: ");
	    int newID = sc.nextInt();
	    sc.nextLine();

	    System.out.print("Introduce el Nombre del Alumno: ");
	    String newNombre = sc.nextLine();
	    System.out.print("Introduce el Módulo: ");
	    String newModulo = sc.nextLine();
	    System.out.print("Introduce la Convocatoria: ");
	    String newConvocatoria = sc.nextLine();
	    
	    if (newConvocatoria.equalsIgnoreCase("Ordinaria") && ExisteOrdinaria(newNombre, newModulo)) {
	        System.out.println("Error: Ya existe una calificación Ordinaria para " + newNombre + " en el módulo " + newModulo);
	        return;
	    }
	    
	    System.out.print("Introduce la Calificación: ");
	    String newCalificacion = sc.nextLine();
	    System.out.print("Introduce la Fecha de Evaluación: ");
	    int newFecha = sc.nextInt();
	    sc.nextLine();

	    Matricula nuevaMatricula = new Matricula(newID, newNombre, newModulo, newConvocatoria, newCalificacion, newFecha);
	    News_Matricula.add(nuevaMatricula);

	    System.out.println("\n¡Calificación registrada exitosamente!");
	    System.out.println(nuevaMatricula);
	}
	
	
	public void Modificar_Calificacion() {
	    
	    System.out.println("\n--- Modificar Matricula ---");
	    System.out.println("1. Buscar por ID de la Matricula");
	    System.out.println("2. Buscar por Nombre de la Matricula");
	    System.out.print("Elige opción: ");
	    int opcion = sc.nextInt();
	    sc.nextLine(); // Limpiar buffer
	    
	    Matricula matricula = null; // Declarar FUERA del if
	    
	    if (opcion == 1) {
	    	matricula = Search_for_ID();
	    } else if (opcion == 2) {
	    	matricula = Search_for_Name();
	    } else {
	        System.out.println("Opción no válida");
	        return;
	    }
	    
	    // Verificar si se encontró el dragón
	    if (matricula == null) {
	        return;
	    }
	    
	    System.out.println("\n--- Introduce los nuevos datos ---");
	    System.out.print("Nuevo Módulo (actual: " + matricula.getModulo() + "): ");
	    String newModulo = sc.nextLine();
	    System.out.print("Nueva Convocatoria (actual: " + matricula.getConvocatoria() + "): ");
	    String newConvocatoria = sc.nextLine();
	    System.out.print("Nueva Calificación (actual: " + matricula.getCalificación() + "): ");
	    String newCalificacion = sc.nextLine();
	    System.out.print("Nueva Fecha de Evaluación (actual: " + matricula.getFecha_Evaluación() + "): ");
	    int newFecha = sc.nextInt();
	    sc.nextLine();
	    
	    matricula.setModulo(newModulo);
	    matricula.setConvocatoria(newConvocatoria);
	    matricula.setCalificación(newCalificacion);
	    matricula.setFecha_Evaluación(newFecha);

	    
	    System.out.println("\n¡Alumno modificado exitosamente!");
	    System.out.println(matricula);
		}

}
