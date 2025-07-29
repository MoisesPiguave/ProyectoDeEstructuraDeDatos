package ec.edu.ups.models;

/**
 * La clase `Cell` representa una celda individual dentro de una cuadrícula bidimensional.
 * Cada celda se define por sus coordenadas de **fila** y **columna**, y posee un **estado**
 * que describe su rol o contenido dentro de la cuadrícula. Esta clase es fundamental
 * para la construcción de entornos como laberintos, tableros de juegos o mapas para algoritmos de búsqueda de rutas.
 *
 * @author Moises Piguave
 * @since 28/07/2025
 */
public class Cell {
    /**
     * La coordenada de la **fila** de la celda. Las filas suelen numerarse de arriba a abajo.
     */
    public int row;

    /**
     * La coordenada de la **columna** de la celda. Las columnas suelen numerarse de izquierda a derecha.
     */
    public int col;

    /**
     * El **estado** actual de la celda. Este atributo define si la celda está vacía, es un obstáculo,
     * el punto de inicio o el punto final. El `CellState` es una enumeración que define estos posibles estados.
     */
    public CellState state;

    /**
     * Construye una nueva instancia de `Cell` con las coordenadas de fila y columna especificadas.
     * Al ser creada, la celda se inicializa con el estado `CellState.EMPTY` por defecto, lo que indica
     * que es transitable a menos que se cambie su estado explícitamente.
     *
     * @param paramInt1 La coordenada de la **fila** para la nueva celda.
     * @param paramInt2 La coordenada de la **columna** para la nueva celda.
     */
    public Cell(int paramInt1, int paramInt2) {
        this.row = paramInt1;
        this.col = paramInt2;
        this.state = CellState.EMPTY;
    }

    /**
     * Compara este objeto `Cell` con otro objeto para determinar si son **iguales**.
     * Dos celdas se consideran iguales si y solo si sus coordenadas de fila y columna son idénticas.
     * El estado de la celda (`CellState`) **no** se considera para la igualdad, lo que permite que dos celdas en la misma
     * posición pero con diferentes roles (por ejemplo, una vacía y otra un obstáculo) sean tratadas como la misma ubicación física.
     *
     * @param paramObject El objeto con el que se va a comparar. Se espera que sea una instancia de `Cell`.
     * @return `true` si los objetos son la misma instancia o si son dos celdas con las mismas coordenadas de fila y columna;
     * `false` en caso contrario.
     */
    @Override
    public boolean equals(Object paramObject) {
        // Optimización: si es la misma referencia de objeto, son iguales.
        if (this == paramObject)
            return true;
        // Si el objeto es nulo o no es una instancia de Cell, no pueden ser iguales.
        if (!(paramObject instanceof Cell))
            return false;
        // Realiza un casting seguro al tipo Cell para acceder a sus atributos.
        Cell cell = (Cell)paramObject;
        // Compara las coordenadas de fila y columna para determinar la igualdad posicional.
        return (this.row == cell.row && this.col == cell.col);
    }

    /**
     * Calcula y devuelve un valor de **código hash** para este objeto `Cell`.
     * El código hash se genera a partir de las coordenadas de fila y columna de la celda.
     * Es crucial sobrescribir `hashCode()` siempre que se sobrescribe `equals()` para garantizar
     * que los objetos `Cell` funcionen correctamente en colecciones basadas en hash (como `HashMap` o `HashSet`).
     * Un código hash consistente significa que dos objetos `Cell` iguales (según `equals()`) siempre producirán el mismo hash.
     *
     * @return Un valor entero que representa el código hash de esta celda, basado en sus coordenadas.
     */
    @Override
    public int hashCode() {
        // Una fórmula simple y común para combinar dos enteros en un hash,
        // utilizando el número primo 31 para una buena distribución de los valores de hash.
        return 31 * this.row + this.col;
    }
}
