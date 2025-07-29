package ec.edu.ups.vista;
import ec.edu.ups.dao.AlgorithmResultDAO;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ec.edu.ups.models.AlgorithmResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Un cuadro de diálogo que muestra los resultados de rendimiento guardados de los algoritmos.
 * Presenta los datos en una tabla y permite visualizarlos en un gráfico de líneas.
 *
 * @author Cristian Moscoso
 */
public class ResultadosDialog extends JDialog {
    /**
     * El modelo de datos para la JTable que muestra los resultados.
     */
    private final DefaultTableModel model;
    /**
     * El objeto de acceso a datos para cargar y limpiar los resultados del archivo.
     */
    private final AlgorithmResultDAO resultDAO;
    /**
     * La lista de resultados cargada desde el DAO.
     */
    private List<AlgorithmResult> results;

    /**
     * Construye el cuadro de diálogo de resultados.
     *
     * @param paramJFrame El frame padre sobre el cual se mostrará este diálogo.
     * @param paramAlgorithmResultDAO El DAO para acceder a los datos de los resultados.
     */
    public ResultadosDialog(JFrame paramJFrame, AlgorithmResultDAO paramAlgorithmResultDAO) {
        super(paramJFrame, "Resultados Guardados", true);
        this.resultDAO = paramAlgorithmResultDAO;
        setLayout(new BorderLayout());

        // Configuración de la tabla
        this.model = new DefaultTableModel(new String[]{"Algoritmo", "Celdas Camino", "Tiempo (ns)"}, 0);
        JTable jTable = new JTable(this.model);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        add(jScrollPane, BorderLayout.CENTER);

        loadData(); // Carga los datos al iniciar

        // Configuración de los botones
        JPanel jPanel = new JPanel();
        JButton jButton1 = new JButton("Limpiar Resultados");
        jButton1.addActionListener(paramActionEvent -> {
            int i = JOptionPane.showConfirmDialog(this, "¿Deseas borrar todos los resultados?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                this.resultDAO.clear(); // Limpia los datos desde el DAO
                this.model.setRowCount(0); // Limpia la tabla en la vista
            }
        });

        JButton jButton2 = new JButton("Graficar Resultados");
        jButton2.addActionListener(paramActionEvent -> mostrarGrafica());
        jPanel.add(jButton1);
        jPanel.add(jButton2);
        add(jPanel, BorderLayout.SOUTH);

        setSize(500, 400);
        setLocationRelativeTo(paramJFrame);
    }

    /**
     * Carga los datos de los resultados desde el archivo (usando el DAO) y los añade a la tabla.
     */
    private void loadData() {
        this.results = this.resultDAO.findAll();
        for (AlgorithmResult algorithmResult : this.results) {
            this.model.addRow(new Object[]{
                    algorithmResult.getAlgorithmName(),
                    algorithmResult.getPathSize(),
                    algorithmResult.getTimeNs()
            });
        }
    }

    /**
     * Crea y muestra un gráfico de líneas con los tiempos de ejecución de los algoritmos
     * utilizando la librería JFreeChart.
     */
    private void mostrarGrafica() {
        if (this.results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay datos para graficar.");
            return;
        }
        // Crea el conjunto de datos para el gráfico
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
        for (AlgorithmResult algorithmResult : this.results) {
            defaultCategoryDataset.addValue(algorithmResult.getTimeNs(), "Tiempo(ns)", algorithmResult.getAlgorithmName());
        }

        // Crea el gráfico
        JFreeChart jFreeChart = ChartFactory.createLineChart("Tiempos de Ejecución por Algoritmo", "Algoritmo", "Tiempo (ns)", defaultCategoryDataset, PlotOrientation.VERTICAL, true, true, false);

        // Muestra el gráfico en un nuevo diálogo
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        JDialog jDialog = new JDialog(this, "Gráficos", true);
        jDialog.setContentPane(chartPanel);
        jDialog.setSize(600, 400);
        jDialog.setLocationRelativeTo(this);
        jDialog.setVisible(true);
    }
}