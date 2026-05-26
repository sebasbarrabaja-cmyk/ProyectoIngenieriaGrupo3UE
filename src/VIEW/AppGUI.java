package VIEW;

import CONTROLLER.CRUD.GestorAlumnos;
import CONTROLLER.CRUD.GestorMatriculas;
import CONTROLLER.CRUD.GestorModulos;
import CONTROLLER.Estadisticas;
import MODEL.Alumno;
import MODEL.Matricula;
import MODEL.Modulo_Profesional;
import PERSISTENCE.JSON_Persistencia;
import PERSISTENCE.CSV_Export;
import PERSISTENCE.PDF_Export;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Interfaz gráfica principal de la aplicación.
 * Lanzar con: AppGUI.iniciar(gestorAlumnos, gestorModulos, gestorMatriculas, json)
 */
public class AppGUI {

    // ── Paleta de colores ─────────────────────────────────────────────────────
    private static final Color C_BG        = new Color(0xEFF3FB);
    private static final Color C_PRIMARY   = new Color(0x1A73E8);  // azul tipo Google (del tutorial de YouTube)
    private static final Color C_PRIMARY_D = new Color(0x1558B0);
    private static final Color C_SIDEBAR   = new Color(0x2C3E50);  // el clasico de los tutoriales de Swing
    private static final Color C_SIDEBAR_H = new Color(0x3D5166);
    private static final Color C_WHITE     = Color.WHITE;
    private static final Color C_TEXT      = new Color(0x1A1A2E);
    private static final Color C_SUBTEXT   = new Color(0x6C757D);
    private static final Color C_DANGER    = new Color(0xC0392B);
    private static final Color C_SUCCESS   = new Color(0x1E8449);
    private static final Color C_BORDER    = new Color(0xCED4DA);
    private static final Color C_ROW_ALT   = new Color(0xF0F5FF);
    private static final Color C_HEADER_BG = new Color(0xF8FAFF);

    // ── Campos de instancia ───────────────────────────────────────────────────
    private final GestorAlumnos    gestorAlumnos;
    private final GestorModulos    gestorModulos;
    private final GestorMatriculas gestorMatriculas;
    private final JSON_Persistencia json;
    private final Login            login;
    private final Estadisticas     estadisticas;
    private final CSV_Export       csvExport;

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // ── Constructor privado ───────────────────────────────────────────────────
    private AppGUI(GestorAlumnos ga, GestorModulos gm, GestorMatriculas gmat, JSON_Persistencia j) {
        this.gestorAlumnos    = ga;
        this.gestorModulos    = gm;
        this.gestorMatriculas = gmat;
        this.json             = j;
        this.login            = new Login(ga);
        this.estadisticas     = new Estadisticas(gmat);
        this.csvExport        = new CSV_Export(ga, gm, gmat);
    }

