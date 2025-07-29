package ec.edu.ups.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ec.edu.ups.models.AlgorithmResult;

/**
 * Implementación del patrón DAO para la clase {@link AlgorithmResult},
 * que utiliza archivos de texto (CSV) como medio de persistencia.
 * <p>
 * Guarda, recupera y elimina resultados de ejecución de algoritmos en un archivo plano.
 * </p>
 * 
 * Formato del archivo CSV:  
 * <code>nombre_algoritmo,numero_celdas,tiempo</code>
 * 
 * @author Pablo Feijo
 */
public class AlgorithmResultDAOFile implements AlgorithmResultDAO {

    /** Archivo donde se almacenan los resultados. */
    private final File file;

    /**
     * Constructor que recibe la ruta del archivo a utilizar.
     *
     * @param paramString Ruta del archivo CSV.
     */
    public AlgorithmResultDAOFile(String paramString) {
        this.file = new File(paramString);
    }

    /**
     * Guarda un resultado de algoritmo en el archivo.
     * <ul>
     *   <li>Si ya existe un resultado con el mismo nombre de algoritmo, lo actualiza.</li>
     *   <li>Si no existe, lo agrega al final del archivo.</li>
     * </ul>
     *
     * @param paramAlgorithmResult Resultado a guardar.
     */
    @Override
    public void save(AlgorithmResult paramAlgorithmResult) {
        List<AlgorithmResult> list = findAll();
        boolean updated = false;

        // Verifica si ya existe un resultado con el mismo nombre
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAlgorithmName().equalsIgnoreCase(paramAlgorithmResult.getAlgorithmName())) {
                list.set(i, paramAlgorithmResult);
                updated = true;
                break;
            }
        }

        if (!updated) {
            list.add(paramAlgorithmResult);
        }

        // Escribe todos los resultados en el archivo
        try (FileWriter fileWriter = new FileWriter(this.file, false)) {
            for (AlgorithmResult algorithmResult : list) {
                fileWriter.write(algorithmResult.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing result to file: " + e.getMessage());
        }
    }

    /**
     * Lee todos los resultados almacenados en el archivo CSV.
     *
     * @return Lista de objetos {@link AlgorithmResult} leídos del archivo.
     */
    @Override
    public List<AlgorithmResult> findAll() {
        List<AlgorithmResult> results = new ArrayList<>();
        if (!this.file.exists())
            return results;

        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int cells = Integer.parseInt(parts[1]);
                    long time = Long.parseLong(parts[2]);
                    results.add(new AlgorithmResult(name, cells, time));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading results from file: " + e.getMessage());
        }

        return results;
    }

    /**
     * Borra todos los registros del archivo, dejándolo vacío.
     */
    @Override
    public void clear() {
        try (FileWriter fileWriter = new FileWriter(this.file, false)) {
            // Simplemente reescribe el archivo vacío
        } catch (IOException e) {
            System.err.println("Error al limpiar el archivo: " + e.getMessage());
        }
    }
}
