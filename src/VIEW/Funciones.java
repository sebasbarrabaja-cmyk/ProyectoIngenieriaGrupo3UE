package VIEW;
 
import MODEL.Alumno;
import MODEL.Matricula;
 
public class Funciones {
 
    //Lista los alumnos que cumplan los filtros indicados.Pasar "" o -1 en un filtro para ignorarlo.
    public static void listarAlumnosFiltrados(Alumno[] alumnos, String ciclo, int curso, String estado) {
 
        boolean encontrado = false;
 
        System.out.println("LISTADO DE ALUMNOS FILTRADOS");
        System.out.println("-----------------------------");
 
        for (int i = 0; i < alumnos.length; i++) {
 
            if (alumnos[i] == null) {
                System.out.println("[AVISO] Posición " + i + " del array contiene un alumno nulo (registro inválido), se omite.");
                System.out.println("-----------------------------");
                continue;
            }

            boolean cumpleCiclo  = ciclo.isEmpty()  || alumnos[i].getCiclo_formativo().equalsIgnoreCase(ciclo);
            boolean cumpleCurso  = curso == -1       || alumnos[i].getCurso() == curso;
            boolean cumpleEstado = estado.isEmpty()  || alumnos[i].getEstado().equalsIgnoreCase(estado);
 
            if (cumpleCiclo && cumpleCurso && cumpleEstado) {
                System.out.println(alumnos[i]);
                System.out.println("-----------------------------");
                encontrado = true;
            }
        }
 
        if (!encontrado) {
            System.out.println("No se ha encontrado ningun alumno con esos filtros.");
        }
    }
 
    //Busca un alumno por nombre.Devuelve la posición en el array, o -1 si no se encuentra
    public static int buscarAlumnoPorNombre(Alumno[] alumnos, String nombreAlumno) {
 
        for (int i = 0; i < alumnos.length; i++) {
            if (alumnos[i] == null) continue;
            if (alumnos[i].getNombre().equalsIgnoreCase(nombreAlumno)) {
                return i;
            }
        }
        return -1;
    }
 
    //Busca un alumno por su ID.Devuelve la posición en el array, o -1 si no se encuentra.

    public static int buscarAlumnoPorID(Alumno[] alumnos, int idAlumno) {
 
        for (int i = 0; i < alumnos.length; i++) {
            if (alumnos[i] == null) continue;
            if (alumnos[i].getID() == idAlumno) {
                return i;
            }
        }
        return -1;
    }
 
    //Lista las calificaciones de todos los alumnos de un módulo.
 
    public static void listarCalificacionesModulo(Alumno[] alumnos, Matricula[] matriculas, int idModulo) {
 
        boolean encontrado = false;
 
        System.out.println("CALIFICACIONES DEL MODULO #" + idModulo);
        System.out.println("----------------------------------------");
 
        for (int i = 0; i < matriculas.length; i++) {
 
            if (matriculas[i].getID_Modulo() == idModulo) {
 
                int posicionAlumno = buscarAlumnoPorID(alumnos, matriculas[i].getID_Alumno());
 
                if (posicionAlumno == -1) {
                    System.out.println("Error: alumno no encontrado -> ID " + matriculas[i].getID_Alumno());
                    System.out.println("----------------------------------------");
                } else {
                    System.out.println("Alumno:       " + alumnos[posicionAlumno].getNombre()
                                        + " " + alumnos[posicionAlumno].getApellido());
                    System.out.println("Módulo:       #" + matriculas[i].getID_Modulo());
                    System.out.println("Convocatoria: " + matriculas[i].getConvocatoria());
                    System.out.println("Calificacion: " + matriculas[i].getCalificación());
                    System.out.println("----------------------------------------");
                }
 
                encontrado = true;
            }
        }
 
        if (!encontrado) {
            System.out.println("No hay calificaciones para ese modulo.");
        }
    }
 
    // Calcula la nota media de un módulo. Las notas "NP" no cuentan.
    
    public static double calcularMediaModulo(Matricula[] matriculas, int idModulo) {
 
        double suma = 0;
        int contador = 0;
 
        for (int i = 0; i < matriculas.length; i++) {
 
            if (matriculas[i].getID_Modulo() == idModulo) {
 
                String calificacion = matriculas[i].getCalificación();
 
                if (!calificacion.equalsIgnoreCase("NP")) {
                    double nota = Double.parseDouble(calificacion);
                    if (nota < 0 || nota > 10) {
                        System.out.println("Error: calificacion fuera de rango -> " + calificacion);
                    } else {
                        suma += nota;
                        contador++;
                    }
                }
            }
        }
 
        return contador == 0 ? 0 : suma / contador;
    }
 
    //Comprueba una calificación concreta: válida si es NP o está entre 0 y 10.
  
    public static void comprobarCalificacion(String calificacion) {
 
        if (calificacion.equalsIgnoreCase("NP")) {
            System.out.println("Calificacion correcta: NP");
        } else {
            try {
                double nota = Double.parseDouble(calificacion);
                if (nota >= 0 && nota <= 10) {
                    System.out.println("Calificacion correcta: " + nota);
                } else {
                    System.out.println("Error: calificacion fuera de rango.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: calificacion no es un número válido.");
            }
        }
    }
 
    //Comprueba si un archivo está dañado (de momento solo verifica si está vacío).
    
    public static void comprobarArchivo(String contenidoArchivo) {
 
        if (contenidoArchivo.isEmpty()) {
            System.out.println("Error: archivo dañado o vacío.");
        } else {
            System.out.println("Archivo cargado correctamente.");
        }
    }
}