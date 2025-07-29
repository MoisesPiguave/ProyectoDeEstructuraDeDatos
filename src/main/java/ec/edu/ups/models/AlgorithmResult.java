package ec.edu.ups.models;

/**
 * La clase `AlgorithmResult` encapsula los resultados de la ejecución de un algoritmo,
 * incluyendo su nombre, el tamaño de la ruta encontrada y el tiempo de ejecución.
 *
 * @author Moises Piguave
 * @since 28/07/2025
 */
public class AlgorithmResult {
    /**
     * El nombre del algoritmo que produjo este resultado.
     */
    private String algorithmName;
    /**
     * El tamaño de la ruta encontrada por el algoritmo.
     */
    private int pathSize;
    /**
     * El tiempo que tardó el algoritmo en ejecutarse, en nanosegundos.
     */
    private long timeNs;

    /**
     * Construye un nuevo `AlgorithmResult` con el nombre del algoritmo, el tamaño de la ruta y el tiempo de ejecución especificados.
     *
     * @param algorithmName El nombre del algoritmo.
     * @param pathSize El tamaño de la ruta.
     * @param timeNs El tiempo de ejecución en nanosegundos.
     */
    public AlgorithmResult(String algorithmName, int pathSize, long timeNs) {
        this.algorithmName = algorithmName;
        this.pathSize = pathSize;
        this.timeNs = timeNs;
    }

    /**
     * Devuelve el nombre del algoritmo.
     *
     * @return El nombre del algoritmo.
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Devuelve el tamaño de la ruta encontrada por el algoritmo.
     *
     * @return El tamaño de la ruta.
     */
    public int getPathSize() {
        return pathSize;
    }

    /**
     * Devuelve el tiempo que tardó el algoritmo en ejecutarse.
     *
     * @return El tiempo de ejecución en nanosegundos.
     */
    public long getTimeNs() {
        return timeNs;
    }

    /**
     * Establece el nombre del algoritmo.
     *
     * @param algorithmName El nuevo nombre del algoritmo.
     */
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    /**
     * Establece el tamaño de la ruta.
     *
     * @param pathSize El nuevo tamaño de la ruta.
     */
    public void setPathSize(int pathSize) {
        this.pathSize = pathSize;
    }

    /**
     * Establece el tiempo que tardó el algoritmo en ejecutarse.
     *
     * @param timeNs El nuevo tiempo de ejecución en nanosegundos.
     */
    public void setTimeNs(long timeNs) {
        this.timeNs = timeNs;
    }

    /**
     * Devuelve una representación en cadena del objeto `AlgorithmResult`.
     * El formato es "nombreAlgoritmo,tamañoRuta,tiempoNs".
     *
     * @return Una cadena separada por comas con el nombre del algoritmo, el tamaño de la ruta y el tiempo de ejecución.
     */
    @Override
    public String toString() {
        return algorithmName + "," + pathSize + "," + timeNs;
    }
}