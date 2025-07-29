package ec.edu.ups.solver;

import ec.edu.ups.models.Cell;
import ec.edu.ups.models.SolveResults;

/**
 * La interfaz `MazeSolver` define el contrato para cualquier clase que implemente
 * un algoritmo capaz de **resolver un laberinto** y encontrar un camino entre dos puntos.
 * Cualquier solucionador de laberintos debe implementar este método para proporcionar
 * los resultados de su búsqueda.
 *
 * @author Moises Piguave
 * @since 28/07/2025
 */
public interface MazeSolver {

    /**
     * Busca un camino desde una celda de inicio hasta una celda de destino dentro de un laberinto dado.
     * Este método es el principal punto de interacción para los usuarios de un solucionador de laberintos.
     * La implementación de este método debe devolver información sobre las celdas visitadas
     * y el camino encontrado (si existe).
     *
     * @param maze Una matriz 2D de objetos `Cell` que representa la estructura del laberinto.
     * Cada `Cell` contiene su posición y estado (vacío, pared, inicio, fin, etc.).
     * @param startCell El objeto `Cell` que representa el punto de partida o la celda de inicio en el laberinto.
     * @param endCell El objeto `Cell` que representa el punto de llegada o la celda de destino en el laberinto.
     * @return Un objeto `SolveResults` que encapsula los resultados de la búsqueda:
     * una lista de todas las `Cell` visitadas durante el proceso y una lista de `Cell`
     * que forman el camino encontrado desde `startCell` hasta `endCell`.
     * Si no se encuentra un camino, la lista del camino puede estar vacía o contener solo la celda de inicio.
     */
    public SolveResults getPath(Cell[][] maze, Cell startCell, Cell endCell);
}