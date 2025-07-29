                               Universidad Politécnica Salesiana   
 
                                  Informe del Proyecto Final

______________________________________________________________________________________________________________ 

![alt text](image-5.png)   

# 🔍 Proyecto Final: Resolución de Laberintos con Algoritmos de Búsqueda

## 🧠 Información General

- **Título:** Resolución de Laberintos con Algoritmos de Búsqueda
- **Asignatura:** Estructura de Datos
- **Carrera:** Computación
- **Estudiantes:** Cristian Moscoso, Pablo Feijo, Moises Piguave, Sebastian Calderon.
- **Correo institucional: pfeijo@est.ups.edu.ec , cmoscosot@est.ups.edu.ec , mpiguaves@est.ups.edu.ec, scalderonm@est.ups.edu.ec
- **Fecha:** 7/29/2025
- **Profesor:** Ing. Pablo Torres

---

## 🎯 Descripción del Problema y Propuesta de Solución

Este proyecto tiene como objetivo encontrar la **ruta óptima** desde un punto de inicio (A) hasta un destino (B) dentro de un laberinto representado como una **matriz de celdas**. Cada celda puede ser transitable o no. La aplicación permite comparar distintos algoritmos de búsqueda y optimización en un entorno visual.

Se desarrollaron cuatro métodos para encontrar la ruta entre los puntos A y B en el laberinto:

-	Método Recursivo Simple: Implementa una búsqueda en profundidad utilizando recursión para explorar todas las posibles rutas.

-	Método con Cache (Programación Dinámica): Mejora el método recursivo utilizando una cache para almacenar los resultados de subproblemas ya resueltos, evitando cálculos redundantes.

-	BFS (Breadth-First Search): Implementa una búsqueda en anchura para explorar todas las rutas a una cierta distancia antes de avanzar a rutas más lejanas.

-	DFS (Depth-First Search): Implementa una búsqueda en profundidad para explorar todas las posibles rutas desde el punto inicial.

### 📚 Marco Teórico

Se han implementado varios algoritmos para comparar su rendimiento y eficiencia en la búsqueda de rutas:

- **BFS (Breadth-First Search):** Recorre por niveles, ideal para encontrar la ruta más corta.
- **DFS (Depth-First Search):** Explora en profundidad, puede no ser óptimo pero es simple.
- **Recursivo (2 y 4 direcciones):** Explora todas las posibles rutas mediante recursión.
- **Backtracking:** Técnica que deshace decisiones para encontrar rutas correctas.
- **Recursivo con Caché (memoización):** Optimización que evita cálculos repetidos.

### 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 17+
- **Interfaz Gráfica:** Java Swing (en progreso)
- **Persistencia:** DAO con archivo `results.csv`
- **Gráficos:** JFreeChart (para comparar tiempos de ejecución)
- **Paradigma:** Programación Orientada a Objetos
- **Diseño de Software:** MVC + DAO

Capturas de la Implementación

Metodo DFS
![WhatsApp Image 2025-07-29 at 00 08 13_538490fc](https://github.com/user-attachments/assets/d2758f90-62fa-4ad7-95b9-049f2e0c528c)

Metodo Recursivo
![WhatsApp Image 2025-07-29 at 00 08 26_0336e5c2](https://github.com/user-attachments/assets/a7514fb8-f7d5-4678-9e20-ee32b9e5d70b)




📦 Explicación de Clases del Proyecto
📁 controlador/
MazeController.java
Controlador principal que sigue el patrón MVC. Se encarga de:

Coordinar la ejecución del algoritmo seleccionado.

Gestionar la entrada y salida de datos entre la vista (interfaz) y el modelo (laberinto).

Encapsular la lógica de interacción con el usuario y la manipulación del laberinto.

📁 dao/
AlgorithmResultDAO.java
Interfaz que define las operaciones de acceso a datos para guardar resultados de los algoritmos, como:

Guardar nuevos resultados.

Leer todos los resultados.

Eliminar el archivo CSV.

AlgorithmResultDAOFile.java
Implementación de AlgorithmResultDAO que guarda y recupera los resultados desde el archivo results.csv. También evita duplicados, actualizando entradas si ya existe el mismo algoritmo.

📁 models/
AlgorithmResult.java
Modelo que representa un resultado de ejecución de un algoritmo. Contiene:

Nombre del algoritmo.

Número de celdas recorridas.

Tiempo de ejecución.

Cell.java
Modelo que representa una celda del laberinto. Atributos:

Posición (x, y).

Estado: si es muro, inicio, fin, parte del camino, etc.

Si fue visitada o no.

Referencia al "padre" para poder reconstruir el camino.

CellState.java
Enum que define los posibles estados de una celda:

START, END, WALL, EMPTY, VISITED, PATH.

Mode.java
Enum que representa el modo actual del usuario al interactuar con la UI (por ejemplo, si está seleccionando un punto de inicio, un muro, o el punto de fin).

SolveResult.java
Encapsula los resultados de la resolución del laberinto, incluyendo el tiempo de ejecución y la ruta encontrada. Se utiliza para presentar datos y graficarlos.

📁 solver/
MazeSolver.java
Interfaz común para todos los algoritmos. Define el método:
Permite que la UI use cualquier algoritmo de forma uniforme.

solver/solverImpl/ — Implementaciones de Algoritmos
MazeSolverBFS.java
Implementa el algoritmo BFS (Breadth-First Search). Usa una cola para explorar primero los vecinos más cercanos. Ideal para encontrar el camino más corto.

MazeSolverDFS.java
Implementa DFS (Depth-First Search). Usa una pila o recursión para explorar profundamente una ruta antes de retroceder. Puede encontrar soluciones rápidamente, pero no siempre óptimas.

MazeSolverRecursivo.java
Recorre el laberinto recursivamente en 2 direcciones (probablemente derecha y abajo). Útil para laberintos simples.

MazeSolverRecursivoCompleto.java
Versión mejorada del recursivo anterior, ahora con 4 direcciones (arriba, abajo, izquierda, derecha).

MazeSolverRecursivoCompletoBT.java
Versión con backtracking, que revierte pasos si encuentra un callejón sin salida. Intenta todas las combinaciones hasta hallar una ruta al destino.

📁 views/ (Faltante)
Aunque no está en el ZIP, esta carpeta debería incluir:

MazeFrame.java
Ventana principal de la aplicación. Debe contener:

El panel del laberinto.

Menú para seleccionar el algoritmo.

Botones para ejecutar, limpiar o graficar resultados.

MazePanel.java
Panel que dibuja el laberinto con colores según el estado de cada celda:

Muro = negro, camino = blanco, inicio = verde, fin = rojo, recorrido = azul, etc.

ResultadosDialog.java
Ventana emergente que muestra una tabla con:

Algoritmo

Tiempo

Longitud del camino
También permite graficar los tiempos con JFreeChart.

📁 resources/ (Vacío)
Carpeta reservada para:

Íconos o imágenes del proyecto.

Archivos CSV o de configuración.

🔄 Flujo Esperado de Funcionamiento
El usuario abre la aplicación y ve el laberinto vacío.

Selecciona las dimensiones del laberinto.

Marca celdas como inicio, fin o muros usando el mouse.

Escoge un algoritmo del menú.

Presiona "Resolver": el algoritmo ejecuta y muestra el camino visualmente.

Se guarda el resultado (tiempo, longitud) en results.csv.

El usuario puede ver los resultados previos en tabla o gráfico comparativo.
