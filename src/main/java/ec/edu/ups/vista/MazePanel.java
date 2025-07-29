package ec.edu.ups.vista;

import ec.edu.ups.controlador.MazeController;
import ec.edu.ups.models.Cell;
import ec.edu.ups.models.CellState;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de Swing que representa visualmente el laberinto como una cuadrícula de botones.
 * Cada botón en el panel corresponde a una celda en el modelo de datos del laberinto.
 *
 * @author Cristian Moscoso
 */
public class MazePanel extends JPanel {
    /**
     * El número de filas en el laberinto.
     */
    private final int rows;
    /**
     * El número de columnas en el laberinto.
     */
    private final int cols;
    /**
     * La matriz de celdas que representa el estado lógico del laberinto.
     */
    private final Cell[][] cells;
    /**
     * La matriz de botones que representa la vista del laberinto.
     */
    private final JButton[][] buttons;
    /**
     * El controlador que maneja las interacciones del usuario en este panel.
     */
    private MazeController controller;

    /**
     * Construye un nuevo panel de laberinto con las dimensiones especificadas.
     *
     * @param paramInt1 El número de filas.
     * @param paramInt2 El número de columnas.
     */
    public MazePanel(int paramInt1, int paramInt2) {
        this.rows = paramInt1;
        this.cols = paramInt2;
        this.cells = new Cell[paramInt1][paramInt2];
        this.buttons = new JButton[paramInt1][paramInt2];
        setLayout(new GridLayout(paramInt1, paramInt2));
        initGrid();
    }

    /**
     * Asigna el controlador que gestionará los eventos de este panel.
     *
     * @param paramMazeController El controlador a asignar.
     */
    public void setController(MazeController paramMazeController) {
        this.controller = paramMazeController;
    }

    /**
     * Inicializa la cuadrícula de celdas y botones.
     * Crea cada celda y su botón correspondiente, y configura el listener para los clics.
     */
    private void initGrid() {
        for (byte b = 0; b < this.rows; b++) {
            for (byte b1 = 0; b1 < this.cols; b1++) {
                Cell cell = new Cell(b, b1);
                JButton jButton = new JButton();
                jButton.setBackground(Color.WHITE);
                jButton.setOpaque(true);
                jButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                final byte rowCoord = b;
                final byte colCoord = b1;

                // Cuando se hace clic en un botón, notifica al controlador.
                jButton.addActionListener(paramActionEvent -> {
                    if (this.controller != null)
                        this.controller.onCellClicked(rowCoord, colCoord);
                });
                add(jButton);
                this.cells[b][b1] = cell;
                this.buttons[b][b1] = jButton;
            }
        }
    }

    /**
     * Restablece el color de las celdas que fueron marcadas como visitadas o parte del camino,
     * devolviéndolas a su estado visual 'EMPTY' (blanco).
     * No afecta a los muros, inicio o fin.
     */
    public void limpiarCeldasVisitadas() {
        for (byte b = 0; b < this.rows; b++) {
            for (byte b1 = 0; b1 < this.cols; b1++) {
                Cell cell = this.cells[b][b1];
                if (cell.state != CellState.WALL && cell.state != CellState.START && cell.state != CellState.END) {
                    cell.state = CellState.EMPTY;
                    this.buttons[b][b1].setBackground(Color.WHITE);
                }
            }
        }
    }

    /**
     * Obtiene la matriz de celdas que representa el modelo de datos del laberinto.
     *
     * @return La matriz 2D de celdas.
     */
    public Cell[][] getCells() {
        return this.cells;
    }

    /**
     * Obtiene un botón específico de la cuadrícula en la posición dada.
     *
     * @param paramInt1 La fila del botón.
     * @param paramInt2 La columna del botón.
     * @return El JButton en la posición especificada.
     */
    public JButton getButton(int paramInt1, int paramInt2) {
        return this.buttons[paramInt1][paramInt2];
    }
}