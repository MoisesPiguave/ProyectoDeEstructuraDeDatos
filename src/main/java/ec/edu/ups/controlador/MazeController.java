package ec.edu.ups.controlador;

import java.awt.Color;
import javax.swing.JButton;


import ec.edu.ups.vista.MazePanel;
import ec.edu.ups.models.Cell;
import ec.edu.ups.models.CellState;

/**
 * Controlador del laberinto que maneja la lógica de interacción entre
 * el usuario y la interfaz gráfica. Permite seleccionar celdas como inicio,
 * fin o muro, y actualiza visualmente el estado de cada celda.
 *
 * @author Pablo Feijo
 */
public class MazeController {

    private final MazePanel panel;
    private Cell startCell;
    private Cell endCell;
    private Mode currentMode = Mode.WALL;

    /**
     * Define los modos posibles de interacción con el laberinto.
     */
    public enum Mode {
        START, END, WALL;
    }

    /**
     * Constructor de MazeController.
     *
     * @param paramMazePanel El panel de la interfaz gráfica que representa el laberinto.
     */
    public MazeController(MazePanel paramMazePanel) {
        this.panel = paramMazePanel;
        paramMazePanel.setController(this);
    }

    /**
     * Establece el modo actual de interacción.
     *
     * @param paramMode Modo a establecer (START, END, WALL).
     */
    public void setMode(Mode paramMode) {
        this.currentMode = paramMode;
    }

    /**
     * Ejecuta la acción correspondiente cuando una celda es clickeada,
     * según el modo de operación actual.
     *
     * @param paramInt1 Fila de la celda.
     * @param paramInt2 Columna de la celda.
     */
    public void onCellClicked(int paramInt1, int paramInt2) {
        switch (this.currentMode) {
            case START:
                setStartCell(paramInt1, paramInt2);
                break;
            case END:
                setEndCell(paramInt1, paramInt2);
                break;
            case WALL:
                toggleWall(paramInt1, paramInt2);
                break;
        }
    }

    /**
     * Versión alternativa del método para manejar clics en celdas,
     * conservada para compatibilidad con versiones anteriores.
     *
     * @param paramInt1 Fila de la celda.
     * @param paramInt2 Columna de la celda.
     */
    public void onCellClickedLegacy(int paramInt1, int paramInt2) {
        Cell cell = this.panel.getCells()[paramInt1][paramInt2];
        JButton jButton = this.panel.getButton(paramInt1, paramInt2);

        switch (this.currentMode) {
            case START:
                if (this.startCell != null)
                    this.panel.getButton(this.startCell.row, this.startCell.col).setBackground(Color.WHITE);
                this.startCell = cell;
                cell.state = CellState.START;
                jButton.setBackground(Color.GREEN);
                break;
            case END:
                if (this.endCell != null)
                    this.panel.getButton(this.endCell.row, this.endCell.col).setBackground(Color.WHITE);
                this.endCell = cell;
                cell.state = CellState.END;
                jButton.setBackground(Color.RED);
                break;
            case WALL:
                if (cell.state == CellState.WALL) {
                    cell.state = CellState.EMPTY;
                    jButton.setBackground(Color.WHITE);
                    break;
                }
                cell.state = CellState.WALL;
                jButton.setBackground(Color.BLACK);
                break;
        }
    }

    /**
     * Limpia las celdas visitadas del laberinto, restaurando su estado original.
     */
    public void limpiar() {
        this.panel.limpiarCeldasVisitadas();
    }

    /**
     * Obtiene la celda marcada como inicio.
     *
     * @return Celda de inicio.
     */
    public Cell getStartCell() {
        return this.startCell;
    }

    /**
     * Obtiene la celda marcada como fin.
     *
     * @return Celda de fin.
     */
    public Cell getEndCell() {
        return this.endCell;
    }

    /**
     * Establece una celda como fin del laberinto, actualizando su color.
     *
     * @param paramInt1 Fila de la celda.
     * @param paramInt2 Columna de la celda.
     */
    public void setEndCell(int paramInt1, int paramInt2) {
        Cell cell = this.panel.getCells()[paramInt1][paramInt2];
        JButton jButton = this.panel.getButton(paramInt1, paramInt2);
        if (this.endCell != null) {
            this.panel.getButton(this.endCell.row, this.endCell.col).setBackground(Color.WHITE);
            this.endCell.state = CellState.EMPTY;
        }
        this.endCell = cell;
        cell.state = CellState.END;
        jButton.setBackground(Color.RED);
    }

    /**
     * Establece una celda como inicio del laberinto, actualizando su color.
     *
     * @param paramInt1 Fila de la celda.
     * @param paramInt2 Columna de la celda.
     */
    public void setStartCell(int paramInt1, int paramInt2) {
        Cell cell = this.panel.getCells()[paramInt1][paramInt2];
        JButton jButton = this.panel.getButton(paramInt1, paramInt2);
        if (this.startCell != null) {
            this.panel.getButton(this.startCell.row, this.startCell.col).setBackground(Color.WHITE);
            this.startCell.state = CellState.EMPTY;
        }
        this.startCell = cell;
        cell.state = CellState.START;
        jButton.setBackground(Color.GREEN);
    }

    /**
     * Alterna el estado de una celda entre transitable (blanca) y muro (negro).
     *
     * @param paramInt1 Fila de la celda.
     * @param paramInt2 Columna de la celda.
     */
    public void toggleWall(int paramInt1, int paramInt2) {
        Cell cell = this.panel.getCells()[paramInt1][paramInt2];
        if (cell.state == CellState.EMPTY) {
            cell.state = CellState.WALL;
            this.panel.getButton(paramInt1, paramInt2).setBackground(Color.BLACK);
        } else if (cell.state == CellState.WALL) {
            cell.state = CellState.EMPTY;
            this.panel.getButton(paramInt1, paramInt2).setBackground(Color.WHITE);
        }
    }
}
