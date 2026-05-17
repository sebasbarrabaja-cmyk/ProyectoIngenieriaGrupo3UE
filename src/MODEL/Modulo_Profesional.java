package MODEL;
public class Modulo_Profesional {
    private int ID_Modulo;
    private String Nombre_Modulo;
    private String Ciclo_Perteneciente;
    private int Horas_t;
    private String Nombre_Docente;
    private int Curso_Imp;

    private static int contadorID = 1;

    private static int generarID() {
        return contadorID++;
    }

    public Modulo_Profesional(String Nombre_Modulo, String Ciclo_Perteneciente,
                               int Horas_t, String Nombre_Docente, int Curso_Imp) {
        setID_Modulo(generarID());
        setNombre_Modulo(Nombre_Modulo);
        setCiclo_Perteneciente(Ciclo_Perteneciente);
        setHoras_t(Horas_t);
        setNombre_Docente(Nombre_Docente);
        setCurso_Imp(Curso_Imp);
    }

    // ── Getters ──────────────────────────────────────────
    public int getID_Modulo() { return ID_Modulo; }
    public String getNombre_Modulo() { return Nombre_Modulo; }
    public String getCiclo_Perteneciente() { return Ciclo_Perteneciente; }
    public int getHoras_t() { return Horas_t; }
    public String getNombre_Docente() { return Nombre_Docente; }
    public int getCurso_Imp() { return Curso_Imp; }

    // ── Setters ──────────────────────────────────────────
    public void setID_Modulo(int iD_Modulo) { ID_Modulo = iD_Modulo; }

    public void setNombre_Modulo(String nombre_Modulo) {
        if (nombre_Modulo == null || nombre_Modulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del módulo no puede estar vacío");
        }
        Nombre_Modulo = nombre_Modulo.trim().toLowerCase();
    }

    public void setCiclo_Perteneciente(String ciclo_Perteneciente) {
        if (ciclo_Perteneciente == null || ciclo_Perteneciente.trim().isEmpty()) {
            throw new IllegalArgumentException("El ciclo no puede estar vacío");
        }
        Ciclo_Perteneciente = ciclo_Perteneciente.trim().toLowerCase();
    }

    public void setHoras_t(int horas_t) {
        if (horas_t <= 0) {
            throw new IllegalArgumentException("Las horas deben ser mayor a 0");
        }
        Horas_t = horas_t;
    }

    public void setNombre_Docente(String nombre_Docente) {
        if (nombre_Docente == null || nombre_Docente.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del docente no puede estar vacío");
        }
        Nombre_Docente = nombre_Docente.trim().toLowerCase();
    }

    public void setCurso_Imp(int curso_Imp) {
        if (curso_Imp < 1 || curso_Imp > 4) {
            throw new IllegalArgumentException("El curso debe estar entre 1 y 4");
        }
        Curso_Imp = curso_Imp;
    }

    @Override
    public String toString() {
        return "──────────────────────────────\n" +
                "  Módulo #" + ID_Modulo + "\n" +
                "──────────────────────────────\n" +
                "  Nombre:  " + Nombre_Modulo + "\n" +
                "  Ciclo:   " + Ciclo_Perteneciente + "\n" +
                "  Horas:   " + Horas_t + "h\n" +
                "  Docente: " + Nombre_Docente + "\n" +
                "  Curso:   " + Curso_Imp + "\n" +
                "──────────────────────────────";
    }
}