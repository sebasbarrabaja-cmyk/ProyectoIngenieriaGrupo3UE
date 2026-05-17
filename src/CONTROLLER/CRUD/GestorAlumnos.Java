import java.util.ArrayList;

public class GestorAlumnos {

    private ArrayList<Alumno> listaAlumnos = new ArrayList<>();

    // ── Create ──────────────────────────────────────────
    public void crear(String nombre, String apellido, String dni, String email,
                      String telefono, int fechaNacimiento, String ciclo, int curso, String estado) {
        Alumno alumno = new Alumno(nombre, apellido, dni, email, telefono, fechaNacimiento, ciclo, curso, estado);
        listaAlumnos.add(alumno);
        System.out.println("\n¡Alumno creado exitosamente!");
        System.out.println(alumno);
    }

    // ── Read ──────────────────────────────────────────
    public void listar() {
        if (listaAlumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }
        System.out.println("\nLista de todos los alumnos:");
        for (Alumno alumno : listaAlumnos) {
            System.out.println(alumno);
        }
    }

    // ── Buscar ──────────────────────────────────────────
    public Alumno buscarPorID(int id) {
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getID() == id) {
                return alumno;
            }
        }
        return null;
    }

    public Alumno buscarPorDNI(String dni) {
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getDNI().equalsIgnoreCase(dni)) {
                return alumno;
            }
        }
        return null;
    }

    public Alumno buscarPorNombre(String nombre) {
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getNombre().equalsIgnoreCase(nombre)) {
                return alumno;
            }
        }
        return null;
    }

    // ── Filtros ──────────────────────────────────────────
    public ArrayList<Alumno> filtrarPorCiclo(String ciclo) {
        ArrayList<Alumno> resultado = new ArrayList<>();
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getCiclo_formativo().equalsIgnoreCase(ciclo)) {
                resultado.add(alumno);
            }
        }
        return resultado;
    }

    public ArrayList<Alumno> filtrarPorCurso(int curso) {
        ArrayList<Alumno> resultado = new ArrayList<>();
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getCurso() == curso) {
                resultado.add(alumno);
            }
        }
        return resultado;
    }

    public ArrayList<Alumno> filtrarPorEstado(String estado) {
        ArrayList<Alumno> resultado = new ArrayList<>();
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getEstado().equalsIgnoreCase(estado)) {
                resultado.add(alumno);
            }
        }
        return resultado;
    }

    // ── Update ──────────────────────────────────────────
    public void actualizar(int id, String nombre, String apellido, String dni, String email,
                           String telefono, int fechaNacimiento, String ciclo, int curso, String estado) {
        Alumno alumno = buscarPorID(id);
        if (alumno == null) {
            System.out.println("Alumno no encontrado con ID: " + id);
            return;
        }
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setDNI(dni);
        alumno.setEmail(email);
        alumno.setTeléfono(telefono);
        alumno.setFecha_Nacimiento(fechaNacimiento);
        alumno.setCiclo_formativo(ciclo);
        alumno.setCurso(curso);
        alumno.setEstado(estado);
        System.out.println("\n¡Alumno modificado exitosamente!");
        System.out.println(alumno);
    }

    // ── Delete ──────────────────────────────────────────
    public void eliminar(int id) {
        Alumno alumno = buscarPorID(id);
        if (alumno == null) {
            System.out.println("Alumno no encontrado con ID: " + id);
            return;
        }
        listaAlumnos.remove(alumno);
        System.out.println("¡Alumno eliminado exitosamente!");
    }

    public ArrayList<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }
}