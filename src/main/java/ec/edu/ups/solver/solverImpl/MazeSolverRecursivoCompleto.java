package ec.edu.ups.solver.solverImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import ec.edu.ups.models.Cell;
import ec.edu.ups.models.CellState;
import ec.edu.ups.models.SolveResults;
import ec.edu.ups.solver.MazeSolver;

/**
 * `MazeSolverRecursivoCompleto` implementa la interfaz `MazeSolver` utilizando
 * un enfoque recursivo de búsqueda en profundidad (DFS).
 * Este algoritmo intenta encontrar un camino desde una celda de inicio a una
 * celda final en un laberinto dado.
 * A diferencia de BFS, no garantiza el camino más corto, pero sí encuentra un camino si existe.
 */
public class MazeSolverRecursivoCompleto  implements MazeSolver {
    // `visited` es un conjunto que almacena las celdas ya exploradas para evitar
    // bucles infinitos y revisar celdas innecesariamente.
    // Se usa `LinkedHashSet` para mantener el orden de visita.
    private Set<Cell> visited = new LinkedHashSet<>();

    // `camino` es una lista que construye el camino encontrado de la celda de
    // inicio a la celda final.
    private List<Cell> camino = new ArrayList<>();

    /**
     * Inicia la búsqueda de un camino desde la celda de inicio hasta la celda final
     * en el laberinto utilizando un algoritmo DFS recursivo.
     *
     * @param paramArrayOfCell Un arreglo 2D de objetos `Cell` que representa la cuadrícula del laberinto.
     * @param paramCell1 La `Cell` de inicio.
     * @param paramCell2 La `Cell` final.
     * @return Un objeto `SolveResults` que contiene dos listas de celdas:
     * 1. La lista `visitedCells`, que contiene todas las celdas exploradas durante el recorrido.
     * 2. La lista `path`, que contiene las celdas que forman el camino desde el inicio hasta el fin.
     * Si no se encuentra un camino, la lista `path` estará vacía.
     */
    public SolveResults getPath(Cell[][] paramArrayOfCell, Cell paramCell1, Cell paramCell2) {
        this.visited.clear(); // Limpiar las celdas visitadas.
        this.camino.clear(); // Limpiar el camino.
        findPath(paramArrayOfCell, paramCell1.row, paramCell1.col, paramCell2); // Iniciar la búsqueda recursiva.
        Collections.reverse(this.camino); // Invertir el camino para que esté en el orden correcto.
        return new SolveResults(new ArrayList<>(this.visited), new ArrayList<>(this.camino)); // Devolver el resultado.
    }

    /**
     * Método auxiliar recursivo para encontrar un camino en el laberinto.
     * Explora las celdas adyacentes de forma recursiva hasta que se encuentra la celda objetivo.
     *
     * @param paramArrayOfCell El arreglo 2D de celdas que representa el laberinto.
     * @param paramInt1 La coordenada de fila de la celda actual.
     * @param paramInt2 La coordenada de columna de la celda actual.
     * @param paramCell La `Cell` objetivo (celda final).
     * @return `true` si se encuentra un camino a la celda objetivo desde la celda actual, `false` en caso contrario.
     */
    private boolean findPath(Cell[][] paramArrayOfCell, int paramInt1, int paramInt2, Cell paramCell) {
        if (!isValid(paramArrayOfCell, paramInt1, paramInt2)) // Verificar si la celda es válida.
            return false;
        Cell cell = paramArrayOfCell[paramInt1][paramInt2]; // Obtener la celda actual.
        if (this.visited.contains(cell)) // Si la celda ya fue visitada.
            return false;
        this.visited.add(cell); // Marcar como visitada.
        this.camino.add(cell); // Añadir al camino.
        if (cell.equals(paramCell)) { // Si es la celda objetivo.
            this.camino.add(cell); // Añadir la celda objetivo al camino (puede duplicarla si ya se añadió antes).
            return true;
        }
        // Explorar recursivamente los vecinos.
        if (findPath(paramArrayOfCell, paramInt1 + 1, paramInt2, paramCell) || // Abajo
                findPath(paramArrayOfCell, paramInt1, paramInt2 + 1, paramCell) || // Derecha
                findPath(paramArrayOfCell, paramInt1 - 1, paramInt2, paramCell) || // Arriba
                findPath(paramArrayOfCell, paramInt1, paramInt2 - 1, paramCell)) // Izquierda
            return true; // Si se encuentra un camino.
        return false; // Si no se encuentra un camino desde esta celda.
    }

    /**
     * Método auxiliar para verificar si una celda dada por sus coordenadas es válida para la exploración.
     * Una celda es válida si está dentro de los límites del laberinto y no es una pared.
     *
     * @param paramArrayOfCell El arreglo 2D de celdas que representa el laberinto.
     * @param paramInt1 La coordenada de fila de la celda a verificar.
     * @param paramInt2 La coordenada de columna de la celda a verificar.
     * @return `true` si la celda es válida para la exploración, `false` en caso contrario.
     */
    private boolean isValid(Cell[][] paramArrayOfCell, int paramInt1, int paramInt2) {
        return (paramInt1 >= 0 && paramInt1 < paramArrayOfCell.length && // Verifica límites de fila.
                paramInt2 >= 0 && paramInt2 < (paramArrayOfCell[0]).length && // Verifica límites de columna.
                (paramArrayOfCell[paramInt1][paramInt2]).state != CellState.WALL); // Verifica que no sea una pared.
    }
}