    // ── Punto de entrada público ──────────────────────────────────────────────
    public static void iniciar(GestorAlumnos ga, GestorModulos gm,
                               GestorMatriculas gmat, JSON_Persistencia j) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new AppGUI(ga, gm, gmat, j).buildAndShow();
        });
    }

    // ── Construcción de la ventana ────────────────────────────────────────────
    private void buildAndShow() {
        frame = new JFrame("Centro Educativo – Gestión de Alumnos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 700);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel  = new JPanel(cardLayout);
        cardPanel.add(buildLoginPanel(),  "login");
        cardPanel.add(buildMainPanel(),   "main");

        frame.setContentPane(cardPanel);
        frame.setVisible(true);
        cardLayout.show(cardPanel, "login");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  PANEL DE LOGIN
    // ═══════════════════════════════════════════════════════════════════════
    private JPanel buildLoginPanel() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(C_SIDEBAR);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(C_WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(40, 50, 40, 50)
        ));
        card.setMaximumSize(new Dimension(400, 600));

        // Logo / título
        JLabel icon  = new JLabel("🎓");
        icon.setFont(new Font("Dialog", Font.PLAIN, 48));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = styledLabel("Centro Educativo", 22, Font.BOLD, C_TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = styledLabel("Sistema de Gestión", 13, Font.PLAIN, C_SUBTEXT);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Selector de rol
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        rolePanel.setBackground(C_BG);
        rolePanel.setBorder(new LineBorder(C_BORDER, 1, true));
        rolePanel.setMaximumSize(new Dimension(300, 42));

        JToggleButton btnSec = roleButton("Secretaría", true);
        JToggleButton btnAlm = roleButton("Alumno",     false);
        ButtonGroup   bg     = new ButtonGroup();
        bg.add(btnSec); bg.add(btnAlm);
        rolePanel.add(btnSec); rolePanel.add(btnAlm);

        // Campos de secretaria
        JPanel secPanel = new JPanel();
        secPanel.setLayout(new BoxLayout(secPanel, BoxLayout.Y_AXIS));
        secPanel.setBackground(C_WHITE);
        secPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lUser = fieldLabel("Usuario");
        JTextField tfUser = styledField("");
        JLabel lPass = fieldLabel("Contraseña");
        JPasswordField tfPass = new JPasswordField("");
        stylePasswordField(tfPass);

        secPanel.add(lUser); secPanel.add(Box.createVerticalStrut(4));
        secPanel.add(tfUser); secPanel.add(Box.createVerticalStrut(12));
        secPanel.add(lPass); secPanel.add(Box.createVerticalStrut(4));
        secPanel.add(tfPass);

        // Campos de alumno
        JPanel almPanel = new JPanel();
        almPanel.setLayout(new BoxLayout(almPanel, BoxLayout.Y_AXIS));
        almPanel.setBackground(C_WHITE);
        almPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        almPanel.setVisible(false);

        JLabel lDNI = fieldLabel("DNI del alumno");
        JTextField tfDNI = styledField("p.ej. 12345678Z");
        almPanel.add(lDNI); almPanel.add(Box.createVerticalStrut(4));
        almPanel.add(tfDNI);

        // Alternar paneles según rol
        btnSec.addActionListener(e -> { secPanel.setVisible(true);  almPanel.setVisible(false); });
        btnAlm.addActionListener(e -> { secPanel.setVisible(false); almPanel.setVisible(true);  });

        // Mensaje de error
        JLabel lblError = styledLabel("", 12, Font.PLAIN, C_DANGER);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón de acceso
        JButton btnLogin = primaryButton("Acceder");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(300, 44));

        btnLogin.addActionListener(e -> {
            lblError.setText("");
            if (btnSec.isSelected()) {
                String u = tfUser.getText().trim();
                String p = new String(tfPass.getPassword()).trim();
                if (login.loginSecretaria(u, p)) {
                    showMain();
                } else {
                    lblError.setText("Usuario o contraseña incorrectos");
                }
            } else {
                String dni = tfDNI.getText().trim();
                if (login.loginAlumno(dni)) {
                    showMain();
                } else {
                    lblError.setText("DNI no encontrado en el sistema");
                }
            }
        });

        // Tecla Enter en los campos
        ActionListener doLogin = e -> btnLogin.doClick();
        tfUser.addActionListener(doLogin);
        tfPass.addActionListener(doLogin);
        tfDNI.addActionListener(doLogin);

        // Componer card
        card.add(icon);
        card.add(Box.createVerticalStrut(8));
        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(28));
        card.add(rolePanel);
        card.add(Box.createVerticalStrut(24));
        card.add(secPanel);
        card.add(almPanel);
        card.add(Box.createVerticalStrut(12));
        card.add(lblError);
        card.add(Box.createVerticalStrut(20));
        card.add(btnLogin);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(C_SIDEBAR);
        wrapper.add(card);
        return wrapper;
    }

    private void showMain() {
        // Reconstruir la parte principal para reflejar el rol actual
        cardPanel.remove(cardPanel.getComponent(1));
        cardPanel.add(buildMainPanel(), "main", 1);
        cardLayout.show(cardPanel, "main");
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  PANEL PRINCIPAL (sidebar + contenido)
    // ═══════════════════════════════════════════════════════════════════════
    private JPanel buildMainPanel() {
        JPanel root = new JPanel(new BorderLayout());

        // ── Sidebar izquierdo ──
        JPanel sidebar = new JPanel();
        sidebar.setBackground(C_SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel appName = styledLabel("EduGest", 18, Font.BOLD, C_WHITE);
        appName.setBorder(new EmptyBorder(0, 20, 20, 0));
        appName.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(appName);

        // Info de sesión
        JLabel sesionLabel = styledLabel(login.getDescripcionSesion(), 11, Font.PLAIN, new Color(0xAABBCC));
        sesionLabel.setBorder(new EmptyBorder(0, 20, 16, 8));
        sesionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(sesionLabel);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(new Color(0x2E3F62));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(12));

        // ── Panel de contenido (CardLayout) ──
        CardLayout contentCards = new CardLayout();
        JPanel contentPanel = new JPanel(contentCards);
        contentPanel.setBackground(C_BG);

        // Páginas
        JPanel pageAlumnos     = buildAlumnosPanel();
        JPanel pageModulos     = buildModulosPanel();
        JPanel pageMatriculas  = buildMatriculasPanel();
        JPanel pageStats       = buildEstadisticasPanel();

        contentPanel.add(pageAlumnos,    "alumnos");
        contentPanel.add(pageModulos,    "modulos");
        contentPanel.add(pageMatriculas, "matriculas");
        contentPanel.add(pageStats,      "estadisticas");

        // Botones de navegación
        String[] secciones = {"👤  Alumnos", "📚  Módulos", "📋  Matrículas", "📊  Estadísticas"};
        String[] cards     = {"alumnos", "modulos", "matriculas", "estadisticas"};
        JButton[] navBtns  = new JButton[secciones.length];

        for (int i = 0; i < secciones.length; i++) {
            final String card = cards[i];
            JButton btn = navButton(secciones[i]);
            navBtns[i] = btn;
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(4));
            btn.addActionListener(e -> {
                contentCards.show(contentPanel, card);
                for (JButton b : navBtns) b.setBackground(C_SIDEBAR);
                btn.setBackground(C_SIDEBAR_H);
            });
        }

        // Ocultar gestión si es alumno
        if (login.esAlumno()) {
            navBtns[1].setEnabled(false);  // módulos
            navBtns[2].setEnabled(false);  // matrículas
            navBtns[3].setEnabled(false);  // estadísticas
        }

        // Seleccionar primera pestaña por defecto
        navBtns[0].setBackground(C_SIDEBAR_H);
        contentCards.show(contentPanel, "alumnos");

        // Botón cerrar sesión al fondo
        sidebar.add(Box.createVerticalGlue());
        JButton btnLogout = navButton("🚪  Cerrar sesión");
        btnLogout.setForeground(new Color(0xFF9090));
        btnLogout.addActionListener(e -> {
            login.cerrarSesion();
            json.guardarTodo();
            cardLayout.show(cardPanel, "login");
        });
        sidebar.add(btnLogout);

        root.add(sidebar,       BorderLayout.WEST);
        root.add(contentPanel,  BorderLayout.CENTER);
        return root;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  PANEL ALUMNOS
    // ═══════════════════════════════════════════════════════════════════════
    private JPanel buildAlumnosPanel() {
        JPanel root = contentPage("Alumnos");
        JPanel top = (JPanel) root.getComponent(0);

        // Si es alumno, solo muestra sus propios datos
        if (login.esAlumno()) {
            Alumno a = login.getAlumnoLogueado();
            JTextArea area = new JTextArea(a.toString());
            area.setEditable(false);
            area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
            area.setBorder(new EmptyBorder(16, 16, 16, 16));
            area.setBackground(C_WHITE);
            JScrollPane sp = new JScrollPane(area);
            sp.setBorder(new LineBorder(C_BORDER));
            root.add(sp, BorderLayout.CENTER);
            return root;
        }

        // Barra de herramientas
        JTextField tfSearch = searchField("Buscar por nombre o DNI…");
        JButton btnNew  = primaryButton("+ Nuevo alumno");
        JButton btnRef  = ghostButton("⟳ Actualizar");
        top.add(tfSearch, BorderLayout.CENTER);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);
        btns.add(btnRef); btns.add(btnNew);
        top.add(btns, BorderLayout.EAST);

        // Tabla
        String[] cols = {"ID", "Nombre", "Apellido", "DNI", "Email", "Ciclo", "Curso", "Estado"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new LineBorder(C_BORDER, 1));
        root.add(sp, BorderLayout.CENTER);

        // Barra inferior con botones de acción
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        bottomBar.setBackground(C_BG);
        JButton btnEdit    = ghostButton("✏ Editar");
        JButton btnDel     = dangerButton("🗑 Eliminar");
        JButton btnCsv     = ghostButton("⬇ Exportar CSV");
        JButton btnPdf     = ghostButton("🖨 Imprimir PDF");
        bottomBar.add(btnCsv); bottomBar.add(btnPdf);
        bottomBar.add(btnEdit); bottomBar.add(btnDel);
        root.add(bottomBar, BorderLayout.SOUTH);

        // Cargar datos
        Runnable refresh = () -> {
            model.setRowCount(0);
            String q = tfSearch.getText().trim().toLowerCase();
            for (Alumno a : gestorAlumnos.getListaAlumnos()) {
                if (q.isEmpty() || a.getNombre().contains(q)
                        || a.getApellido().contains(q)
                        || a.getDNI().toLowerCase().contains(q)) {
                    model.addRow(new Object[]{
                        a.getID(), cap(a.getNombre()), cap(a.getApellido()),
                        a.getDNI(), a.getEmail(),
                        a.getCiclo_formativo().toUpperCase(),
                        a.getCurso(), cap(a.getEstado())
                    });
                }
            }
        };
        refresh.run();

        tfSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { refresh.run(); }
        });
        btnRef.addActionListener(e -> refresh.run());

        btnNew.addActionListener(e -> {
            if (showAlumnoDialog(null)) { json.guardarTodo(); refresh.run(); }
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { showInfo("Selecciona un alumno para editar."); return; }
            int id = (int) model.getValueAt(row, 0);
            Alumno a = gestorAlumnos.buscarPorID(id);
            if (showAlumnoDialog(a)) { json.guardarTodo(); refresh.run(); }
        });

        btnDel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { showInfo("Selecciona un alumno para eliminar."); return; }
            int id = (int) model.getValueAt(row, 0);
            if (confirmDialog("¿Eliminar alumno #" + id + "?")) {
                gestorAlumnos.eliminar(id);
                json.guardarTodo();
                refresh.run();
            }
        });

        // ── Exportar CSV (todos los alumnos) ──────────────────────────────
        btnCsv.addActionListener(e -> {
            try {
                csvExport.exportarAlumnos();
                showInfo("CSV exportado correctamente en data/alumnos.csv");
            } catch (Exception ex) {
                showInfo("Error al exportar CSV: " + ex.getMessage());
            }
        });

        // ── Imprimir PDF (alumno seleccionado) ────────────────────────────
        btnPdf.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { showInfo("Selecciona un alumno para imprimir su ficha PDF."); return; }
            int id = (int) model.getValueAt(row, 0);
            Alumno a = gestorAlumnos.buscarPorID(id);
            java.util.List<Matricula> mats = new ArrayList<>();
            for (Matricula m : gestorMatriculas.getListaMatriculas())
                if (m.getID_Alumno() == id) mats.add(m);
            String nombreArchivo = "alumno_" + id + ".pdf";
            PDF_Export.exportarAlumno(a, mats, nombreArchivo);
            showInfo("PDF generado correctamente en download/" + nombreArchivo);
        });

        return root;
    }

    /** Diálogo para crear / editar un alumno. Devuelve true si se guardó. */
    private boolean showAlumnoDialog(Alumno alumno) {
        boolean esNuevo = (alumno == null);
        JDialog dialog = new JDialog(frame, esNuevo ? "Nuevo alumno" : "Editar alumno", true);
        dialog.setSize(500, 580);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(C_WHITE);
        form.setBorder(new EmptyBorder(24, 28, 16, 28));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 4, 4, 4);

        // -- Ciclos disponibles --
        String[] ciclosDisp = {
            "DAM", "DAW", "ASIR", "SMR", "FPII Administración",
            "FPII Comercio", "FPII Electrónica", "FPII Informática",
            "FPII Mecánica", "FPII Sanidad", "Otro"
        };

        JTextField tfNombre   = dialogField(esNuevo ? "" : cap(alumno.getNombre()));
        JTextField tfApellido = dialogField(esNuevo ? "" : cap(alumno.getApellido()));
        JTextField tfDNI      = dialogField(esNuevo ? "" : alumno.getDNI());
        JTextField tfEmail    = dialogField(esNuevo ? "" : alumno.getEmail());
        JTextField tfTelef    = dialogField(esNuevo ? "" : alumno.getTeléfono());

        // Campo fecha con formato automático DD/MM/AAAA
        JTextField tfFecha = dialogField(esNuevo ? "" : formatFechaNacimiento(alumno.getFecha_Nacimiento()));
        tfFecha.setToolTipText("Formato: DD/MM/AAAA");
        tfFecha.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                String actual = tfFecha.getText();
                int len = actual.length();
                // Solo dígitos y permitir borrar
                if (!Character.isDigit(ch) && ch != KeyEvent.VK_BACK_SPACE
                        && ch != KeyEvent.VK_DELETE) {
                    e.consume();
                    return;
                }
                if (Character.isDigit(ch)) {
                    if (len >= 10) { e.consume(); return; }
                    // Insertar barra automáticamente en posición 2 y 5
                    if (len == 2 || len == 5) {
                        tfFecha.setText(actual + "/");
                    }
                }
            }
        });

        // Límites en campos de texto
        // DNI: máx 9 caracteres
        tfDNI.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (tfDNI.getText().length() >= 9 && e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
                    e.consume();
            }
        });
        // Teléfono: solo dígitos, máx 9
        tfTelef.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!Character.isDigit(ch) && ch != KeyEvent.VK_BACK_SPACE) { e.consume(); return; }
                if (tfTelef.getText().length() >= 9 && ch != KeyEvent.VK_BACK_SPACE) e.consume();
            }
        });
        // Email: sin espacios
        tfEmail.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == ' ') e.consume();
            }
        });
        // Nombre y apellido: solo letras y espacios
        KeyAdapter soloLetras = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!Character.isLetter(ch) && ch != ' ' && ch != KeyEvent.VK_BACK_SPACE)
                    e.consume();
            }
        };
        tfNombre.addKeyListener(soloLetras);
        tfApellido.addKeyListener(soloLetras);

        // Ciclo: desplegable
        JComboBox<String> cbCiclo = new JComboBox<>(ciclosDisp);
        if (!esNuevo) {
            String cicloActual = alumno.getCiclo_formativo().toUpperCase();
            boolean found = false;
            for (String c2 : ciclosDisp) if (c2.equalsIgnoreCase(cicloActual)) { cbCiclo.setSelectedItem(c2); found = true; break; }
            if (!found) { cbCiclo.addItem(cicloActual); cbCiclo.setSelectedItem(cicloActual); }
        }

        JSpinner spCurso = new JSpinner(new SpinnerNumberModel(esNuevo ? 1 : alumno.getCurso(), 1, 4, 1));
        JComboBox<String> cbEstado = new JComboBox<>(new String[]{"matriculado", "baja", "graduado"});
        if (!esNuevo) cbEstado.setSelectedItem(alumno.getEstado().toLowerCase());

        Object[][] filas = {
            {"Nombre",          tfNombre},
            {"Apellido",        tfApellido},
            {"DNI",             tfDNI},
            {"Email",           tfEmail},
            {"Teléfono",        tfTelef},
            {"Fecha nacimiento",tfFecha},
            {"Ciclo",           cbCiclo},
            {"Curso",           spCurso},
            {"Estado",          cbEstado}
        };

        for (int i = 0; i < filas.length; i++) {
            c.gridx = 0; c.gridy = i; c.weightx = 0.35;
            form.add(formLabel((String) filas[i][0]), c);
            c.gridx = 1; c.weightx = 0.65;
            form.add((Component) filas[i][1], c);
        }

        JLabel lblErr = styledLabel("", 11, Font.PLAIN, C_DANGER);
        c.gridx = 0; c.gridy = filas.length; c.gridwidth = 2;
        form.add(lblErr, c);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        btns.setBackground(C_BG);
        btns.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDER));
        JButton btnCancel = ghostButton("Cancelar");
        JButton btnSave   = primaryButton("Guardar");
        btns.add(btnCancel); btns.add(btnSave);

        boolean[] saved = {false};

        btnSave.addActionListener(e -> {
            try {
                String nombre   = tfNombre.getText().trim();
                String apellido = tfApellido.getText().trim();
                String dni      = tfDNI.getText().trim().toUpperCase();
                String email    = tfEmail.getText().trim();
                String telef    = tfTelef.getText().trim();
                String fechaStr = tfFecha.getText().trim();
                String ciclo    = (String) cbCiclo.getSelectedItem();
                int    curso    = (int) spCurso.getValue();
                String estado   = (String) cbEstado.getSelectedItem();

                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || email.isEmpty()) {
                    lblErr.setText("Nombre, apellido, DNI y email son obligatorios.");
                    return;
                }
                if (!dni.matches("[0-9]{8}[A-Za-z]")) {
                    lblErr.setText("DNI no válido (ej: 12345678Z).");
                    return;
                }
                if (!email.contains("@") || !email.contains(".")) {
                    lblErr.setText("Email no válido.");
                    return;
                }
                if (!telef.isEmpty() && !telef.matches("[0-9]{9}")) {
                    lblErr.setText("Teléfono: 9 dígitos numéricos.");
                    return;
                }
                if (fechaStr.isEmpty()) {
                    lblErr.setText("La fecha de nacimiento es obligatoria.");
                    return;
                }
                if (!fechaStr.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    lblErr.setText("Fecha: formato DD/MM/AAAA.");
                    return;
                }
                int anyo = Integer.parseInt(fechaStr.substring(6));
                if (anyo < 1950 || anyo > 2015) {
                    lblErr.setText("Año de nacimiento no válido.");
                    return;
                }

                if (esNuevo) {
                    gestorAlumnos.crear(nombre, apellido, dni, email, telef, anyo, ciclo, curso, estado);
                } else {
                    gestorAlumnos.actualizar(alumno.getID(), nombre, apellido, dni, email, telef, anyo, ciclo, curso, estado);
                }
                saved[0] = true;
                dialog.dispose();
            } catch (Exception ex) {
                lblErr.setText(ex.getMessage());
            }
        });
        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btns, BorderLayout.SOUTH);
        dialog.setVisible(true);
        return saved[0];
    }

    /** Convierte un año entero (o fecha almacenada) en String DD/MM/AAAA para mostrar. */
    private String formatFechaNacimiento(int valor) {
        // Si el valor es solo un año (4 dígitos), mostrar vacío para que el usuario rellene
        if (valor >= 1900 && valor <= 2100) return "01/01/" + valor;
        return String.valueOf(valor);
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  PANEL MÓDULOS
    // ═══════════════════════════════════════════════════════════════════════
    private JPanel buildModulosPanel() {
        JPanel root = contentPage("Módulos Profesionales");
        JPanel top  = (JPanel) root.getComponent(0);

        JTextField tfSearch = searchField("Buscar por nombre o ciclo…");
        JButton btnNew = primaryButton("+ Nuevo módulo");
        JButton btnRef = ghostButton("⟳ Actualizar");
        top.add(tfSearch, BorderLayout.CENTER);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);
        btns.add(btnRef); btns.add(btnNew);
        top.add(btns, BorderLayout.EAST);

        String[] cols = {"ID", "Nombre", "Ciclo", "Horas", "Docente", "Curso"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(new LineBorder(C_BORDER, 1));
        root.add(sp, BorderLayout.CENTER);

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        bottomBar.setBackground(C_BG);
        JButton btnEdit    = ghostButton("✏ Editar");
        JButton btnDel     = dangerButton("🗑 Eliminar");
        JButton btnCsvMod  = ghostButton("⬇ Exportar CSV");
        bottomBar.add(btnCsvMod); bottomBar.add(btnEdit); bottomBar.add(btnDel);
        root.add(bottomBar, BorderLayout.SOUTH);

        Runnable refresh = () -> {
            model.setRowCount(0);
            String q = tfSearch.getText().trim().toLowerCase();
            for (Modulo_Profesional m : gestorModulos.getListaModulos()) {
                if (q.isEmpty() || m.getNombre_Modulo().contains(q)
                        || m.getCiclo_Perteneciente().toLowerCase().contains(q)) {
                    model.addRow(new Object[]{
                        m.getID_Modulo(), cap(m.getNombre_Modulo()),
                        m.getCiclo_Perteneciente().toUpperCase(),
                        m.getHoras_t(), cap(m.getNombre_Docente()), m.getCurso_Imp()
                    });
                }
            }
        };
        refresh.run();

        tfSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { refresh.run(); }
        });
        btnRef.addActionListener(e -> refresh.run());

        btnNew.addActionListener(e -> {
            if (showModuloDialog(null)) { json.guardarTodo(); refresh.run(); }
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { showInfo("Selecciona un módulo para editar."); return; }
            int id = (int) model.getValueAt(row, 0);
            Modulo_Profesional m = gestorModulos.buscarPorID(id);
            if (showModuloDialog(m)) { json.guardarTodo(); refresh.run(); }
        });

        btnDel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { showInfo("Selecciona un módulo para eliminar."); return; }
            int id = (int) model.getValueAt(row, 0);
            if (confirmDialog("¿Eliminar módulo #" + id + "?")) {
                gestorModulos.eliminar(id);
                json.guardarTodo();
                refresh.run();
            }
        });

        // ── Exportar CSV módulos ──────────────────────────────────────────
        btnCsvMod.addActionListener(e -> {
            try {
                csvExport.exportarModulos();
                showInfo("CSV exportado correctamente en data/modulos.csv");
            } catch (Exception ex) {
                showInfo("Error al exportar CSV: " + ex.getMessage());
            }
        });

        return root;
    }

    private boolean showModuloDialog(Modulo_Profesional mod) {
        boolean esNuevo = (mod == null);
        JDialog dialog = new JDialog(frame, esNuevo ? "Nuevo módulo" : "Editar módulo", true);
        dialog.setSize(440, 400);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(C_WHITE);
        form.setBorder(new EmptyBorder(24, 28, 16, 28));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 4, 4, 4);

        String[] ciclosDisp = {
            "DAM", "DAW", "ASIR", "SMR", "FPII Administración",
            "FPII Comercio", "FPII Electrónica", "FPII Informática",
            "FPII Mecánica", "FPII Sanidad", "Otro"
        };

        JTextField tfNombre  = dialogField(esNuevo ? "" : cap(mod.getNombre_Modulo()));
        JComboBox<String> cbCiclo = new JComboBox<>(ciclosDisp);
        if (!esNuevo) {
            String cicloActual = mod.getCiclo_Perteneciente().toUpperCase();
            boolean found = false;
            for (String c2 : ciclosDisp) if (c2.equalsIgnoreCase(cicloActual)) { cbCiclo.setSelectedItem(c2); found = true; break; }
            if (!found) { cbCiclo.addItem(cicloActual); cbCiclo.setSelectedItem(cicloActual); }
        }
        JTextField tfHoras   = dialogField(esNuevo ? "" : String.valueOf(mod.getHoras_t()));
        JTextField tfDocente = dialogField(esNuevo ? "" : cap(mod.getNombre_Docente()));
        JSpinner   spCurso   = new JSpinner(new SpinnerNumberModel(esNuevo ? 1 : mod.getCurso_Imp(), 1, 4, 1));

        // Solo números en horas
        tfHoras.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
                    e.consume();
            }
        });
        // Solo letras y espacios en docente
        tfDocente.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!Character.isLetter(ch) && ch != ' ' && ch != KeyEvent.VK_BACK_SPACE)
                    e.consume();
            }
        });

        Object[][] filas = {
            {"Nombre",  tfNombre}, {"Ciclo",   cbCiclo},
            {"Horas",   tfHoras},  {"Docente", tfDocente}, {"Curso", spCurso}
        };
        for (int i = 0; i < filas.length; i++) {
            c.gridx = 0; c.gridy = i; c.weightx = 0.35;
            form.add(formLabel((String) filas[i][0]), c);
            c.gridx = 1; c.weightx = 0.65;
            form.add((Component) filas[i][1], c);
        }
        JLabel lblErr = styledLabel("", 11, Font.PLAIN, C_DANGER);
        c.gridx = 0; c.gridy = filas.length; c.gridwidth = 2;
        form.add(lblErr, c);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        btns.setBackground(C_BG);
        btns.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDER));
        JButton btnCancel = ghostButton("Cancelar");
        JButton btnSave   = primaryButton("Guardar");
        btns.add(btnCancel); btns.add(btnSave);

        boolean[] saved = {false};
        btnSave.addActionListener(e -> {
            try {
                String nombre  = tfNombre.getText().trim();
                String ciclo   = (String) cbCiclo.getSelectedItem();
                String horasStr = tfHoras.getText().trim();
                String docente = tfDocente.getText().trim();
                int    curso   = (int) spCurso.getValue();

                if (nombre.isEmpty()) { lblErr.setText("El nombre es obligatorio."); return; }
                if (horasStr.isEmpty()) { lblErr.setText("Las horas son obligatorias."); return; }
                int horas = Integer.parseInt(horasStr);
                if (horas <= 0 || horas > 9999) { lblErr.setText("Horas debe ser un valor entre 1 y 9999."); return; }

                if (esNuevo) {
                    gestorModulos.crear(nombre, ciclo, horas, docente, curso);
                } else {
                    gestorModulos.actualizar(mod.getID_Modulo(), nombre, ciclo, horas, docente, curso);
                }
                saved[0] = true;
                dialog.dispose();
            } catch (NumberFormatException ex) {
                lblErr.setText("Las horas deben ser un número entero.");
            } catch (Exception ex) {
                lblErr.setText(ex.getMessage());
            }
        });
        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btns, BorderLayout.SOUTH);
        dialog.setVisible(true);
        return saved[0];
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  PANEL MATRÍCULAS
    // ═══════════════════════════════════════════════════════════════════════
    private JPanel buildMatriculasPanel() {
        JPanel root = contentPage("Matrículas");
        JPanel top  = (JPanel) root.getComponent(0);

        JButton btnNew    = primaryButton("+ Nueva matrícula");
        JButton btnRef    = ghostButton("⟳ Actualizar");
        JButton btnCsvMat = ghostButton("⬇ Exportar CSV");
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);
        btns.add(btnCsvMat); btns.add(btnRef); btns.add(btnNew);
        top.add(btns, BorderLayout.EAST);

        // Panel con scroll que mostrará alumnos + sus matrículas
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(C_BG);
        listPanel.setBorder(new EmptyBorder(12, 16, 12, 16));
        JScrollPane sp = new JScrollPane(listPanel);
        sp.setBorder(new LineBorder(C_BORDER, 1));
        sp.getVerticalScrollBar().setUnitIncrement(16);
        root.add(sp, BorderLayout.CENTER);

        // Usamos un array para que el lambda pueda llamarse a sí mismo (Java no permite
        // capturar una variable que aún no está inicializada en el propio lambda)
        final Runnable[] refreshRef = {null};
        refreshRef[0] = () -> {
            listPanel.removeAll();
            java.util.LinkedHashMap<Integer, java.util.List<Matricula>> porAlumno = new java.util.LinkedHashMap<>();
            for (Matricula m : gestorMatriculas.getListaMatriculas()) {
                porAlumno.computeIfAbsent(m.getID_Alumno(), k -> new ArrayList<>()).add(m);
            }
            if (porAlumno.isEmpty()) {
                JLabel vacio = styledLabel("No hay matrículas registradas.", 13, Font.ITALIC, C_SUBTEXT);
                vacio.setBorder(new EmptyBorder(24, 8, 8, 8));
                listPanel.add(vacio);
            }
            for (java.util.Map.Entry<Integer, java.util.List<Matricula>> entry : porAlumno.entrySet()) {
                Alumno al = gestorAlumnos.buscarPorID(entry.getKey());
                String nombreAl = al != null
                    ? "#" + al.getID() + "  " + cap(al.getNombre()) + " " + cap(al.getApellido())
                    : "Alumno #" + entry.getKey();

                // Cabecera del alumno
                JPanel alumnoHeader = new JPanel(new BorderLayout());
                alumnoHeader.setBackground(new Color(0xE8EEF8));
                alumnoHeader.setBorder(new CompoundBorder(
                    new MatteBorder(0, 4, 0, 0, C_PRIMARY),
                    new EmptyBorder(8, 12, 8, 12)
                ));
                alumnoHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                alumnoHeader.add(styledLabel(nombreAl, 13, Font.BOLD, C_TEXT), BorderLayout.WEST);
                listPanel.add(alumnoHeader);
                listPanel.add(Box.createVerticalStrut(2));

                // Sub-tabla con matrículas del alumno
                String[] cols = {"ID Mat.", "Módulo", "Convocatoria", "Calificación", "Año eval."};
                DefaultTableModel subModel = new DefaultTableModel(cols, 0) {
                    public boolean isCellEditable(int r, int c) { return false; }
                };
                for (Matricula m : entry.getValue()) {
                    Modulo_Profesional mo = gestorModulos.buscarPorID(m.getID_Modulo());
                    String nombreMo = mo != null ? cap(mo.getNombre_Modulo()) : "#" + m.getID_Modulo();
                    subModel.addRow(new Object[]{
                        m.getID_Matricula(), nombreMo,
                        cap(m.getConvocatoria()), m.getCalificación(), m.getFecha_Evaluación()
                    });
                }
                JTable subTable = styledTable(subModel);
                subTable.setRowHeight(30);
                int tableHeight = subTable.getRowHeight() * subModel.getRowCount()
                    + subTable.getTableHeader().getPreferredSize().height + 4;
                JScrollPane subSp = new JScrollPane(subTable);
                subSp.setBorder(new MatteBorder(0, 0, 1, 0, C_BORDER));
                subSp.setPreferredSize(new Dimension(0, tableHeight));
                subSp.setMaximumSize(new Dimension(Integer.MAX_VALUE, tableHeight));
                subSp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                subSp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

                // Botones editar/eliminar por fila
                JPanel rowBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 4));
                rowBtns.setBackground(C_BG);
                rowBtns.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
                JButton bEdit = ghostButton("✏ Editar");
                JButton bDel  = dangerButton("🗑 Eliminar");
                bEdit.setFont(new Font("Arial", Font.PLAIN, 11));
                bDel.setFont(new Font("Arial", Font.PLAIN, 11));
                rowBtns.add(bEdit); rowBtns.add(bDel);

                bEdit.addActionListener(ev -> {
                    int row = subTable.getSelectedRow();
                    if (row < 0) { showInfo("Selecciona una matrícula para editar."); return; }
                    int id = (int) subModel.getValueAt(row, 0);
                    Matricula m = gestorMatriculas.buscarPorID(id);
                    if (showMatriculaDialog(m)) { json.guardarTodo(); refreshRef[0].run(); }
                });
                bDel.addActionListener(ev -> {
                    int row = subTable.getSelectedRow();
                    if (row < 0) { showInfo("Selecciona una matrícula para eliminar."); return; }
                    int id = (int) subModel.getValueAt(row, 0);
                    if (confirmDialog("¿Eliminar matrícula #" + id + "?")) {
                        gestorMatriculas.eliminar(id);
                        json.guardarTodo();
                        refreshRef[0].run();
                    }
                });

                listPanel.add(subSp);
                listPanel.add(rowBtns);
                listPanel.add(Box.createVerticalStrut(12));
            }
            listPanel.revalidate();
            listPanel.repaint();
        };
        final Runnable refresh = refreshRef[0];
        refresh.run();

        btnRef.addActionListener(e -> refresh.run());
        btnNew.addActionListener(e -> {
            if (showMatriculaDialog(null)) { json.guardarTodo(); refresh.run(); }
        });

        // ── Exportar CSV matrículas ───────────────────────────────────────
        btnCsvMat.addActionListener(e -> {
            try {
                csvExport.exportarMatriculas();
                showInfo("CSV exportado correctamente en data/matriculas.csv");
            } catch (Exception ex) {
                showInfo("Error al exportar CSV: " + ex.getMessage());
            }
        });

        return root;
    }

    private boolean showMatriculaDialog(Matricula mat) {
        boolean esNuevo = (mat == null);
        JDialog dialog = new JDialog(frame, esNuevo ? "Nueva matrícula" : "Editar matrícula", true);
        dialog.setSize(400, 320);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(C_WHITE);
        form.setBorder(new EmptyBorder(24, 28, 16, 28));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 4, 4, 4);

        // Construir listas de IDs disponibles
        ArrayList<Alumno> alumnos = gestorAlumnos.getListaAlumnos();
        ArrayList<Modulo_Profesional> modulos = gestorModulos.getListaModulos();

        String[] alumnosArr = alumnos.stream()
            .map(a -> "#" + a.getID() + " " + cap(a.getNombre()) + " " + cap(a.getApellido()))
            .toArray(String[]::new);
        String[] modulosArr = modulos.stream()
            .map(m -> "#" + m.getID_Modulo() + " " + cap(m.getNombre_Modulo()))
            .toArray(String[]::new);

        JComboBox<String> cbAlumno = new JComboBox<>(alumnosArr.length > 0 ? alumnosArr : new String[]{"(sin alumnos)"});
        JComboBox<String> cbModulo = new JComboBox<>(modulosArr.length > 0 ? modulosArr : new String[]{"(sin módulos)"});
        JComboBox<String> cbConv   = new JComboBox<>(new String[]{"ordinaria", "extraordinaria"});
        JTextField tfCalif = dialogField(esNuevo ? "" : mat.getCalificación());
        JTextField tfAnyo  = dialogField(esNuevo ? "" : String.valueOf(mat.getFecha_Evaluación()));

        // Solo números y punto/coma en calificación (0-10)
        tfCalif.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                String actual = tfCalif.getText();
                if (!Character.isDigit(ch) && ch != '.' && ch != ',' && ch != KeyEvent.VK_BACK_SPACE)
                    e.consume();
                if (actual.length() >= 5 && ch != KeyEvent.VK_BACK_SPACE) e.consume();
            }
        });
        // Solo 4 dígitos en año
        tfAnyo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
                    e.consume();
                if (tfAnyo.getText().length() >= 4 && e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
                    e.consume();
            }
        });

        if (!esNuevo) {
            for (int i = 0; i < alumnos.size(); i++)
                if (alumnos.get(i).getID() == mat.getID_Alumno()) cbAlumno.setSelectedIndex(i);
            for (int i = 0; i < modulos.size(); i++)
                if (modulos.get(i).getID_Modulo() == mat.getID_Modulo()) cbModulo.setSelectedIndex(i);
            cbConv.setSelectedItem(mat.getConvocatoria().toLowerCase());
            cbAlumno.setEnabled(false);
            cbModulo.setEnabled(false);
        }

        Object[][] filas = {
            {"Alumno",       cbAlumno}, {"Módulo",       cbModulo},
            {"Convocatoria", cbConv},   {"Calificación", tfCalif},
            {"Año eval.",    tfAnyo}
        };
        for (int i = 0; i < filas.length; i++) {
            c.gridx = 0; c.gridy = i; c.weightx = 0.35;
            form.add(formLabel((String) filas[i][0]), c);
            c.gridx = 1; c.weightx = 0.65;
            form.add((Component) filas[i][1], c);
        }
        JLabel lblErr = styledLabel("", 11, Font.PLAIN, C_DANGER);
        c.gridx = 0; c.gridy = filas.length; c.gridwidth = 2;
        form.add(lblErr, c);

        JPanel bBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        bBar.setBackground(C_BG);
        bBar.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDER));
        JButton btnCancel = ghostButton("Cancelar");
        JButton btnSave   = primaryButton("Guardar");
        bBar.add(btnCancel); bBar.add(btnSave);

        boolean[] saved = {false};
        btnSave.addActionListener(e -> {
            try {
                String conv  = (String) cbConv.getSelectedItem();
                String calif = tfCalif.getText().trim();
                int anyo     = Integer.parseInt(tfAnyo.getText().trim());
                if (esNuevo) {
                    int idAl = alumnos.get(cbAlumno.getSelectedIndex()).getID();
                    int idMo = modulos.get(cbModulo.getSelectedIndex()).getID_Modulo();
                    gestorMatriculas.crear(idAl, idMo, conv, calif, anyo);
                } else {
                    gestorMatriculas.actualizar(mat.getID_Matricula(), conv, calif, anyo);
                }
                saved[0] = true;
                dialog.dispose();
            } catch (NumberFormatException ex) {
                lblErr.setText("El año debe ser un número.");
            } catch (Exception ex) {
                lblErr.setText(ex.getMessage());
            }
        });
        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(bBar, BorderLayout.SOUTH);
        dialog.setVisible(true);
        return saved[0];
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  PANEL ESTADÍSTICAS
    // ═══════════════════════════════════════════════════════════════════════
    private JPanel buildEstadisticasPanel() {
        JPanel root = contentPage("Estadísticas");
        JPanel top  = (JPanel) root.getComponent(0);

        JComboBox<String> cbModulo = new JComboBox<>();
        for (Modulo_Profesional m : gestorModulos.getListaModulos())
            cbModulo.addItem("#" + m.getID_Modulo() + " " + cap(m.getNombre_Modulo()));
        cbModulo.setPreferredSize(new Dimension(240, 32));
        JPanel topLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        topLeft.setOpaque(false);
        topLeft.add(new JLabel("Módulo:")); topLeft.add(cbModulo);
        top.add(topLeft, BorderLayout.WEST);

        JPanel cards = new JPanel(new GridLayout(1, 3, 16, 0));
        cards.setBackground(C_BG);
        cards.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel lblMedia   = bigStatLabel("—");
        JLabel lblApro    = bigStatLabel("—");
        JLabel lblSus     = bigStatLabel("—");
        cards.add(statCard("📈 Nota media",    lblMedia,  C_PRIMARY));
        cards.add(statCard("✅ Aprobados",      lblApro,   C_SUCCESS));
        cards.add(statCard("❌ Suspensos",      lblSus,    C_DANGER));

        root.add(cards, BorderLayout.CENTER);

        // Calcular automáticamente al cambiar el módulo seleccionado
        Runnable calcular = () -> {
            if (gestorModulos.getListaModulos().isEmpty()) return;
            int idx = cbModulo.getSelectedIndex();
            if (idx < 0) return;
            int idMod = gestorModulos.getListaModulos().get(idx).getID_Modulo();
            lblMedia.setText(estadisticas.notaMedia(idMod) + "");
            lblApro.setText(estadisticas.porcentajeAprobados(idMod) + "%");
            lblSus.setText(estadisticas.porcentajeSuspensos(idMod) + "%");
        };
        cbModulo.addActionListener(e -> calcular.run());
        // Calcular al cargar
        calcular.run();

        return root;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  HELPERS DE LAYOUT / ESTILOS
    // ═══════════════════════════════════════════════════════════════════════

    /** Crea el esqueleto de una página con cabecera y BorderLayout. */
    private JPanel contentPage(String titulo) {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(C_BG);

        // Cabecera
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setBackground(C_HEADER_BG);
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 4, 1, 0, C_PRIMARY),   // borde izquierdo azul - quedamos muy pros
            new EmptyBorder(14, 20, 14, 24)
        ));
        JLabel lblTit = styledLabel(titulo, 17, Font.BOLD, C_TEXT);
        header.add(lblTit, BorderLayout.WEST);
        root.add(header, BorderLayout.NORTH);
        return root;
    }

    private JLabel styledLabel(String text, int size, int style, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", style, size));
        l.setForeground(color);
        return l;
    }

    private JLabel fieldLabel(String text) {
        JLabel l = styledLabel(text, 12, Font.PLAIN, C_SUBTEXT);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel formLabel(String text) {
        return styledLabel(text + ":", 12, Font.PLAIN, C_SUBTEXT);
    }

    private JTextField styledField(String placeholder) {
        JTextField tf = new JTextField(18);
        tf.setText(placeholder);
        tf.setFont(new Font("Dialog", Font.PLAIN, 13));
        tf.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        tf.setMaximumSize(new Dimension(300, 38));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return tf;
    }

    private void stylePasswordField(JPasswordField pf) {
        pf.setFont(new Font("Dialog", Font.PLAIN, 13));
        pf.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        pf.setMaximumSize(new Dimension(300, 38));
        pf.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JTextField searchField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Dialog", Font.PLAIN, 13));
        tf.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        tf.setToolTipText(placeholder);
        tf.setPreferredSize(new Dimension(220, 32));
        return tf;
    }

    private JTextField dialogField(String value) {
        JTextField tf = new JTextField(value);
        tf.setFont(new Font("Dialog", Font.PLAIN, 13));
        tf.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    private JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.setBackground(C_PRIMARY);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);              // sin esto en Windows el fondo queda blanco!!
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setBorder(new EmptyBorder(9, 20, 9, 20));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(C_PRIMARY_D); }
            public void mouseExited(MouseEvent e)  { b.setBackground(C_PRIMARY); }
        });
        return b;
    }

    private JButton ghostButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.PLAIN, 13));
        b.setBackground(C_WHITE);
        b.setForeground(C_TEXT);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(7, 16, 7, 16)
        ));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton dangerButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.PLAIN, 13));
        b.setBackground(new Color(0xFADEDE));
        b.setForeground(C_DANGER);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorder(new CompoundBorder(
            new LineBorder(new Color(0xE8AAAA), 1, true),
            new EmptyBorder(7, 16, 7, 16)
        ));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton navButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.PLAIN, 13));
        b.setForeground(new Color(0xC8D6E8));
        b.setBackground(C_SIDEBAR);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setBorder(new EmptyBorder(10, 20, 10, 20));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        return b;
    }

    private JToggleButton roleButton(String text, boolean selected) {
        JToggleButton b = new JToggleButton(text, selected);
        b.setFont(new Font("Arial", Font.PLAIN, 12));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setBackground(selected ? C_PRIMARY : C_BG);
        b.setForeground(selected ? Color.WHITE : C_TEXT);
        b.setBorder(new EmptyBorder(8, 20, 8, 20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addItemListener(e -> {
            b.setBackground(b.isSelected() ? C_PRIMARY : C_BG);
            b.setForeground(b.isSelected() ? Color.WHITE : C_TEXT);
        });
        return b;
    }

    private JTable styledTable(DefaultTableModel model) {
        JTable t = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row))
                    c.setBackground(row % 2 == 0 ? C_WHITE : C_ROW_ALT);
                return c;
            }
        };
        t.setFont(new Font("Dialog", Font.PLAIN, 13));
        t.setRowHeight(34);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setSelectionBackground(new Color(0xEBF0FF));
        t.setSelectionForeground(C_TEXT);
        t.setFillsViewportHeight(true);
        JTableHeader h = t.getTableHeader();
        h.setFont(new Font("Dialog", Font.BOLD, 12));
        h.setBackground(new Color(0xF1F5F9));
        h.setForeground(C_SUBTEXT);
        h.setBorder(new MatteBorder(0, 0, 1, 0, C_BORDER));
        h.setReorderingAllowed(false);
        // Centrar columna ID y ajustar su ancho
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        if (t.getColumnCount() > 0) {
            t.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            t.getColumnModel().getColumn(0).setPreferredWidth(60);
            t.getColumnModel().getColumn(0).setMaxWidth(80);
            t.getColumnModel().getColumn(0).setMinWidth(50);
            // Centrar también el encabezado de ID
            ((DefaultTableCellRenderer) t.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.LEFT);
        }
        return t;
    }

    private JPanel statCard(String label, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(C_WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(C_BORDER, 1, true),
            new EmptyBorder(24, 24, 24, 24)
        ));
        JLabel lbl = styledLabel(label, 13, Font.PLAIN, C_SUBTEXT);
        valueLabel.setForeground(accentColor);
        card.add(lbl,        BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JLabel bigStatLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Dialog", Font.BOLD, 36));
        return l;
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean confirmDialog(String msg) {
        return JOptionPane.showConfirmDialog(frame, msg, "Confirmar",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /** Capitaliza la primera letra de cada palabra. */
    private static String cap(String s) {
        if (s == null || s.isEmpty()) return s;
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty())
                sb.append(Character.toUpperCase(w.charAt(0)))
                  .append(w.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();
    }
}
