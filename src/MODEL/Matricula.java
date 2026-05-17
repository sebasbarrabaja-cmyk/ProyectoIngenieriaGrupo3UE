package MODEL;

public class Matricula {
    private int ID_Matricula;
    private int ID_Alumno;
    private int ID_Modulo;
    private String Convocatoria;
    private String Calificación;
    private int Fecha_Evaluación;

    private static int contadorID = 1;

    private static int generarID() {
        return contadorID++;
    }

    public Matricula(int ID_Alumno, int ID_Modulo, String Convocatoria, String Calificación, int Fecha_Evaluación) {
        setID_Matricula(generarID());
        setID_Alumno(ID_Alumno);
        setID_Modulo(ID_Modulo);
        setConvocatoria(Convocatoria);
        setCalificación(Calificación);
        setFecha_Evaluación(Fecha_Evaluación);
    }

    // ── Getters ──────────────────────────────────────────
    public int getID_Matricula() { return ID_Matricula; }
    public int getID_Alumno() { return ID_Alumno; }
    public int getID_Modulo() { return ID_Modulo; }
    public String getConvocatoria() { return Convocatoria; }
    public String getCalificación() { return Calificación; }
    public int getFecha_Evaluación() { return Fecha_Evaluación; }

    // ── Setters ──────────────────────────────────────────
    public void setID_Matricula(int iD_Matricula) { ID_Matricula = iD_Matricula; }

    public void setID_Alumno(int iD_Alumno) {
        if (iD_Alumno <= 0) {
            throw new IllegalArgumentException("El ID del alumno no es válido");
        }
        ID_Alumno = iD_Alumno;
    }

    public void setID_Modulo(int iD_Modulo) {
        if (iD_Modulo <= 0) {
            throw new IllegalArgumentException("El ID del módulo no es válido");
        }
        ID_Modulo = iD_Modulo;
    }

    public void setConvocatoria(String convocatoria) {
        if (convocatoria == null || !convocatoria.equalsIgnoreCase("ordinaria")
                && !convocatoria.equalsIgnoreCase("extraordinaria")) {
            throw new IllegalArgumentException("La convocatoria debe ser: ordinaria o extraordinaria");
        }
        Convocatoria = convocatoria.trim().toLowerCase();
    }

    public void setCalificación(String calificación) {
        if (calificación == null) {
            throw new IllegalArgumentException("La calificación no puede ser nula");
        }
        if (!calificación.equalsIgnoreCase("NP")) {
            try {
                double nota = Double.parseDouble(calificación);
                if (nota < 0 || nota > 10) {
                    throw new IllegalArgumentException("La calificación debe estar entre 0 y 10");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("La calificación debe ser un número entre 0 y 10 o NP");
            }
        }
        Calificación = calificación.trim().toUpperCase();
    }

    public void setFecha_Evaluación(int fecha_Evaluación) {
        if (fecha_Evaluación < 2000 || fecha_Evaluación > 2026) {
            throw new IllegalArgumentException("La fecha de evaluación no es válida");
        }
        Fecha_Evaluación = fecha_Evaluación;
    }

    @Override
    public String toString() {
        return "──────────────────────────────\n" +
                "  Matrícula #" + ID_Matricula + "\n" +
                "──────────────────────────────\n" +
                "  Alumno:       #" + ID_Alumno + "\n" +
                "  Módulo:       #" + ID_Modulo + "\n" +
                "  Convocatoria: " + Convocatoria + "\n" +
                "  Calificación: " + Calificación + "\n" +
                "  Año eval.:    " + Fecha_Evaluación + "\n" +
                "──────────────────────────────";
    }
}