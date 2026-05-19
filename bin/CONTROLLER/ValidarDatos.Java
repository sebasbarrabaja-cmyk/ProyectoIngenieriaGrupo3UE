package CONTROLLER;
public class ValidarDatos {

    // ── DNI ──────────────────────────────────────────
    public static boolean validarDNI(String dni) {
        if (dni == null || !dni.matches("\\d{8}[A-Za-z]")) {
            return false;
        }
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCorrecta = letras.charAt(numero % 23);
        char letraIntroducida = Character.toUpperCase(dni.charAt(8));
        return letraIntroducida == letraCorrecta;
    }

    // ── Edad ──────────────────────────────────────────
    public static boolean validarEdad(int anioNacimiento) {
        return (2026 - anioNacimiento) >= 16;
    }

    // ── Nota ──────────────────────────────────────────
    public static boolean validarNota(String nota) {
        if (nota == null) return false;
        if (nota.equalsIgnoreCase("NP")) return true;
        try {
            double n = Double.parseDouble(nota);
            return n >= 0 && n <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}