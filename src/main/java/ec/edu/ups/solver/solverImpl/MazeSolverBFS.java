package ec.edu.ups.solver.solverImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import ec.edu.ups.models.Cell;
import ec.edu.ups.models.CellState;
import ec.edu.ups.models.SolveResults;
import ec.edu.ups.solver.MazeSolver;

/**
 * `MazeSolverBFS` implementa la interfaz `MazeSolver` utilizando el algoritmo de Búsqueda en Amplitud (BFS).
 * Esta clase encuentra el camino más corto desde una celda inicial hasta una celda final en un laberinto dado.
 */
public class MazeSolverBFS implements MazeSolver {
    /**
     * Encuentra un camino desde la celda de inicio hasta la celda final en el laberinto dado utilizando el algoritmo BFS.
     *
     * @param paramArrayOfCell Un arreglo 2D de objetos `Cell` que representa la cuadrícula del laberinto.
     * @param paramCell1 La `Cell` de inicio.
     * @param paramCell2 La `Cell` final.
     * @return Un objeto `SolveResults` que contiene dos listas de celdas:
     * 1. La lista `visitedCells`, que contiene todas las celdas exploradas durante el recorrido BFS.
     * 2. La lista `path`, que contiene las celdas que forman el camino más corto desde el inicio hasta el fin.
     * Si no se encuentra un camino, la lista `path` estará vacía.
     */
    public SolveResults getPath(Cell[][] paramArrayOfCell, Cell paramCell1, Cell paramCell2) {
        // Dimensiones del laberinto
        int i = paramArrayOfCell.length, j = (paramArrayOfCell[0]).length;
        // `arrayOfBoolean` (visitado) mantiene un registro de las celdas visitadas para evitar ciclos.
        boolean[][] arrayOfBoolean = new boolean[i][j];
        // `hashMap` (parentMap) almacena el padre de cada celda para reconstruir el camino.
        HashMap<Object, Object> hashMap = new HashMap<>();
        // `linkedList` (cola) es la cola utilizada para el recorrido BFS.
        LinkedList<Cell> linkedList = new LinkedList();
        // `arrayList1` (celdasVisitadas) almacena el orden en que las celdas son visitadas.
        ArrayList<Cell> arrayList1 = new ArrayList();
        // Obtener las celdas de inicio y fin reales.
        Cell cell1 = paramArrayOfCell[paramCell1.row][paramCell1.col];
        Cell cell2 = paramArrayOfCell[paramCell2.row][paramCell2.col];
        // Iniciar BFS desde la celda de inicio.
        linkedList.add(cell1);
        arrayOfBoolean[cell1.row][cell1.col] = true; // Marcar la celda de inicio como visitada.
        // Bucle de recorrido BFS.
        while (!linkedList.isEmpty()) {
            Cell cell = linkedList.poll(); // Desencolar la celda actual.
            arrayList1.add(cell); // Añadir la celda a la lista de visitadas.
            if (cell.equals(cell2)) // Si la celda actual es la meta, romper el bucle.
                break;
            // Explorar vecinos (arriba, abajo, izquierda, derecha).
            for (int[] arrayOfInt : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }) {
                int k = cell.row + arrayOfInt[0]; // Fila del vecino.
                int m = cell.col + arrayOfInt[1]; // Columna del vecino.
                // Verificar límites del laberinto.
                if (k >= 0 && k < i && m >= 0 && m < j) {
                    Cell cell4 = paramArrayOfCell[k][m]; // Obtener la celda vecina.
                    // Si el vecino no ha sido visitado y no es una pared.
                    if (!arrayOfBoolean[k][m] && cell4.state != CellState.WALL) {
                        arrayOfBoolean[k][m] = true; // Marcar como visitado.
                        hashMap.put(cell4, cell); // Establecer el padre.
                        linkedList.add(cell4); // Encolar el vecino.
                    }
                }
            }
        }
        ArrayList<Cell> arrayList2 = new ArrayList(); // Lista para el camino final.
        Cell cell3 = cell2; // Iniciar la reconstrucción desde la celda final.
        // Reconstruir el camino usando el mapa de padres.
        while (hashMap.containsKey(cell3)) {
            arrayList2.add(cell3);
            cell3 = (Cell)hashMap.get(cell3);
        }
        // Si se llegó a la celda de inicio, el camino existe.
        if (cell3.equals(cell1)) {
            arrayList2.add(cell1);
            Collections.reverse(arrayList2); // Invertir el camino para que vaya de inicio a fin.
        } else {
            arrayList2.clear(); // Si no se llegó al inicio, el camino está vacío.
        }
        return new SolveResults(arrayList1, arrayList2); // Devolver el resultado.
    }
}