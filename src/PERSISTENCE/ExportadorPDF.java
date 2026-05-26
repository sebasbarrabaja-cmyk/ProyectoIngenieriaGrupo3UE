package VIEW;
 
import MODEL.Alumno;
import MODEL.Matricula;
 
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
 
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
 
/**
 * ExportadorPDF
 * Genera un PDF con los datos de un alumno y sus matrículas.
 *
 * Dependencia (añadir al proyecto):
 *   iText 8 Core  →  com.itextpdf:itext-core:8.0.4
 *   o con Maven:
 *   <dependency>
 *       <groupId>com.itextpdf</groupId>
 *       <artifactId>itext-core</artifactId>
 *       <version>8.0.4</version>
 *       <type>pom</type>
 *   </dependency>
 */
public class ExportadorPDF {
 
    // ─── Colores corporativos ────────────────────────────────────────────────
    private static final DeviceRgb COLOR_CABECERA  = new DeviceRgb(30, 90, 160);   // azul oscuro
    private static final DeviceRgb COLOR_SUBTITULO = new DeviceRgb(245, 248, 255); // azul muy claro
    private static final DeviceRgb COLOR_FILA_PAR  = new DeviceRgb(235, 241, 252); // azul pálido
 
    /**
     * Exporta los datos de un alumno junto con su lista de matrículas a un PDF.
     *
     * @param alumno     El alumno a imprimir.
     * @param matriculas Lista de matrículas que pertenecen a ese alumno.
     * @param rutaSalida Ruta del archivo PDF que se va a crear, p. ej. "alumno_12.pdf"
     */
    public static void exportarAlumno(Alumno alumno, List<Matricula> matriculas, String rutaSalida) {
 
        try {
            PdfWriter  writer  = new PdfWriter(rutaSalida);
            PdfDocument pdf    = new PdfDocument(writer);
            Document   doc    = new Document(pdf);
            doc.setMargins(40, 50, 40, 50);
 
            // ── 1. Cabecera ──────────────────────────────────────────────────
            Paragraph titulo = new Paragraph("FICHA DEL ALUMNO")
                    .setFontSize(20)
                    .setBold()
                    .setFontColor(ColorConstants.WHITE)
                    .setBackgroundColor(COLOR_CABECERA)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(10);
            doc.add(titulo);
 
            Paragraph fecha = new Paragraph("Fecha de impresión: " + LocalDate.now())
                    .setFontSize(9)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.RIGHT);
            doc.add(fecha);
 
            doc.add(new Paragraph(" "));  // espacio
 
            // ── 2. Datos del alumno ──────────────────────────────────────────
            doc.add(seccionTitulo("Datos Personales"));
 
            Table tablaAlumno = new Table(UnitValue.createPercentArray(new float[]{35, 65}))
                    .setWidth(UnitValue.createPercentValue(100));
 
            agregarFila(tablaAlumno, "ID",                String.valueOf(alumno.getID()),         false);
            agregarFila(tablaAlumno, "Nombre completo",   capitalizar(alumno.getNombre()) + " " +
                                                          capitalizar(alumno.getApellido()),       true);
            agregarFila(tablaAlumno, "DNI",               alumno.getDNI(),                        false);
            agregarFila(tablaAlumno, "Email",             alumno.getEmail(),                      true);
            agregarFila(tablaAlumno, "Teléfono",          alumno.getTeléfono(),                   false);
            agregarFila(tablaAlumno, "Año de nacimiento", String.valueOf(alumno.getFecha_Nacimiento()), true);
            agregarFila(tablaAlumno, "Ciclo formativo",   capitalizar(alumno.getCiclo_formativo()), false);
            agregarFila(tablaAlumno, "Curso",             String.valueOf(alumno.getCurso()) + "º", true);
            agregarFila(tablaAlumno, "Estado",            capitalizar(alumno.getEstado()),         false);
 
            doc.add(tablaAlumno);
            doc.add(new Paragraph(" "));
 
            // ── 3. Matrículas ────────────────────────────────────────────────
            doc.add(seccionTitulo("Matrículas"));
 
            if (matriculas == null || matriculas.isEmpty()) {
                doc.add(new Paragraph("Este alumno no tiene matrículas registradas.")
                        .setFontSize(11)
                        .setItalic()
                        .setFontColor(ColorConstants.GRAY));
            } else {
                Table tablaMatriculas = new Table(
                        UnitValue.createPercentArray(new float[]{10, 15, 20, 22, 18, 15}))
                        .setWidth(UnitValue.createPercentValue(100));
 
                // Cabecera de tabla
                String[] columnas = {"ID Mat.", "ID Alumno", "ID Módulo", "Convocatoria", "Calificación", "Año eval."};
                for (String col : columnas) {
                    tablaMatriculas.addHeaderCell(
                            new Cell().add(new Paragraph(col).setBold().setFontSize(10)
                                    .setFontColor(ColorConstants.WHITE))
                                    .setBackgroundColor(COLOR_CABECERA)
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setPadding(5)
                    );
                }
 
                // Filas de datos
                for (int i = 0; i < matriculas.size(); i++) {
                    Matricula m = matriculas.get(i);
                    boolean   par = (i % 2 == 0);
 
                    agregarCeldaTabla(tablaMatriculas, String.valueOf(m.getID_Matricula()),   par);
                    agregarCeldaTabla(tablaMatriculas, String.valueOf(m.getID_Alumno()),      par);
                    agregarCeldaTabla(tablaMatriculas, String.valueOf(m.getID_Modulo()),      par);
                    agregarCeldaTabla(tablaMatriculas, capitalizar(m.getConvocatoria()),      par);
                    agregarCeldaTabla(tablaMatriculas, m.getCalificación(),                   par);
                    agregarCeldaTabla(tablaMatriculas, String.valueOf(m.getFecha_Evaluación()), par);
                }
 
                doc.add(tablaMatriculas);
            }
 
            // ── 4. Pie de página ─────────────────────────────────────────────
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Documento generado automáticamente por el sistema de gestión.")
                    .setFontSize(8)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorderTop(new SolidBorder(ColorConstants.LIGHT_GRAY, 1))
                    .setPaddingTop(6));
 
