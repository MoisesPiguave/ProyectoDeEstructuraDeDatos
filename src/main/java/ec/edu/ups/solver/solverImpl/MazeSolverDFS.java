package ec.edu.ups.solver.solverImpl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import ec.edu.ups.models.Cell;
import ec.edu.ups.models.CellState;
import ec.edu.ups.models.SolveResults;
import ec.edu.ups.solver.MazeSolver;

/**
 * `MazeSolverDFS` implementa la interfaz `MazeSolver` utilizando el algoritmo de Búsqueda en Profundidad (DFS).
 * Esta clase encuentra un camino desde una celda inicial hasta una celda final en un laberinto dado.
 * Es importante destacar que DFS no garantiza encontrar el camino más corto, solo un camino si existe.
 */
public class MazeSolverDFS implements MazeSolver {
    // `visitadas` es un conjunto para almacenar las celdas ya visitadas durante la exploración DFS.
    // Se utiliza `LinkedHashSet` para mantener el orden de inserción de las celdas visitadas.
    private Set<Cell> visitadas = new LinkedHashSet<>();

    // `camino` es una lista para almacenar las celdas que forman el camino encontrado desde el inicio hasta el fin.
    private List<Cell> camino = new ArrayList<>();

    /**
     * Inicia la búsqueda de un camino desde la celda de inicio hasta la celda final en el laberinto
     * utilizando el algoritmo DFS.
     *
     * @param paramArrayOfCell Un arreglo 2D de objetos `Cell` que representa la cuadrícula del laberinto.
     * @param paramCell1 La `Cell` de inicio.
     * @param paramCell2 La `Cell` final.
     * @return Un objeto `SolveResults` que contiene dos listas de celdas:
     * 1. La lista `visitedCells`, que contiene todas las celdas exploradas durante el recorrido DFS.
     * 2. La lista `path`, que contiene las celdas que forman el camino encontrado desde la celda de inicio hasta la celda final.
     * Si no se encuentra un camino, la lista `path` estará vacía.
     */
    public SolveResults getPath(Cell[][] paramArrayOfCell, Cell paramCell1, Cell paramCell2) {
        this.visitadas.clear(); // Limpiar las celdas visitadas.
        this.camino.clear(); // Limpiar el camino.
        dfs(paramArrayOfCell, paramCell1.row, paramCell1.col, paramCell2); // Iniciar DFS.
        return new SolveResults(new ArrayList<>(this.visitadas), new ArrayList<>(this.camino)); // Devolver resultado.
    }

    /**
     * Método auxiliar recursivo para realizar la Búsqueda en Profundidad (DFS).
     * Explora el laberinto en profundidad, marcando las celdas visitadas y construyendo el camino
     * si se encuentra la celda objetivo.
     *
     * @param paramArrayOfCell El arreglo 2D de celdas que representa el laberinto.
     * @param paramInt1 La coordenada de fila de la celda actual.
     * @param paramInt2 La coordenada de columna de la celda actual.
     * @param paramCell La `Cell` objetivo (celda final) que se busca.
     * @return `true` si se encontró un camino a la celda objetivo desde la celda actual, `false` en caso contrario.
     */
    private boolean dfs(Cell[][] paramArrayOfCell, int paramInt1, int paramInt2, Cell paramCell) {
        if (!isValid(paramArrayOfCell, paramInt1, paramInt2)) // Si la celda no es válida.
            return false;
        Cell cell = paramArrayOfCell[paramInt1][paramInt2]; // Obtener la celda actual.
        if (this.visitadas.contains(cell)) // Si ya fue visitada.
            return false;
        this.visitadas.add(cell); // Marcar como visitada.
        if (cell.equals(paramCell)) { // Si es la celda objetivo.
            this.camino.add(cell); // Añadir al camino.
            return true;
        }
        // Explorar vecinos recursivamente.
        if (dfs(paramArrayOfCell, paramInt1 + 1, paramInt2, paramCell) || // Abajo
                dfs(paramArrayOfCell, paramInt1 - 1, paramInt2, paramCell) || // Arriba
                dfs(paramArrayOfCell, paramInt1, paramInt2 + 1, paramCell) || // Derecha
                dfs(paramArrayOfCell, paramInt1, paramInt2 - 1, paramCell)) { // Izquierda
            this.camino.add(cell); // Si un vecino lleva al objetivo, añadir la celda actual al camino.
            return true;
        }
        return false; // Si ningún vecino lleva al objetivo.
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