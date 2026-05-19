import MODEL.Alumno;
import MODEL.Matricula;
import CONTROLLER.CRUD.GestorAlumnos;
import CONTROLLER.CRUD.GestorMatriculas;
import CONTROLLER.CRUD.GestorModulos;
import PERSISTENCE.JSON_Persistencia;

public class Main {

    public static void main(String[] args) {

        GestorAlumnos    gestorAlumnos    = new GestorAlumnos();
        GestorMatriculas gestorMatriculas = new GestorMatriculas();
        GestorModulos    gestorModulos    = new GestorModulos();

        JSON_Persistencia json = new JSON_Persistencia(gestorAlumnos, gestorModulos, gestorMatriculas);
        json.cargarTodo();

        if (!gestorAlumnos.getListaAlumnos().isEmpty()) {
            System.out.println("[INFO] Datos cargados desde JSON. Alumnos: " + gestorAlumnos.getListaAlumnos().size());
        } else {
            System.out.println("[INFO] No se detectaron datos previos. Creando demostración...");

            // Inserción de Módulos
            gestorModulos.crear("Programacion",   "DAM", 256, "Carlos Ruiz",    1);
            gestorModulos.crear("Bases de Datos", "DAM", 192, "Ana Martinez",   1);
            gestorModulos.crear("Sistemas",       "DAW", 160, "Pedro Sanchez",  1);

            // Inserción de Alumnos
            gestorAlumnos.crear("Mikel",  "Fernandez", "12345678Z", "mikel@email.com",  "600111222", 2005, "DAM", 1, "matriculado");
            gestorAlumnos.crear("Juan",   "Lopez",     "87654321X", "juan@email.com",   "600222333", 2004, "DAM", 1, "matriculado");
            gestorAlumnos.crear("Laura",  "Garcia",    "11223344A", "laura@email.com",  "600333444", 2003, "DAW", 2, "baja");
            gestorAlumnos.crear("Sofia",  "Perez",     "44556677B", "sofia@email.com",  "600444555", 2004, "DAM", 1, "matriculado");

            // Inserción de Matrículas
            gestorMatriculas.crear(1, 1, "ordinaria",      "8.5",  2026);
            gestorMatriculas.crear(1, 2, "ordinaria",      "NP",   2026);
            gestorMatriculas.crear(2, 1, "ordinaria",      "6",    2026);
            gestorMatriculas.crear(2, 2, "ordinaria",      "7.5",  2026);
            gestorMatriculas.crear(4, 1, "extraordinaria", "5",    2026);

            json.guardarTodo();
            System.out.println("[INFO] Datos guardados en archivos JSON.");
        }

        // Diagnóstico del estado en memoria
        System.out.println("\n--- ESTADO EN MEMORIA ---");
        gestorAlumnos.listar();
        gestorModulos.listar();
        gestorMatriculas.listar();

        // Consultas de prueba
        System.out.println("\n--- PRUEBAS DE CONSULTA ---");
        Alumno encontrado = gestorAlumnos.buscarPorID(1);
        if (encontrado != null) {
            System.out.println("ID 1: " + encontrado.getNombre() + " " + encontrado.getApellido());
        }

        System.out.println("\nFiltrar por estado 'matriculado':");
        for (Alumno a : gestorAlumnos.filtrarPorEstado("matriculado")) {
            System.out.println(" · " + a.getNombre() + " " + a.getApellido() + " - " + a.getCiclo_formativo().toUpperCase());
        }

        System.out.println("\nMatrículas del alumno ID 1:");
        for (Matricula m : gestorMatriculas.buscarPorAlumno(1)) {
            System.out.println(" · Mat #" + m.getID_Matricula() + " | Mod #" + m.getID_Modulo() + " | Nota: " + m.getCalificación());
        }

        // Modificación y persistencia
        System.out.println("\n--- MODIFICACIÓN DE DATOS ---");
        Alumno alumnoAModificar = gestorAlumnos.buscarPorID(3);
        if (alumnoAModificar != null) {
            System.out.println("Estado inicial (Laura): " + alumnoAModificar.getEstado());
            alumnoAModificar.setEstado("graduado");
            System.out.println("Estado final (Laura): " + alumnoAModificar.getEstado());
            json.guardarAlumnos();
            System.out.println("[JSON] Cambios guardados en alumnos.json");
        }
    }
}