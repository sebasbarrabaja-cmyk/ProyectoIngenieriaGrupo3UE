package MODEL;
public class Alumno {
    private int ID;
    private String Nombre;
    private String Apellido;
    private String DNI;
    private String Email;
    private String Teléfono;
    private int Fecha_Nacimiento;
    private String Ciclo_formativo;
    private int Curso;
    private String Estado;

    private static int contadorID = 1;

    private static int generarID() {
        return contadorID++;
    }

    // Permite al gestor JSON sincronizar el contador tras la carga de archivos
    public static void sincronizarContador(int nuevoValor) {
        if (nuevoValor > contadorID) {
            contadorID = nuevoValor;
        }
    }

    private Alumno(String Nombre, String Apellido, String DNI, String Email,
                  String Teléfono, int Fecha_Nacimiento, String Ciclo_formativo,
				   int Curso, String Estado) {
        setID(generarID());
        setNombre(Nombre);
        setApellido(Apellido);
        setDNI(DNI);
        setEmail(Email);
        setTeléfono(Teléfono);
        setFecha_Nacimiento(Fecha_Nacimiento);
        setCiclo_formativo(Ciclo_formativo);
        setCurso(Curso);
        setEstado(Estado);
    }

    public static Alumno crearAlumno(String Nombre, String Apellido, String DNI, String Email,
                                     String Teléfono, int Fecha_Nacimiento, String Ciclo_formativo,
                                     int Curso, String Estado) {
        try {
            return new Alumno(Nombre, Apellido, DNI, Email, Teléfono, Fecha_Nacimiento, Ciclo_formativo, Curso, Estado);
        } catch (IllegalArgumentException e) {
            System.err.println("[VALIDACIÓN FAILED] No se pudo registrar al alumno (" + Nombre + " " + Apellido + "): " + e.getMessage());
            
            // Revertimos el incremento del contador estático si la instanciación falló
            contadorID--; 
            
            return null; // El alumno no se crea y se retorna un valor nulo de forma controlada
        }
    }

    // ── Getters ──────────────────────────────────────────
    public int getID() { return ID; }
    public String getNombre() { return Nombre; }
    public String getApellido() { return Apellido; }
    public String getDNI() { return DNI; }
    public String getEmail() { return Email; }
    public String getTeléfono() { return Teléfono; }
    public int getFecha_Nacimiento() { return Fecha_Nacimiento; }
    public String getCiclo_formativo() { return Ciclo_formativo; }
    public int getCurso() { return Curso; }
    public String getEstado() { return Estado; }

    // ── Setters ──────────────────────────────────────────
    public void setID(int iD) { ID = iD; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        Nombre = nombre.trim().toLowerCase();
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        Apellido = apellido.trim().toLowerCase();
    }

    public void setDNI(String dni) {
        if (dni == null || !dni.matches("\\d{8}[A-Za-z]")) {
            throw new IllegalArgumentException("El DNI debe tener 8 números y una letra");
        }
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCorrecta = letras.charAt(numero % 23);
        char letraIntroducida = Character.toUpperCase(dni.charAt(8));
        if (letraIntroducida != letraCorrecta) {
            throw new IllegalArgumentException("La letra del DNI no es correcta");
        }
        DNI = dni.toUpperCase();
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El email debe contener @");
        }
        Email = email.trim().toLowerCase();
    }

    public void setTeléfono(String teléfono) {
        if (teléfono == null || !teléfono.matches("\\d{9}")) {
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos");
        }
        Teléfono = teléfono.trim();
    }

    public void setFecha_Nacimiento(int fecha_Nacimiento) {
        if (2026 - fecha_Nacimiento < 16) {
            throw new IllegalArgumentException("El alumno debe tener al menos 16 años");
        }
        Fecha_Nacimiento = fecha_Nacimiento;
    }

    public void setCiclo_formativo(String ciclo_formativo) {
        if (ciclo_formativo == null || ciclo_formativo.trim().isEmpty()) {
            throw new IllegalArgumentException("El ciclo formativo no puede estar vacío");
        }
        Ciclo_formativo = ciclo_formativo.trim().toLowerCase();
    }

    public void setCurso(int curso) {
        if (curso < 1 || curso > 4) {
            throw new IllegalArgumentException("El curso debe estar entre 1 y 4");
        }
        Curso = curso;
    }

    public void setEstado(String estado) {
        if (estado == null || !estado.equalsIgnoreCase("matriculado")
                && !estado.equalsIgnoreCase("baja")
                && !estado.equalsIgnoreCase("graduado")) {
            throw new IllegalArgumentException("El estado debe ser: matriculado, baja o graduado");
        }
        Estado = estado.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return "──────────────────────────────\n" +
                "  Alumno #" + ID + "\n" +
                "──────────────────────────────\n" +
                "  Nombre:   " + Nombre + " " + Apellido + "\n" +
                "  DNI:      " + DNI + "\n" +
                "  Email:    " + Email + "\n" +
                "  Teléfono: " + Teléfono + "\n" +
                "  Ciclo:    " + Ciclo_formativo + "\n" +
                "  Curso:    " + Curso + "\n" +
                "  Estado:   " + Estado + "\n" +
                "  ID:       " + ID + "\n" +
                "──────────────────────────────";
    }
}