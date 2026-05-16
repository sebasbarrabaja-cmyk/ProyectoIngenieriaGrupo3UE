package fp_CRUD;

import java.util.ArrayList;
import java.util.Scanner;

public class CRUD {
	
    Scanner sc = new Scanner(System.in);
    
    private ArrayList<Alumno> News_Alumnos = new ArrayList<>();

    public void Create() {
        System.out.print("Introduzca el ID del Alumno : ");
        int ID_NewAL = sc.nextInt();
        System.out.print("Introduzca el Nombre del Alumno : ");
        String Nombre_NewAL = sc.next();
        System.out.print("Introduzca el Apellido del Alumno : ");
        String Apellido_NewAL = sc.next();
        System.out.print("Introduzca el DNI del Alumno : ");
        String DNI_NewAL = sc.next();
        System.out.print("Introduzca el Email del dragón : ");
        String Email_NewAL = sc.next();
        System.out.print("Introduzca el número telefonico del Alumno (Sino tiene el de su tutor legal) : ");
        int Teléfono_NewAL = sc.nextInt();
        System.out.print("Introduzca la fecha de nacimiento del Alumno  : ");
        int Fecha_Nacimiento_NewAL = sc.nextInt();
        System.out.print("Introduzca el Ciclo al que pertenece el Alumno  : ");
        String Ciclo_formativo_NewAL = sc.next();
        System.out.print("Introduzca el Curso en el que se encuentra el Alumno  : ");
        String Curso_NewAL = sc.next();
        System.out.print("Introduzca si el Alumno esta matriculado de baja o graduado : ");
        String Estado_NewAL = sc.next(); 
        
        News_Alumnos.add(new Alumno (ID_NewAL , Nombre_NewAL , Apellido_NewAL , DNI_NewAL , Email_NewAL , Teléfono_NewAL,Fecha_Nacimiento_NewAL,Ciclo_formativo_NewAL,Curso_NewAL,Estado_NewAL));

	}
    
	public void Read() {
        System.out.print("\nLista de todos los Alumnos : ");
        for (Alumno alumno : News_Alumnos) {
        	alumno.toString();
        }
    }
	
	public Alumno Search_for_ID() {
		System.out.print("Introduzca id del Alumno:");
		int idBuscar = sc.nextInt();
		
		for (Alumno alumnos : News_Alumnos) {
			if (alumnos.getID() == idBuscar) {
				System.out.println("Alumno encontrado:");
				System.out.print(alumnos);
				return alumnos;
			}
		}
		
		System.out.println("Alumno no encontrado, no existe un Alumno con ID: " + idBuscar );
		return null;
    }
	
	
	public Alumno Search_for_Name() {
		System.out.print("Introduzca nombre del Alumno:");
		String nameBuscar = sc.next();
		for (Alumno alumno : News_Alumnos) {
			if (alumno.getNombre().equals(nameBuscar)) {
				System.out.println("Alumno encontrado:");
				System.out.println(alumno);
				return alumno;
			}
		}
		System.out.println("Alumno no encontrado, no existe un Alumno con Nombre : " + nameBuscar);
		return null;
    }
	
	public void Update() {
	    
	    System.out.println("\n--- Modificar Alumno ---");
	    System.out.println("1. Buscar por ID de la Matricula");
	    System.out.println("2. Buscar por Nombre de la Matricula");
	    System.out.print("Elige opción: ");
	    int opcion = sc.nextInt();
	    sc.nextLine(); // Limpiar buffer
	    
	    Alumno alumno = null; // Declarar FUERA del if
	    
	    if (opcion == 1) {
	    	alumno = Search_for_ID();
	    } else if (opcion == 2) {
	    	alumno = Search_for_Name();
	    } else {
	        System.out.println("Opción no válida");
	        return;
	    }
	    
	    // Verificar si se encontró el dragón
	    if (alumno == null) {
	        return;
	    }
	    
	    System.out.println("\n--- Introduce los nuevos datos ---");
	    System.out.print("Nueva ID (actual: " + alumno.getID() + "): ");
	    int NewID = sc.nextInt();
	    System.out.print("Nuevo Nombre (actual: " + alumno.getNombre() + "): ");
	    String Newnombre = sc.nextLine();	    
	    System.out.print("Nuevo Apellido (actual: " + alumno.getApellido() + "): ");
	    String NewApellido = sc.nextLine();	    
	    System.out.print("Nuevo DNI (actual: " + alumno.getDNI() + "): ");
	    String NewDNi = sc.nextLine();	    
	    System.out.print("Nueva Email (actual: " + alumno.getEmail() + "): ");
	    String NewEmail = sc.nextLine();
	    System.out.print("Nueva Numero Telefónico (actual: " + alumno.getTeléfono() + "): ");
	    int NewTeléfono = sc.nextInt();
	    System.out.print("Nueva Fecha de Nacimiento (actual: " + alumno.getFecha_Nacimiento() + "): ");
	    int NewF_N = sc.nextInt();
	    System.out.print("Nuevo Ciclo formativo (actual: " + alumno.getCiclo_formativo() + "): ");
	    String NewC_F = sc.nextLine();
	    System.out.print("Nuevo Curso (actual: " + alumno.getCurso() + "): ");
	    String NewCurso = sc.nextLine();
	    System.out.print("Nuevo Estado de matriculación (actual: " + alumno.getEstado() + "): ");
	    String NewEstado = sc.nextLine();
	    sc.nextLine();

	    alumno.setID(NewID);
	    alumno.setNombre(Newnombre);
	    alumno.setApellido(NewApellido);
	    alumno.setDNI(NewDNi);
	    alumno.setEmail(NewEmail);
	    alumno.setTeléfono(NewTeléfono);
	    alumno.setFecha_Nacimiento(NewF_N);
	    alumno.setCiclo_formativo(NewC_F);
	    alumno.setCurso(NewCurso);
	    alumno.setEstado(NewEstado);

	    
	    System.out.println("\n¡Alumno modificado exitosamente!");
	    System.out.println(alumno);
		}
	
	public void Delete() {
	    System.out.println("\n=== Eliminar Alumno ===");
	    System.out.println("1. Buscar por ID");
	    System.out.println("2. Buscar por Nombre");
	    System.out.print("Elige opción: ");
	    int opcion = sc.nextInt();
	    sc.nextLine();
	    
	    Alumno alumno = null;
	    
	    if (opcion == 1) {
	    	alumno = Search_for_ID();
	    } else if (opcion == 2) {
	    	alumno = Search_for_Name();
	    } else {
	        System.out.println("Opción no válida");
	        return;
	    }
	    
	    if (alumno == null) {
	        return;
	    }
	    sc.next();
	    System.out.print("\n¿Está seguro de eliminar este Alumno? (si/no): ");
	    String confirmacion = sc.nextLine();
	    
	    if (confirmacion.equalsIgnoreCase("si")) {
	    	News_Alumnos.remove(alumno);
	        System.out.println("¡Alumno eliminado exitosamente!");
	    } 
	    
	    else if (confirmacion.equalsIgnoreCase("no")){
	        System.out.println("Eliminación cancelada");
	    }
	}
}
