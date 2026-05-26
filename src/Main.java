
import CONTROLLER.CRUD.GestorAlumnos;
import CONTROLLER.CRUD.GestorMatriculas;
import CONTROLLER.CRUD.GestorModulos;
import PERSISTENCE.JSON_Persistencia;
import PERSISTENCE.CSV_Export;
import VIEW.AppGUI;
import VIEW.MenuTerminal;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        GestorAlumnos    gestorAlumnos    = new GestorAlumnos();
        GestorModulos    gestorModulos    = new GestorModulos();
        GestorMatriculas gestorMatriculas = new GestorMatriculas();

        JSON_Persistencia json = new JSON_Persistencia(gestorAlumnos, gestorModulos, gestorMatriculas);
        CSV_Export csv = new CSV_Export(gestorAlumnos, gestorModulos, gestorMatriculas);
        json.cargarTodo();

        if (gestorAlumnos.getListaAlumnos().isEmpty()) {
            System.out.println("[INFO] No se detectaron datos previos. Creando demostración...");
            sembrarDatosDemo(gestorAlumnos, gestorModulos, gestorMatriculas);
            json.guardarTodo();
        } else {
            System.out.println("[INFO] Datos cargados desde JSON. Alumnos: "
                    + gestorAlumnos.getListaAlumnos().size());
        }

        // ── Selección de modo de uso ─────────────────────────────────────
        Scanner sc = new Scanner(System.in);
        int modo;
        try {
            do {
                System.out.println("\n1. Terminal (modo texto)");
                System.out.println("2. App (interfaz gráfica)");
                System.out.println("0. Salir");
                System.out.print("Opción: ");

                String linea = sc.nextLine().trim();
                try {
                    modo = Integer.parseInt(linea);
                } catch (NumberFormatException e) {
                    modo = -1;
                }

                if (modo == 1) {
                    MenuTerminal menu = new MenuTerminal(sc, gestorAlumnos, gestorModulos, gestorMatriculas, json);
                    menu.iniciar();
                } else if (modo == 2) {
                    System.out.println("\n[INFO] Iniciando la interfaz gráfica de usuario...");
                    
                    // Invocación de la API gráfica con los parámetros requeridos
                    AppGUI.iniciar(gestorAlumnos, gestorModulos, gestorMatriculas, json);
                    
                    // Opcional: Se puede establecer 'modo = 0' o finalizar el bucle si se desea 
                    // que la terminal deje de solicitar opciones mientras la GUI está activa.
                } else if (modo == 0) {
                    System.out.println("\nSaliendo del programa...");
                } else {
                    System.out.println("Opción no válida, inténtalo de nuevo.");
                }
            } while (modo != 0);
        } finally {
            csv.exportarTodo();
        }
    }

    // ── Datos de ejemplo iniciales ───────────────────────────────────────
    private static void sembrarDatosDemo(GestorAlumnos gestorAlumnos,
                                         GestorModulos gestorModulos,
                                         GestorMatriculas gestorMatriculas) {
        // Módulos
        gestorModulos.crear("Programacion",   "DAM", 256, "Carlos Ruiz",   1);
        gestorModulos.crear("Bases de Datos", "DAM", 192, "Ana Martinez",  1);
        gestorModulos.crear("Sistemas",       "DAW", 160, "Pedro Sanchez", 1);

        // Alumnos
        gestorAlumnos.crear("Mikel", "Fernandez", "12345678Z", "mikel@email.com", "600111222", 2005, "DAM", 1, "matriculado");
        gestorAlumnos.crear("Juan",  "Lopez",     "87654321X", "juan@email.com",  "600222333", 2004, "DAM", 1, "matriculado");
        gestorAlumnos.crear("Laura", "Garcia",    "11223344B", "laura@email.com", "600333444", 2003, "DAW", 2, "baja");
        gestorAlumnos.crear("Sofia", "Perez",     "44556677L", "sofia@email.com", "600444555", 2004, "DAM", 1, "matriculado");

        // Matrículas
        gestorMatriculas.crear(1, 1, "ordinaria",      "8.5", 2026);
        gestorMatriculas.crear(1, 2, "ordinaria",      "NP",  2026);
        gestorMatriculas.crear(2, 1, "ordinaria",      "6",   2026);
        gestorMatriculas.crear(2, 2, "ordinaria",      "7.5", 2026);
        gestorMatriculas.crear(4, 1, "extraordinaria", "5",   2026);
    }
}
