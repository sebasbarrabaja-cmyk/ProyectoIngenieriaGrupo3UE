package VIEW;


import MODEL.Alumno;
import MODEL.Matricula;
import MODEL.Modulo_Profesional;
import CONTROLLER.CRUD.GestorAlumnos;
import CONTROLLER.CRUD.GestorModulos;
import CONTROLLER.CRUD.GestorMatriculas;
import CONTROLLER.Estadisticas;
import PERSISTENCE.JSON_Persistencia;
import PERSISTENCE.PDF_Export;
import PERSISTENCE.CSV_Export;

import java.util.List;
import java.util.Scanner;

public class MenuTerminal {

    private final Scanner sc;

    private final GestorAlumnos     gestorAlumnos;
    private final GestorModulos     gestorModulos;
    private final GestorMatriculas  gestorMatriculas;
    private final Estadisticas      estadisticas;
    private final JSON_Persistencia json;
    private final Login             login;

    public MenuTerminal(Scanner sc,
                        GestorAlumnos gestorAlumnos,
                        GestorModulos gestorModulos,
                        GestorMatriculas gestorMatriculas,
                        JSON_Persistencia json) {
        this.sc               = sc;
        this.gestorAlumnos    = gestorAlumnos;
        this.gestorModulos    = gestorModulos;
        this.gestorMatriculas = gestorMatriculas;
        this.json             = json;
        this.estadisticas     = new Estadisticas(gestorMatriculas);
        this.login            = new Login(gestorAlumnos);
    }

    // ── Punto de entrada del modo terminal ───────────────────────────────
    public void iniciar() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE GESTIÓN ACADÉMICA - TERMINAL  ║");
        System.out.println("╚═══════════════════════════════════════════╝");

        int resultadoLogin = autenticar();
        if (resultadoLogin == -1) {
            System.out.println("\nDemasiados intentos fallidos. Saliendo...");
            return;
        }
        if (resultadoLogin == 0) {
            System.out.println("\nSaliendo del modo terminal...");
            return;
        }

        if (login.esSecretaria()) {
            menuSecretaria();
        } else if (login.esAlumno()) {
            menuAlumno();
        }

