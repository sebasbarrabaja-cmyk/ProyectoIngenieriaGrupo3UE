package VIEW;

import MODEL.Alumno;
import CONTROLLER.CRUD.GestorAlumnos;

public class Login {

    // ── Roles posibles (como texto, igual que los estados) ───────────────
    public static final String ROL_SECRETARIA = "secretaria";
    public static final String ROL_ALUMNO     = "alumno";

    // ── Cuenta predeterminada de secretaría ──────────────────────────────
    private static final String USUARIO_SECRETARIA  = "secretaria";
    private static final String PASSWORD_SECRETARIA = "admin";

    // ── Estado de la sesión actual ───────────────────────────────────────
    private String rolActual;
    private Alumno alumnoLogueado;

    private final GestorAlumnos gestorAlumnos;

    public Login(GestorAlumnos gestorAlumnos) {
        this.gestorAlumnos = gestorAlumnos;
    }

    // ── Login de secretaría (usuario + contraseña) ───────────────────────
    public boolean loginSecretaria(String usuario, String password) {
        if (usuario == null || password == null) {
            return false;
        }
        boolean ok = usuario.trim().equalsIgnoreCase(USUARIO_SECRETARIA)
                  && password.equals(PASSWORD_SECRETARIA);
        if (ok) {
            rolActual      = ROL_SECRETARIA;
            alumnoLogueado = null;
        }
        return ok;
    }

    // ── Login de alumno (solo DNI) ───────────────────────────────────────
    public boolean loginAlumno(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return false;
        }
        Alumno encontrado = gestorAlumnos.buscarPorDNI(dni.trim());
        if (encontrado != null) {
            rolActual      = ROL_ALUMNO;
            alumnoLogueado = encontrado;
            return true;
        }
        return false;
    }

    public void cerrarSesion() {
        rolActual      = null;
        alumnoLogueado = null;
    }

    // ── Consultas de estado ──────────────────────────────────────────────
    public boolean haySesion()        { return rolActual != null; }
    public boolean esSecretaria()     { return ROL_SECRETARIA.equals(rolActual); }
    public boolean esAlumno()         { return ROL_ALUMNO.equals(rolActual); }
    public String  getRolActual()     { return rolActual; }
    public Alumno  getAlumnoLogueado(){ return alumnoLogueado; }

    public String getDescripcionSesion() {
        if (esSecretaria()) {
            return "Secretaría (acceso total)";
        }
        if (esAlumno() && alumnoLogueado != null) {
            return "Alumno: " + alumnoLogueado.getNombre() + " "
                   + alumnoLogueado.getApellido()
                   + " (DNI " + alumnoLogueado.getDNI() + ")";
        }
        return "Sin sesión";
    }
}
