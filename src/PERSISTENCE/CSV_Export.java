package PERSISTENCE;
 
import MODEL.Alumno;
import MODEL.Modulo_Profesional;
import MODEL.Matricula;
import CONTROLLER.CRUD.GestorAlumnos;
import CONTROLLER.CRUD.GestorModulos;
import CONTROLLER.CRUD.GestorMatriculas;
 
import java.io.*;
import java.util.ArrayList;
 
public class CSV_Extport {
 
    private static final String RUTA_CSV_ALUMNOS    = "data/alumnos.csv";
    private static final String RUTA_CSV_MODULOS    = "data/modulos.csv";
    private static final String RUTA_CSV_MATRICULAS = "data/matriculas.csv";
 
    private final GestorAlumnos    gestorAlumnos;
    private final GestorModulos    gestorModulos;
    private final GestorMatriculas gestorMatriculas;
 
    public CSV_Exportacion(GestorAlumnos gestorAlumnos, GestorModulos gestorModulos, GestorMatriculas gestorMatriculas) {
        this.gestorAlumnos    = gestorAlumnos;
        this.gestorModulos    = gestorModulos;
        this.gestorMatriculas = gestorMatriculas;
        crearDirectorioData();
    }
 
    private void crearDirectorioData() {
        File dir = new File("data");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("[CSV] Carpeta 'data/' creada.");
            }
        }
    }
 
    // ── Exportación completa ──────────────────────────────────────────
 
    public void exportarTodo() {
        exportarAlumnos();
        exportarModulos();
        exportarMatriculas();
        System.out.println("[CSV] Todos los datos han sido exportados correctamente.");
    }
 
    // ── Exportar Alumnos ──────────────────────────────────────────
 
    public void exportarAlumnos() {
        ArrayList<Alumno> lista = gestorAlumnos.getListaAlumnos();
 
        if (lista.isEmpty()) {
            System.out.println("[CSV] No hay alumnos para exportar.");
            return;
        }
 
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_CSV_ALUMNOS))) {
 
            // Cabecera
            pw.println("ID,Nombre,Apellido,DNI,Email,Telefono,Fecha_Nacimiento,Ciclo_Formativo,Curso,Estado");
 
            // Filas
            for (Alumno a : lista) {
                pw.printf("%d,%s,%s,%s,%s,%s,%d,%s,%d,%s%n",
                    a.getID(),
                    escaparCampo(a.getNombre()),
                    escaparCampo(a.getApellido()),
                    escaparCampo(a.getDNI()),
                    escaparCampo(a.getEmail()),
                    escaparCampo(a.getTeléfono()),
                    a.getFecha_Nacimiento(),
                    escaparCampo(a.getCiclo_formativo()),
                    a.getCurso(),
                    escaparCampo(a.getEstado())
                );
            }
 
            System.out.println("[CSV] Alumnos exportados (" + lista.size() + " registros) → " + RUTA_CSV_ALUMNOS);
 
        } catch (IOException e) {
            System.err.println("[CSV] Error al exportar alumnos: " + e.getMessage());
        }
    }
 
    // ── Exportar Módulos ──────────────────────────────────────────
 
    public void exportarModulos() {
        ArrayList<Modulo_Profesional> lista = gestorModulos.getListaModulos();
 
        if (lista.isEmpty()) {
            System.out.println("[CSV] No hay módulos para exportar.");
            return;
        }
 
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_CSV_MODULOS))) {
 
            pw.println("ID_Modulo,Nombre_Modulo,Ciclo_Perteneciente,Horas_Totales,Nombre_Docente,Curso_Impartido");
 
            for (Modulo_Profesional m : lista) {
                pw.printf("%d,%s,%s,%d,%s,%d%n",
                    m.getID_Modulo(),
                    escaparCampo(m.getNombre_Modulo()),
                    escaparCampo(m.getCiclo_Perteneciente()),
                    m.getHoras_t(),
                    escaparCampo(m.getNombre_Docente()),
                    m.getCurso_Imp()
                );
            }
 
            System.out.println("[CSV] Módulos exportados (" + lista.size() + " registros) → " + RUTA_CSV_MODULOS);
 
        } catch (IOException e) {
            System.err.println("[CSV] Error al exportar módulos: " + e.getMessage());
        }
    }
 
    // ── Exportar Matrículas ──────────────────────────────────────────
 
    public void exportarMatriculas() {
        ArrayList<Matricula> lista = gestorMatriculas.getListaMatriculas();
 
        if (lista.isEmpty()) {
            System.out.println("[CSV] No hay matrículas para exportar.");
            return;
        }
 
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_CSV_MATRICULAS))) {
 
            pw.println("ID_Matricula,ID_Alumno,ID_Modulo,Convocatoria,Calificacion,Fecha_Evaluacion");
 
            for (Matricula mat : lista) {
                pw.printf("%d,%d,%d,%s,%s,%d%n",
                    mat.getID_Matricula(),
                    mat.getID_Alumno(),
                    mat.getID_Modulo(),
                    escaparCampo(mat.getConvocatoria()),
                    escaparCampo(mat.getCalificación()),
                    mat.getFecha_Evaluación()
                );
            }
 
            System.out.println("[CSV] Matrículas exportadas (" + lista.size() + " registros) → " + RUTA_CSV_MATRICULAS);
 
        } catch (IOException e) {
            System.err.println("[CSV] Error al exportar matrículas: " + e.getMessage());
        }
    }
 
    // ── Utilidad: escapar campos que contengan comas o comillas ──────
 
    private String escaparCampo(String campo) {
        if (campo == null) return "";
        // Si el campo contiene coma, comilla doble o salto de línea, se envuelve entre comillas dobles
        if (campo.contains(",") || campo.contains("\"") || campo.contains("\n")) {
            return "\"" + campo.replace("\"", "\"\"") + "\"";
        }
        return campo;
    }
}