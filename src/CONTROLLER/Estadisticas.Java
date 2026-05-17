import java.util.ArrayList;

public class Estadisticas {

    private GestorMatriculas gestorMatriculas;

    public Estadisticas(GestorMatriculas gestorMatriculas) {
        this.gestorMatriculas = gestorMatriculas;
    }

    // ── Nota media de un módulo ──────────────────────────────────────────
    public double notaMedia(int idModulo) {
        ArrayList<Matricula> matriculas = gestorMatriculas.buscarPorModulo(idModulo);
        if (matriculas.isEmpty()) {
            System.out.println("No hay matrículas para este módulo.");
            return 0;
        }
        double suma = 0;
        int count = 0;
        for (Matricula matricula : matriculas) {
            if (!matricula.getCalificación().equalsIgnoreCase("NP")) {
                suma += Double.parseDouble(matricula.getCalificación());
                count++;
            }
        }
        if (count == 0) return 0;
        return Math.round((suma / count) * 100.0) / 100.0;
    }

    // ── Porcentaje de aprobados ──────────────────────────────────────────
    public double porcentajeAprobados(int idModulo) {
        ArrayList<Matricula> matriculas = gestorMatriculas.buscarPorModulo(idModulo);
        if (matriculas.isEmpty()) return 0;
        int aprobados = 0;
        for (Matricula matricula : matriculas) {
            if (!matricula.getCalificación().equalsIgnoreCase("NP")) {
                if (Double.parseDouble(matricula.getCalificación()) >= 5) {
                    aprobados++;
                }
            }
        }
        return Math.round(((double) aprobados / matriculas.size()) * 10000.0) / 100.0;
    }

    // ── Porcentaje de suspensos ──────────────────────────────────────────
    public double porcentajeSuspensos(int idModulo) {
        ArrayList<Matricula> matriculas = gestorMatriculas.buscarPorModulo(idModulo);
        if (matriculas.isEmpty()) return 0;
        int suspensos = 0;
        for (Matricula matricula : matriculas) {
            if (matricula.getCalificación().equalsIgnoreCase("NP")
                    || Double.parseDouble(matricula.getCalificación()) < 5) {
                suspensos++;
            }
        }
        return Math.round(((double) suspensos / matriculas.size()) * 10000.0) / 100.0;
    }

    // ── Mostrar resumen completo de un módulo ──────────────────────────────────────────
    public void resumenModulo(int idModulo) {
        System.out.println("──────────────────────────────");
        System.out.println("  Estadísticas módulo #" + idModulo);
        System.out.println("──────────────────────────────");
        System.out.println("  Nota media:   " + notaMedia(idModulo));
        System.out.println("  Aprobados:    " + porcentajeAprobados(idModulo) + "%");
        System.out.println("  Suspensos:    " + porcentajeSuspensos(idModulo) + "%");
        System.out.println("──────────────────────────────");
    }
}