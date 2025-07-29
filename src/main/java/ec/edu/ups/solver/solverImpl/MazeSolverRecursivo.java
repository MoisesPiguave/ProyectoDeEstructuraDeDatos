package ec.edu.ups.solver.solverImpl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import ec.edu.ups.models.CellState;
import ec.edu.ups.models.Cell;
import ec.edu.ups.models.SolveResults;
import ec.edu.ups.solver.MazeSolver;

/**
 * La clase `MazeSolverRecursivo` implementa la interfaz `MazeSolver` y ofrece una solución
 * para encontrar un camino en un laberinto usando un algoritmo de búsqueda recursivo simple.
 * Este algoritmo explora el laberinto marcando las celdas visitadas y construyendo el camino
 * de regreso desde el destino hasta el punto de partida.
 *
 * @author Moises Piguave
 * @since 28/07/2025
 */
public class MazeSolverRecursivo implements MazeSolver {

    /**
     * Un `Set` de tipo `LinkedHashSet` que guarda las celdas **visitadas** durante la búsqueda.
     * Un `LinkedHashSet` mantiene el orden en que se agregaron las celdas, lo cual es útil
     * para visualizar la secuencia de exploración. Al ser un `Set`, asegura que cada celda
     * se registre solo una vez.
     */
    private Set<Cell> visitadas = new LinkedHashSet<>();

    /**
     * Una `List` de tipo `ArrayList` que almacena las celdas que forman el **camino** hallado
     * desde la celda inicial hasta la celda final. Este camino se construye en orden inverso
     * a medida que las llamadas recursivas retornan.
     */
    private List<Cell> camino = new ArrayList<>();

    /**
     * Este método es el punto de entrada para iniciar la búsqueda de un camino en el laberinto.
     * Antes de cada nueva búsqueda, limpia las listas de celdas visitadas y del camino.
     * Luego, invoca al método recursivo `findPath` para ejecutar la lógica principal de búsqueda.
     *
     * @param paramArrayOfCell La matriz 2D de objetos `Cell` que representa el laberinto.
     * @param paramCell1 La celda de **inicio** de la cual se quiere partir.
     * @param paramCell2 La celda de **destino** a la que se desea llegar.
     * @return Un objeto `SolveResults` que contiene dos listas: una con todas las celdas visitadas
     * y otra con las celdas que componen el camino encontrado.
     */
    @Override
    public SolveResults getPath(Cell[][] paramArrayOfCell, Cell paramCell1, Cell paramCell2) {
        // Limpia las estructuras de datos antes de una nueva búsqueda para asegurar resultados frescos.
        this.visitadas.clear();
        this.camino.clear();

        // Llama al método recursivo `findPath`, que realiza la búsqueda real del camino.
        // Se inicia desde las coordenadas de la celda de inicio.
        findPath(paramArrayOfCell, paramCell1.row, paramCell1.col, paramCell2);

        // Retorna un nuevo objeto SolveResults con copias de las listas para evitar
        // que las listas internas del solucionador sean modificadas externamente.
        return new SolveResults(new ArrayList<>(this.visitadas), new ArrayList<>(this.camino));
    }

    /**
     * Este es el método recursivo privado que implementa el algoritmo de búsqueda para encontrar un camino.
     * Explora las celdas adyacentes (actualmente solo hacia abajo y hacia la derecha)
     * y marca las celdas ya visitadas para evitar bucles infinitos.
     * Si la celda actual es el destino, la añade al camino y devuelve `true`, indicando que se encontró una ruta.
     *
     * @param paramArrayOfCell La matriz 2D de `Cell` que representa el laberinto.
     * @param paramInt1 La coordenada de la fila de la celda que se está evaluando actualmente.
     * @param paramInt2 La coordenada de la columna de la celda que se está evaluando actualmente.
     * @param paramCell La celda de **destino** que el algoritmo intenta alcanzar.
     * @return `true` si se encontró un camino desde la celda actual hasta el destino; `false` en caso contrario.
     */
    private boolean findPath(Cell[][] paramArrayOfCell, int paramInt1, int paramInt2, Cell paramCell) {
        // Primero, verifica si la celda actual es válida (dentro de los límites del laberinto y no es una pared).
        if (!isValid(paramArrayOfCell, paramInt1, paramInt2)) {
            return false; // Si no es válida, no hay camino por aquí.
        }

        Cell cell = paramArrayOfCell[paramInt1][paramInt2];

        // Luego, verifica si esta celda ya fue visitada en esta búsqueda para evitar ciclos.
        if (this.visitadas.contains(cell)) {
            return false; // Si ya fue visitada, ignoramos este camino.
        }

        // Marca la celda actual como visitada.
        this.visitadas.add(cell);

        // Si la celda actual es la celda de destino:
        if (cell.equals(paramCell)) {
            this.camino.add(cell); // La añade al camino.
            return true;          // ¡Se encontró el camino!
        }

        // Intenta explorar las celdas adyacentes de forma recursiva:
        // Primero, intenta moverse hacia abajo (incrementando la fila).
        // Si no encuentra camino hacia abajo, intenta moverse hacia la derecha (incrementando la columna).
        // Si cualquiera de estas llamadas recursivas devuelve `true` (indicando que encontró el destino):
        if (findPath(paramArrayOfCell, paramInt1 + 1, paramInt2, paramCell) || // Mover abajo
                findPath(paramArrayOfCell, paramInt1, paramInt2 + 1, paramCell)) {  // Mover derecha

            this.camino.add(cell); // Añade la celda actual al camino (se construye el camino hacia atrás).
            return true;           // Propaga que se encontró el camino.
        }

        // Si ninguna de las direcciones adyacentes lleva al destino, esta rama no tiene solución.
        return false;
    }

    /**
     * Verifica si una celda en las coordenadas especificadas es **válida** para la exploración.
     * Una celda se considera válida si cumple con las siguientes condiciones:
     * 1. Sus coordenadas (fila y columna) están dentro de los límites de la matriz del laberinto.
     * 2. El estado de la celda no es `CellState.WALL` (es decir, no es un obstáculo infranqueable).
     *
     * @param paramArrayOfCell La matriz 2D de objetos `Cell` que representa el laberinto.
     * @param paramInt1 La coordenada de la fila a verificar.
     * @param paramInt2 La coordenada de la columna a verificar.
     * @return `true` si la celda es válida y puede ser explorada; `false` en caso contrario.
     */
    private boolean isValid(Cell[][] paramArrayOfCell, int paramInt1, int paramInt2) {
        return (paramInt1 >= 0 &&                           // La fila debe ser mayor o igual a 0.
                paramInt1 < paramArrayOfCell.length &&      // La fila debe ser menor que el número total de filas.
                paramInt2 >= 0 &&                           // La columna debe ser mayor o igual a 0.
                paramInt2 < (paramArrayOfCell[0]).length && // La columna debe ser menor que el número total de columnas.
                (paramArrayOfCell[paramInt1][paramInt2]).state != CellState.WALL); // La celda no debe ser una pared.
    }
}