package CONTROLLER.CRUD;

import MODEL.Modulo_Profesional;
import java.util.ArrayList;


public class GestorModulos {

    private ArrayList<Modulo_Profesional> listaModulos = new ArrayList<>();

    // ── Create ──────────────────────────────────────────
public void crear(String nombre, String ciclo, int horas, String docente, int curso) {
    Modulo_Profesional modulo = Modulo_Profesional.crearModulo_Profesional(nombre, ciclo, horas, docente, curso);
    if (modulo != null) {
        listaModulos.add(modulo);
        System.out.println("Módulo procesado correctamente.");
    }
}

    // ── Read ──────────────────────────────────────────
    public void listar() {
        if (listaModulos.isEmpty()) {
            System.out.println("No hay módulos registrados.");
            return;
        }
        System.out.println("\nLista de todos los módulos:");
        for (Modulo_Profesional modulo : listaModulos) {
            System.out.println(modulo);
        }
    }

    // ── Buscar ──────────────────────────────────────────
    public Modulo_Profesional buscarPorID(int id) {
        for (Modulo_Profesional modulo : listaModulos) {
            if (modulo.getID_Modulo() == id) {
                return modulo;
            }
        }
        return null;
    }

    public ArrayList<Modulo_Profesional> filtrarPorCiclo(String ciclo) {
        ArrayList<Modulo_Profesional> resultado = new ArrayList<>();
        for (Modulo_Profesional modulo : listaModulos) {
            if (modulo.getCiclo_Perteneciente().equalsIgnoreCase(ciclo)) {
                resultado.add(modulo);
            }
        }
        return resultado;
    }

    public ArrayList<Modulo_Profesional> filtrarPorCurso(int curso) {
        ArrayList<Modulo_Profesional> resultado = new ArrayList<>();
        for (Modulo_Profesional modulo : listaModulos) {
            if (modulo.getCurso_Imp() == curso) {
                resultado.add(modulo);
            }
        }
        return resultado;
    }

    // ── Update ──────────────────────────────────────────
    public void actualizar(int id, String nombre, String ciclo, int horas, String docente, int curso) {
        Modulo_Profesional modulo = buscarPorID(id);
        if (modulo == null) {
            System.out.println("Módulo no encontrado con ID: " + id);
            return;
        }
        modulo.setNombre_Modulo(nombre);
        modulo.setCiclo_Perteneciente(ciclo);
        modulo.setHoras_t(horas);
        modulo.setNombre_Docente(docente);
        modulo.setCurso_Imp(curso);
        System.out.println("\n¡Módulo modificado exitosamente!");
        System.out.println(modulo);
    }

    // ── Delete ──────────────────────────────────────────
    public void eliminar(int id) {
        Modulo_Profesional modulo = buscarPorID(id);
        if (modulo == null) {
            System.out.println("Módulo no encontrado con ID: " + id);
            return;
        }
        listaModulos.remove(modulo);
        System.out.println("¡Módulo eliminado exitosamente!");
    }

    public ArrayList<Modulo_Profesional> getListaModulos() {
        return listaModulos;
    }
}
