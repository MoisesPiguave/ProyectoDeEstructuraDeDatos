package ec.edu.ups.dao;

import java.util.List;
import ec.edu.ups.models.AlgorithmResult;

/**
 * Interfaz que define las operaciones básicas para acceder y manipular
 * los resultados obtenidos por los algoritmos de resolución de laberintos.
 * <p>
 * Esta interfaz sigue el patrón DAO (Data Access Object) para abstraer
 * la lógica de persistencia del resto de la aplicación.
 * </p>
 * 
 * @author Pablo Feijo
 */
public interface AlgorithmResultDAO {

    /**
     * Guarda un nuevo resultado de ejecución de un algoritmo.
     * Si el resultado ya existe, la implementación puede optar por actualizarlo.
     *
     * @param paramAlgorithmResult Objeto que contiene el nombre del algoritmo,
     *                             la longitud del camino y el tiempo de ejecución.
     */
    void save(AlgorithmResult paramAlgorithmResult);

    /**
     * Recupera todos los resultados almacenados en el sistema.
     *
     * @return Lista de objetos {@link AlgorithmResult} representando cada ejecución registrada.
     */
    List<AlgorithmResult> findAll();

    /**
     * Elimina todos los registros almacenados. Útil para reiniciar el sistema o limpiar el archivo CSV.
     */
    void clear();
}
