package ec.edu.ups.models;

/**
 * La enumeración `CellState` representa los posibles **estados** que una celda puede tener
 * en una cuadrícula o laberinto. Cada estado define una característica o rol específico
 * de la celda, lo cual es fundamental para algoritmos de búsqueda de caminos o la representación
 * de entornos de juego.
 *
 * @author Moises Piguave
 * @since 28/07/2025
 */
public enum CellState {
    /**
     * Indica que la celda está **vacía** y es transitable. Es el estado por defecto para la mayoría de las celdas
     * y significa que un camino puede pasar a través de ella.
     */
    EMPTY,
    /**
     * Indica que la celda contiene un **obstáculo** o una pared y no es transitable.
     * Las entidades o los caminos no pueden moverse a través de estas celdas.
     */
    WALL,
    /**
     * Indica que la celda es el **punto de inicio** de una ruta. Es el origen desde donde un algoritmo
     * de búsqueda de caminos comenzará su recorrido.
     */
    START,
    /**
     * Indica que la celda es el **punto final** o destino de una ruta. Es el objetivo que un algoritmo
     * de búsqueda de caminos intenta alcanzar.
     */
    END,
    /**
     * Indica que la celda forma parte de la **ruta** encontrada por un algoritmo de búsqueda de caminos.
     * Este estado se utiliza generalmente para visualizar el camino óptimo o resultante.
     */
    PATH;
}