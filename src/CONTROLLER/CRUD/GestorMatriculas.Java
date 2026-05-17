import java.util.ArrayList;

public class GestorMatriculas {

    private ArrayList<Matricula> listaMatriculas = new ArrayList<>();

    // ── Create ──────────────────────────────────────────
    public void crear(int idAlumno, int idModulo, String convocatoria, String calificacion, int fechaEvaluacion) {
        if (convocatoria.equalsIgnoreCase("ordinaria") && existeOrdinaria(idAlumno, idModulo)) {
            System.out.println("Error: ya existe una calificación ordinaria para este alumno en este módulo.");
            return;
        }
        Matricula matricula = new Matricula(idAlumno, idModulo, convocatoria, calificacion, fechaEvaluacion);
        listaMatriculas.add(matricula);
        System.out.println("\n¡Matrícula creada exitosamente!");
        System.out.println(matricula);
    }

    // ── Read ──────────────────────────────────────────
    public void listar() {
        if (listaMatriculas.isEmpty()) {
            System.out.println("No hay matrículas registradas.");
            return;
        }
        System.out.println("\nLista de todas las matrículas:");
        for (Matricula matricula : listaMatriculas) {
            System.out.println(matricula);
        }
    }

    // ── Buscar ──────────────────────────────────────────
    public Matricula buscarPorID(int id) {
        for (Matricula matricula : listaMatriculas) {
            if (matricula.getID_Matricula() == id) {
                return matricula;
            }
        }
        return null;
    }

    public ArrayList<Matricula> buscarPorAlumno(int idAlumno) {
        ArrayList<Matricula> resultado = new ArrayList<>();
        for (Matricula matricula : listaMatriculas) {
            if (matricula.getID_Alumno() == idAlumno) {
                resultado.add(matricula);
            }
        }
        return resultado;
    }

    public ArrayList<Matricula> buscarPorModulo(int idModulo) {
        ArrayList<Matricula> resultado = new ArrayList<>();
        for (Matricula matricula : listaMatriculas) {
            if (matricula.getID_Modulo() == idModulo) {
                resultado.add(matricula);
            }
        }
        return resultado;
    }

    // ── Regla de negocio ──────────────────────────────────────────
    private boolean existeOrdinaria(int idAlumno, int idModulo) {
        for (Matricula matricula : listaMatriculas) {
            if (matricula.getID_Alumno() == idAlumno
                    && matricula.getID_Modulo() == idModulo
                    && matricula.getConvocatoria().equalsIgnoreCase("ordinaria")) {
                return true;
            }
        }
        return false;
    }

    // ── Update ──────────────────────────────────────────
    public void actualizar(int id, String convocatoria, String calificacion, int fechaEvaluacion) {
        Matricula matricula = buscarPorID(id);
        if (matricula == null) {
            System.out.println("Matrícula no encontrada con ID: " + id);
            return;
        }
        matricula.setConvocatoria(convocatoria);
        matricula.setCalificación(calificacion);
        matricula.setFecha_Evaluación(fechaEvaluacion);
        System.out.println("\n¡Matrícula modificada exitosamente!");
        System.out.println(matricula);
    }

    // ── Delete ──────────────────────────────────────────
    public void eliminar(int id) {
        Matricula matricula = buscarPorID(id);
        if (matricula == null) {
            System.out.println("Matrícula no encontrada con ID: " + id);
            return;
        }
        listaMatriculas.remove(matricula);
        System.out.println("¡Matrícula eliminada exitosamente!");
    }

    public ArrayList<Matricula> getListaMatriculas() {
        return listaMatriculas;
    }
}