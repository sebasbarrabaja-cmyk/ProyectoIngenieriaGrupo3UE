package PERSISTENCE;

import com.google.gson.*;
import MODEL.Alumno;
import MODEL.Modulo_Profesional;
import MODEL.Matricula;
import CONTROLLER.CRUD.GestorAlumnos;
import CONTROLLER.CRUD.GestorModulos;
import CONTROLLER.CRUD.GestorMatriculas;

import java.io.*;
public class JSON_Persistencia {

    private static final String RUTA_ALUMNOS    = "data/alumnos.json";
    private static final String RUTA_MODULOS    = "data/modulos.json";
    private static final String RUTA_MATRICULAS = "data/matriculas.json";

    // Configuración del motor Gson con formato legible (Pretty Printing)
    private static final Gson gson = new GsonBuilder()
                                        .setPrettyPrinting()
                                        .create();

    private final GestorAlumnos    gestorAlumnos;
    private final GestorModulos    gestorModulos;
    private final GestorMatriculas gestorMatriculas;

    public JSON_Persistencia(GestorAlumnos gestorAlumnos, GestorModulos gestorModulos, GestorMatriculas gestorMatriculas) {
        this.gestorAlumnos    = gestorAlumnos;
        this.gestorModulos    = gestorModulos;
        this.gestorMatriculas = gestorMatriculas;
        crearDirectorioData();
    }

    private void crearDirectorioData() {
        File dir = new File("data");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("[JSON] Carpeta 'data/' creada.");
            }
        }
    }

    // ── Métodos de Salvaguarda (Guardar) ──────────────────────────────────────────

    public void guardarTodo() {
        guardarAlumnos();
        guardarModulos();
        guardarMatriculas();
        System.out.println("[JSON] Todos los datos han sido almacenados correctamente.");
    }

    public void guardarAlumnos() {
        try (Writer writer = new FileWriter(RUTA_ALUMNOS)) {
            // Gson serializa de manera automática la lista completa de objetos
            gson.toJson(gestorAlumnos.getListaAlumnos(), writer);
            System.out.println("[JSON] Alumnos guardados con éxito.");
        } catch (IOException e) {
            System.err.println("[JSON] Error al guardar alumnos: " + e.getMessage());
        }
    }

    public void guardarModulos() {
        try (Writer writer = new FileWriter(RUTA_MODULOS)) {
            gson.toJson(gestorModulos.getListaModulos(), writer);
            System.out.println("[JSON] Módulos guardados con éxito.");
        } catch (IOException e) {
            System.err.println("[JSON] Error al guardar módulos: " + e.getMessage());
        }
    }

    public void guardarMatriculas() {
        try (Writer writer = new FileWriter(RUTA_MATRICULAS)) {
            gson.toJson(gestorMatriculas.getListaMatriculas(), writer);
            System.out.println("[JSON] Matrículas guardadas con éxito.");
        } catch (IOException e) {
            System.err.println("[JSON] Error al guardar matrículas: " + e.getMessage());
        }
    }

    // ── Métodos de Recuperación (Cargar) ──────────────────────────────────────────

    public void cargarTodo() {
        cargarAlumnos();
        cargarModulos();
        cargarMatriculas();
        System.out.println("[JSON] Todos los datos han sido cargados correctamente.");
    }

    public void cargarAlumnos() {
        File archivo = new File(RUTA_ALUMNOS);
        if (!archivo.exists()) {
            System.out.println("[JSON] No se encontró 'alumnos.json'. Se inicia vacío.");
            return;
        }

        try (Reader reader = new FileReader(archivo)) {
            // Conversión del flujo de datos directamente a un arreglo de elementos JSON
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            int maxId = 0;

            for (JsonElement element : jsonArray) {
                // Reconstrucción del objeto Alumno mediante deserialización automatizada
                Alumno alumno = gson.fromJson(element, Alumno.class);
                gestorAlumnos.getListaAlumnos().add(alumno);

                if (alumno.getID() > maxId) {
                    maxId = alumno.getID();
                }
            }
            Alumno.sincronizarContador(maxId + 1);
            System.out.println("[JSON] Alumnos cargados (" + jsonArray.size() + " registros).");
        } catch (Exception e) {
            System.err.println("[JSON] Error al cargar el archivo de alumnos: " + e.getMessage());
        }
    }

    public void cargarModulos() {
        File archivo = new File(RUTA_MODULOS);
        if (!archivo.exists()) {
            System.out.println("[JSON] No se encontró 'modulos.json'. Se inicia vacío.");
            return;
        }

        try (Reader reader = new FileReader(archivo)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            int maxId = 0;

            for (JsonElement element : jsonArray) {
                Modulo_Profesional modulo = gson.fromJson(element, Modulo_Profesional.class);
                gestorModulos.getListaModulos().add(modulo);

                if (modulo.getID_Modulo() > maxId) {
                    maxId = modulo.getID_Modulo();
                }
            }
            Modulo_Profesional.sincronizarContador(maxId + 1);
            System.out.println("[JSON] Módulos cargados (" + jsonArray.size() + " registros).");
        } catch (Exception e) {
            System.err.println("[JSON] Error al cargar el archivo de módulos: " + e.getMessage());
        }
    }

    public void cargarMatriculas() {
        File archivo = new File(RUTA_MATRICULAS);
        if (!archivo.exists()) {
            System.out.println("[JSON] No se encontró 'matriculas.json'. Se inicia vacío.");
            return;
        }

        try (Reader reader = new FileReader(archivo)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            int maxId = 0;

            for (JsonElement element : jsonArray) {
                Matricula matricula = gson.fromJson(element, Matricula.class);
                gestorMatriculas.getListaMatriculas().add(matricula);

                if (matricula.getID_Matricula() > maxId) {
                    maxId = matricula.getID_Matricula();
                }
            }
            Matricula.sincronizarContador(maxId + 1);
            System.out.println("[JSON] Matrículas cargadas (" + jsonArray.size() + " registros).");
        } catch (Exception e) {
            System.err.println("[JSON] Error al cargar el archivo de matrículas: " + e.getMessage());
        }
    }
}