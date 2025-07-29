package ec.edu.ups.models;

import java.util.List;

/**
 * La clase `SolveResults` encapsula los resultados de la ejecución de un algoritmo de resolución
 * de caminos o búsqueda. Contiene dos listas principales: una para las celdas **visitadas**
 * durante el proceso de búsqueda y otra para las celdas que constituyen el **camino** encontrado
 * desde el inicio hasta el destino.
 *
 * @author Moises Piguave
 * @since 28/07/2025
 */
public class SolveResults {
    /**
     * Una lista inmutable de objetos `Cell` que representa todas las celdas que fueron
     * **visitadas** o exploradas por el algoritmo durante su ejecución. Esta lista es útil para
     * visualizar la extensión de la búsqueda o para propósitos de depuración y análisis del rendimiento.
     */
    public final List<Cell> visitadas;
    /**
     * Una lista inmutable de objetos `Cell` que representa el **camino** óptimo (o el encontrado)
     * desde la celda de inicio hasta la celda de destino. Si no se encontró un camino, esta lista
     * podría estar vacía o contener solo la celda de inicio, dependiendo de la implementación
     * del algoritmo.
     */
    public final List<Cell> camino;

    /**
     * Construye una nueva instancia de `SolveResults` con las listas de celdas visitadas y del camino.
     * Estas listas son finales, lo que significa que una vez asignadas en el constructor, no pueden
     * ser modificadas para apuntar a nuevas listas. Sin embargo, el contenido de las listas (los objetos `Cell`
     * dentro de ellas) puede ser mutable si la clase `Cell` lo permite.
     *
     * @param paramList1 Una lista de objetos `Cell` que representa las celdas visitadas.
     * @param paramList2 Una lista de objetos `Cell` que representa el camino encontrado.
     */
    public SolveResults(List<Cell> paramList1, List<Cell> paramList2) {
        this.visitadas = paramList1;
        this.camino = paramList2;
    }
}