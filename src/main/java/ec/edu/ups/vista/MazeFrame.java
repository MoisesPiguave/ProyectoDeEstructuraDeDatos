package ec.edu.ups.vista;
import ec.edu.ups.controlador.MazeController;
import ec.edu.ups.dao.AlgorithmResultDAO;
import ec.edu.ups.dao.DaoImpl.AlgorithmResultDAOFile;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import ec.edu.ups.models.AlgorithmResult;
import ec.edu.ups.models.Cell;
import ec.edu.ups.models.CellState;
import ec.edu.ups.models.SolveResults;
import ec.edu.ups.solver.MazeSolver;
import ec.edu.ups.solver.solverImpl.*;

/**
 * La ventana principal de la aplicación del laberinto.
 * Orquesta todos los componentes de la interfaz de usuario, incluyendo el panel del laberinto,
 * los botones de control y el menú.
 *
 * @author Cristian Moscoso
 */
public class MazeFrame extends JFrame {
    /** El panel que muestra la cuadrícula del laberinto. */
    private final MazePanel mazePanel;
    /** El controlador que maneja la lógica de la interacción del usuario. */
    private final MazeController controller;
    /** El JComboBox para seleccionar el algoritmo de resolución. */
    private final JComboBox<String> algorithmSelector;
    /** El botón para iniciar la resolución del laberinto. */
    private final JButton solveButton;
    /** El DAO para guardar y leer los resultados de los algoritmos. */
    private final AlgorithmResultDAO resultDAO;
    /** Un mapa para asociar cada estado de celda con un color para la visualización. */
    private static final Map<CellState, Color> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put(CellState.EMPTY, Color.LIGHT_GRAY);
        COLOR_MAP.put(CellState.WALL, Color.BLACK);
        COLOR_MAP.put(CellState.START, Color.GREEN);
        COLOR_MAP.put(CellState.END, Color.RED);
        COLOR_MAP.put(CellState.PATH, Color.BLUE);
    }

    /**
     * Construye la ventana principal de la aplicación.
     *
     * @param paramInt1 El número de filas para el laberinto.
     * @param paramInt2 El número de columnas para el laberinto.
     */
    public MazeFrame(int paramInt1, int paramInt2) {
        this.resultDAO = new AlgorithmResultDAOFile("results.csv");
        setTitle("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Inicializa el panel y el controlador
        this.mazePanel = new MazePanel(paramInt1, paramInt2);
        this.controller = new MazeController(this.mazePanel);
        this.mazePanel.setController(this.controller);
        add(this.mazePanel, BorderLayout.CENTER);

        // Panel de control superior (Set Start, Set End, etc.)
        JPanel jPanel1 = new JPanel();
        JButton jButton1 = new JButton("Set Start");
        JButton jButton2 = new JButton("Set End");
        JButton jButton3 = new JButton("Toggle Wall");
        jButton1.addActionListener(paramActionEvent -> this.controller.setMode(MazeController.Mode.START));
        jButton2.addActionListener(paramActionEvent -> this.controller.setMode(MazeController.Mode.END));
        jButton3.addActionListener(paramActionEvent -> this.controller.setMode(MazeController.Mode.WALL));
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        jPanel1.add(jButton3);
        add(jPanel1, BorderLayout.NORTH);

        // Panel de control inferior (Selector de algoritmo, Resolver, etc.)
        String[] arrayOfString = {"Recursivo", "Recursivo Completo", "Recursivo Completo BT", "BFS", "DFS"};
        this.algorithmSelector = new JComboBox<>(arrayOfString);
        this.solveButton = new JButton("Resolver");
        JPanel jPanel2 = new JPanel();
        jPanel2.add(new JLabel("Algoritmo:"));
        jPanel2.add(this.algorithmSelector);
        jPanel2.add(this.solveButton);
        add(jPanel2, BorderLayout.SOUTH);

        // Listener para el botón de resolver
        this.solveButton.addActionListener(paramActionEvent -> {
            SolveResults solveResults = resolverYObtenerResultados();
            if (solveResults != null)
                animarVisitadas(solveResults.visitadas, solveResults.camino);
        });

        // Botón y menú
        JButton jButton4 = new JButton("Limpiar");
        jButton4.addActionListener(e -> this.mazePanel.limpiarCeldasVisitadas());
        jPanel2.add(jButton4);

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu1 = new JMenu("Archivo");
        JMenuItem jMenuItem1 = new JMenuItem("Nuevo laberinto");
        jMenuItem1.addActionListener(paramActionEvent -> reiniciarLaberinto());
        jMenu1.add(jMenuItem1);

        JMenuItem jMenuItem3 = new JMenuItem("Ver resultados");
        jMenuItem3.addActionListener(paramActionEvent -> {
            ResultadosDialog resultadosDialog = new ResultadosDialog(this, this.resultDAO);
            resultadosDialog.setVisible(true);
        });
        jMenu1.add(jMenuItem3);
        jMenuBar.add(jMenu1);

        JMenu jMenu2 = new JMenu("Ayuda");
        JMenuItem jMenuItem2 = new JMenuItem("Acerca de");
        jMenuItem2.addActionListener(paramActionEvent -> mostrarAcercaDe());
        jMenu2.add(jMenuItem2);
        jMenuBar.add(jMenu2);

        setJMenuBar(jMenuBar);
        setVisible(true);
    }

    /**
     * Orquesta el proceso de resolución: selecciona el algoritmo, mide el tiempo,
     * ejecuta la búsqueda, guarda los resultados y los devuelve.
     *
     * @return El objeto SolveResults con el camino y las celdas visitadas.
     */
    private SolveResults resolverYObtenerResultados() {
        MazeSolver selectedSolver = null;
        Cell cell1 = this.controller.getStartCell();
        Cell cell2 = this.controller.getEndCell();
        if (cell1 == null || cell2 == null) {
            JOptionPane.showMessageDialog(this, "Seleccione primero el origen y destino.");
            return null;
        }
        this.mazePanel.limpiarCeldasVisitadas();
        String str = (String) this.algorithmSelector.getSelectedItem();

        switch (str) {
            case "Recursivo":           selectedSolver = new MazeSolverRecursivo();         break;
            case "Recursivo Completo":  selectedSolver = new MazeSolverRecursivoCompleto(); break;
            case "Recursivo Completo BT": selectedSolver = new MazeSolverRecursivoCompletoBT(); break;
            case "DFS":                 selectedSolver = new MazeSolverDFS();               break;
            case "BFS":                 selectedSolver = new MazeSolverBFS();               break;
            default:                    selectedSolver = new MazeSolverRecursivo();         break;
        }

        if (selectedSolver == null) {
            JOptionPane.showMessageDialog(this, "Error: No se pudo inicializar el algoritmo.");
            return null;
        }

        long l1 = System.nanoTime();
        SolveResults solveResults = selectedSolver.getPath(this.mazePanel.getCells(), cell1, cell2);
        long l2 = System.nanoTime();

        if (solveResults != null && !solveResults.camino.isEmpty()) {
            AlgorithmResult algorithmResult = new AlgorithmResult(str, solveResults.camino.size(), l2 - l1);
            this.resultDAO.save(algorithmResult);
        }
        return solveResults;
    }

    /**
     * Pinta una celda de un color específico en el hilo de despacho de eventos de Swing.
     *
     * @param paramCell La celda a pintar.
     * @param paramCellState El nuevo estado (y color) para la celda.
     */
    private void paintCell(Cell paramCell, CellState paramCellState) {
        JButton jButton = this.mazePanel.getButton(paramCell.row, paramCell.col);
        jButton.setBackground(COLOR_MAP.getOrDefault(paramCellState, Color.WHITE));
    }

    /**
     * Anima el proceso de resolución en un hilo separado para no congelar la interfaz.
     * Primero pinta las celdas visitadas y luego el camino final.
     *
     * @param paramList1 La lista de celdas visitadas.
     * @param paramList2 La lista de celdas del camino final.
     */
    private void animarVisitadas(List<Cell> paramList1, List<Cell> paramList2) {
        new Thread(() -> {
            try {
                // Animar celdas visitadas
                for (Cell cell : paramList1) {
                    if (cell.state == CellState.EMPTY) {
                        SwingUtilities.invokeLater(() -> paintCell(cell, CellState.EMPTY));
                        Thread.sleep(30L);
                    }
                }
                // Animar camino final
                if (paramList2.isEmpty()) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "No se encontró un camino."));
                    return;
                }
                for (Cell cell : paramList2) {
                    if (cell.state != CellState.START && cell.state != CellState.END) {
                        SwingUtilities.invokeLater(() -> paintCell(cell, CellState.PATH));
                        Thread.sleep(80L);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Solicita al usuario las nuevas dimensiones para el laberinto.
     *
     * @return un array de int con {filas, columnas} o null si el usuario cancela.
     */
    public int[] solicitarDimensiones() {
        try {
            String str1 = JOptionPane.showInputDialog("Ingrese numero de filas 🙂:");
            if (str1 == null) return null;
            String str2 = JOptionPane.showInputDialog("Ingrese numero de columnas 🙂:");
            if (str2 == null) return null;
            int i = Integer.parseInt(str1.trim());
            int j = Integer.parseInt(str2.trim());
            if (i <= 4 || j <= 4) {
                JOptionPane.showMessageDialog(null, "Debe ingresar valores mayores a 4 😁.");
                return null;
            }
            return new int[]{i, j};
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "Debe ingresar numero valido 😁");
            return null;
        }
    }

    /**
     * Cierra la ventana actual y crea una nueva instancia de MazeFrame con nuevas dimensiones.
     */
    private void reiniciarLaberinto() {
        int[] arrayOfInt = solicitarDimensiones();
        if (arrayOfInt == null) return;
        dispose();
        SwingUtilities.invokeLater(() -> new MazeFrame(arrayOfInt[0], arrayOfInt[1]));
    }

    /**
     * Muestra un cuadro de diálogo con información "Acerca de" la aplicación y sus autores.
     */
    private void mostrarAcercaDe() {
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
        JLabel jLabel1 = new JLabel("Desarrollado por: Moises Piguave, Cristian Moscoso, Sebastian Calderon, Pablo Feijon");
        jLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel1.add(jLabel1);
        jPanel1.add(Box.createVerticalStrut(10));
        JPanel jPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jLabel2 = new JLabel("<html><a href=''>MoisesPiguave/ProyectoFinalDeEstructuraDeDatos</a></html>");
        jLabel2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent param1MouseEvent) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/MoisesPiguave/ProyectoFinalDeEstructuraDeDatos"));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        jPanel2.add(jLabel2);
        jPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel1.add(jPanel2);
        JOptionPane.showMessageDialog(this, jPanel1, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }
}