        System.out.println("\nSesión finalizada. ¡Hasta pronto!");
    }

    // ── Autenticación ────────────────────────────────────────────────────
    private int autenticar() {
        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            System.out.println("\n──────── INICIAR SESIÓN ────────");
            System.out.println("  1. Entrar como SECRETARÍA");
            System.out.println("  2. Entrar como ALUMNO");
            System.out.println("  0. Salir");
            int op = leerEntero("Selecciona una opción: ");

            if (op == 1) {
                System.out.print("Usuario: ");
                String usuario = sc.nextLine().trim();
                System.out.print("Contraseña: ");
                String pass = sc.nextLine();
                if (login.loginSecretaria(usuario, pass)) {
                    System.out.println("\n[OK] Bienvenida, " + login.getDescripcionSesion());
                    return 1;
                }
                System.out.println("[ERROR] Usuario o contraseña incorrectos.");
                intentos++;
            } else if (op == 2) {
                System.out.print("Introduce tu DNI: ");
                String dni = sc.nextLine().trim();
                if (login.loginAlumno(dni)) {
                    System.out.println("\n[OK] Bienvenido/a, " + login.getDescripcionSesion());
                    return 1;
                }
                System.out.println("[ERROR] No existe ningún alumno con ese DNI.");
                intentos++;
            } else if (op == 0) {
                return 0;
            } else {
                System.out.println("Opción no válida.");
            }

            if (intentos > 0 && intentos < MAX_INTENTOS) {
                System.out.println("Intentos restantes: " + (MAX_INTENTOS - intentos));
            }
        }
        return -1;
    }

    // ── Menú secretaría (acceso total) ───────────────────────────────────
    private void menuSecretaria() {
        int op;
        do {
            System.out.println("\n╔═══════════ MENÚ SECRETARÍA ═══════════╗");
            System.out.println("  ── ALUMNOS ──");
            System.out.println("   1. Crear alumno");
            System.out.println("   2. Listar alumnos");
            System.out.println("   3. Buscar alumno (ID / DNI / nombre)");
            System.out.println("   4. Filtrar alumnos (ciclo / curso / estado)");
            System.out.println("   5. Modificar alumno");
            System.out.println("   6. Eliminar alumno");
            System.out.println("  ── MÓDULOS ──");
            System.out.println("   7. Crear módulo");
            System.out.println("   8. Listar módulos");
            System.out.println("   9. Modificar módulo");
            System.out.println("  10. Eliminar módulo");
            System.out.println("  ── MATRÍCULAS ──");
            System.out.println("  11. Crear matrícula");
            System.out.println("  12. Listar matrículas");
            System.out.println("  13. Modificar matrícula");
            System.out.println("  14. Eliminar matrícula");
            System.out.println("  ── CONSULTAS / EXPORTACIÓN ──");
            System.out.println("  15. Estadísticas de un módulo");
            System.out.println("  16. Guardar todo (JSON)");
            System.out.println("  17. Exportar ficha de alumno a PDF");
            System.out.println("  18. Exportar a CSV");
            System.out.println("   0. Cerrar sesión");
            System.out.println("╚═══════════════════════════════════════╝");

            op = leerEntero("Opción: ");

            if      (op == 1)  crearAlumno();
            else if (op == 2)  gestorAlumnos.listar();
            else if (op == 3)  buscarAlumno();
            else if (op == 4)  filtrarAlumnos();
            else if (op == 5)  modificarAlumno();
            else if (op == 6)  eliminarAlumno();
            else if (op == 7)  crearModulo();
            else if (op == 8)  gestorModulos.listar();
            else if (op == 9)  modificarModulo();
            else if (op == 10) eliminarModulo();
            else if (op == 11) crearMatricula();
            else if (op == 12) gestorMatriculas.listar();
            else if (op == 13) modificarMatricula();
            else if (op == 14) eliminarMatricula();
            else if (op == 15) estadisticasModulo();
            else if (op == 16) json.guardarTodo();
            else if (op == 17) exportarPDF();
            else if (op == 18) exportarCSV();
            else if (op == 0)  { }
            else               System.out.println("Opción no válida.");

        } while (op != 0);
    }

    // ── Menú alumno (solo lectura de sus datos) ──────────────────────────
    private void menuAlumno() {
        Alumno yo = login.getAlumnoLogueado();
        int op;
        do {
            System.out.println("\n╔═══════════ MENÚ ALUMNO ═══════════╗");
            System.out.println("   1. Ver mis datos personales");
            System.out.println("   2. Ver mis notas / matrículas");
            System.out.println("   3. Generar mi ficha en PDF");
            System.out.println("   0. Cerrar sesión");
            System.out.println("╚═══════════════════════════════════╝");

            op = leerEntero("Opción: ");

            if (op == 1) {
                System.out.println(yo);
            } else if (op == 2) {
                verMisNotas(yo);
            } else if (op == 3) {
                List<Matricula> mias = gestorMatriculas.buscarPorAlumno(yo.getID());
                String ruta = "alumno_" + yo.getID() + ".pdf";
                PDF_Export.exportarAlumno(yo, mias, ruta);
            } else if (op == 0) {
                // cerrar sesión
            } else {
                System.out.println("Opción no válida.");
            }
        } while (op != 0);
    }

    private void verMisNotas(Alumno yo) {
        List<Matricula> mias = gestorMatriculas.buscarPorAlumno(yo.getID());
        if (mias.isEmpty()) {
            System.out.println("No tienes matrículas registradas.");
            return;
        }
        System.out.println("\n── TUS MATRÍCULAS ──");
        for (Matricula m : mias) {
            Modulo_Profesional mod = gestorModulos.buscarPorID(m.getID_Modulo());
            String nombreMod = (mod != null) ? mod.getNombre_Modulo() : ("#" + m.getID_Modulo());
            System.out.println("  · " + nombreMod
                    + " | Convocatoria: " + m.getConvocatoria()
                    + " | Nota: " + m.getCalificación());
        }
    }

    // ── Operaciones sobre alumnos (secretaría) ───────────────────────────
    private void crearAlumno() {
        System.out.println("\n── NUEVO ALUMNO ──");
        String nombre   = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String dni      = leerTexto("DNI (8 números + letra): ");
        String email    = leerTexto("Email: ");
        String telefono = leerTexto("Teléfono (9 dígitos): ");
        int    nac      = leerEntero("Año de nacimiento: ");
        String ciclo    = leerTexto("Ciclo formativo: ");
        int    curso    = leerEntero("Curso (1-4): ");
        String estado   = leerTexto("Estado (matriculado/baja/graduado): ");
        gestorAlumnos.crear(nombre, apellido, dni, email, telefono, nac, ciclo, curso, estado);
    }

    private void buscarAlumno() {
        System.out.println("\n── BUSCAR ALUMNO ──");
        System.out.println("  1. Por ID");
        System.out.println("  2. Por DNI");
        System.out.println("  3. Por nombre");
        int op = leerEntero("Opción: ");
        Alumno a = null;
        if      (op == 1) a = gestorAlumnos.buscarPorID(leerEntero("ID: "));
        else if (op == 2) a = gestorAlumnos.buscarPorDNI(leerTexto("DNI: "));
        else if (op == 3) a = gestorAlumnos.buscarPorNombre(leerTexto("Nombre: "));
        else { System.out.println("Opción no válida."); return; }
        System.out.println(a != null ? a : "No se encontró ningún alumno.");
    }

    private void filtrarAlumnos() {
        System.out.println("\n── FILTRAR ALUMNOS ──");
        System.out.println("  1. Por ciclo");
        System.out.println("  2. Por curso");
        System.out.println("  3. Por estado");
        int op = leerEntero("Opción: ");
        List<Alumno> resultado;
        if      (op == 1) resultado = gestorAlumnos.filtrarPorCiclo(leerTexto("Ciclo: "));
        else if (op == 2) resultado = gestorAlumnos.filtrarPorCurso(leerEntero("Curso: "));
        else if (op == 3) resultado = gestorAlumnos.filtrarPorEstado(leerTexto("Estado: "));
        else { System.out.println("Opción no válida."); return; }
        if (resultado.isEmpty()) {
            System.out.println("Sin resultados.");
        } else {
            for (Alumno a : resultado) System.out.println(a);
        }
    }

    private void modificarAlumno() {
        int id = leerEntero("ID del alumno a modificar: ");
        if (gestorAlumnos.buscarPorID(id) == null) {
            System.out.println("No existe ese alumno.");
            return;
        }
        System.out.println("Introduce los nuevos datos:");
        String nombre   = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String dni      = leerTexto("DNI: ");
        String email    = leerTexto("Email: ");
        String telefono = leerTexto("Teléfono: ");
        int    nac      = leerEntero("Año de nacimiento: ");
        String ciclo    = leerTexto("Ciclo formativo: ");
        int    curso    = leerEntero("Curso (1-4): ");
        String estado   = leerTexto("Estado: ");
        gestorAlumnos.actualizar(id, nombre, apellido, dni, email, telefono, nac, ciclo, curso, estado);
    }

    private void eliminarAlumno() {
        gestorAlumnos.eliminar(leerEntero("ID del alumno a eliminar: "));
    }

    // ── Operaciones sobre módulos (secretaría) ───────────────────────────
    private void crearModulo() {
        System.out.println("\n── NUEVO MÓDULO ──");
        String nombre  = leerTexto("Nombre del módulo: ");
        String ciclo   = leerTexto("Ciclo perteneciente: ");
        int    horas   = leerEntero("Horas totales: ");
        String docente = leerTexto("Nombre del docente: ");
        int    curso   = leerEntero("Curso impartido (1-4): ");
        gestorModulos.crear(nombre, ciclo, horas, docente, curso);
    }

    private void modificarModulo() {
        int id = leerEntero("ID del módulo a modificar: ");
        if (gestorModulos.buscarPorID(id) == null) {
            System.out.println("No existe ese módulo.");
            return;
        }
        String nombre  = leerTexto("Nombre: ");
        String ciclo   = leerTexto("Ciclo: ");
        int    horas   = leerEntero("Horas: ");
        String docente = leerTexto("Docente: ");
        int    curso   = leerEntero("Curso (1-4): ");
        gestorModulos.actualizar(id, nombre, ciclo, horas, docente, curso);
    }

    private void eliminarModulo() {
        gestorModulos.eliminar(leerEntero("ID del módulo a eliminar: "));
    }

    // ── Operaciones sobre matrículas (secretaría) ────────────────────────
    private void crearMatricula() {
        System.out.println("\n── NUEVA MATRÍCULA ──");
        int    idAlumno = leerEntero("ID del alumno: ");
        int    idModulo = leerEntero("ID del módulo: ");
        String conv     = leerTexto("Convocatoria (ordinaria/extraordinaria): ");
        String calif    = leerTexto("Calificación (0-10 o NP): ");
        int    fecha    = leerEntero("Año de evaluación: ");
        gestorMatriculas.crear(idAlumno, idModulo, conv, calif, fecha);
    }

    private void modificarMatricula() {
        int id = leerEntero("ID de la matrícula a modificar: ");
        if (gestorMatriculas.buscarPorID(id) == null) {
            System.out.println("No existe esa matrícula.");
            return;
        }
        String conv  = leerTexto("Convocatoria: ");
        String calif = leerTexto("Calificación: ");
        int    fecha = leerEntero("Año de evaluación: ");
        gestorMatriculas.actualizar(id, conv, calif, fecha);
    }

    private void eliminarMatricula() {
        gestorMatriculas.eliminar(leerEntero("ID de la matrícula a eliminar: "));
    }

    // ── Consultas y exportación (secretaría) ─────────────────────────────
    private void estadisticasModulo() {
        estadisticas.resumenModulo(leerEntero("ID del módulo: "));
    }

    private void exportarPDF() {
        int id = leerEntero("ID del alumno para el PDF: ");
        Alumno a = gestorAlumnos.buscarPorID(id);
        if (a == null) {
            System.out.println("No existe ese alumno.");
            return;
        }
        List<Matricula> mats = gestorMatriculas.buscarPorAlumno(id);
        PDF_Export.exportarAlumno(a, mats, "alumno_" + id + ".pdf");
    }

    private void exportarCSV() {
        CSV_Export csv = new CSV_Export(gestorAlumnos, gestorModulos, gestorMatriculas);
        csv.exportarTodo();
    }

    // ── Utilidades de entrada ────────────────────────────────────────────
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("  → Introduce un número válido.");
            }
        }
    }
}