            doc.close();
            System.out.println("[PDF] Archivo generado correctamente: " + rutaSalida);
 
        } catch (IOException e) {
            System.err.println("[PDF ERROR] No se pudo generar el PDF: " + e.getMessage());
        }
    }
 
    // ─── Métodos auxiliares ──────────────────────────────────────────────────
 
    /** Párrafo con fondo azul claro para los títulos de sección. */
    private static Paragraph seccionTitulo(String texto) {
        return new Paragraph(texto)
                .setFontSize(13)
                .setBold()
                .setFontColor(COLOR_CABECERA)
                .setBackgroundColor(COLOR_SUBTITULO)
                .setPadding(5)
                .setBorderLeft(new SolidBorder(COLOR_CABECERA, 4));
    }
 
    /** Añade una fila etiqueta/valor a la tabla de datos del alumno. */
    private static void agregarFila(Table tabla, String etiqueta, String valor, boolean sombreado) {
        DeviceRgb fondo = sombreado ? COLOR_FILA_PAR : null;
 
        Cell celdaEtiqueta = new Cell()
                .add(new Paragraph(etiqueta).setBold().setFontSize(10))
                .setPadding(5);
        Cell celdaValor = new Cell()
                .add(new Paragraph(valor).setFontSize(10))
                .setPadding(5);
 
        if (fondo != null) {
            celdaEtiqueta.setBackgroundColor(fondo);
            celdaValor.setBackgroundColor(fondo);
        }
        tabla.addCell(celdaEtiqueta);
        tabla.addCell(celdaValor);
    }
 
    /** Añade una celda centrada a la tabla de matrículas. */
    private static void agregarCeldaTabla(Table tabla, String texto, boolean filaPar) {
        Cell celda = new Cell()
                .add(new Paragraph(texto).setFontSize(10))
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(4);
        if (filaPar) {
            celda.setBackgroundColor(COLOR_FILA_PAR);
        }
        tabla.addCell(celda);
    }
 
    /** Pone la primera letra en mayúscula. */
    private static String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        return Character.toUpperCase(texto.charAt(0)) + texto.substring(1);
    }
 
    // ─── Ejemplo de uso ──────────────────────────────────────────────────────
 
    /**
     * Ejemplo de uso desde Main o desde el menú de Funciones.java:
     *
     *   // Obtener el alumno y sus matrículas desde los gestores
     *   Alumno alumno = gestorAlumnos.buscarPorID(idAlumno);
     *   List<Matricula> matriculas = gestorMatriculas.obtenerPorAlumno(idAlumno);
     *
     *   // Generar el PDF
     *   ExportadorPDF.exportarAlumno(alumno, matriculas, "alumno_" + idAlumno + ".pdf");
     */
}