package ec.edu.ups;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import ec.edu.ups.vista.MazeFrame;



public class Main {
    public static void main(String[] paramArrayOfString) {
        SwingUtilities.invokeLater(() -> {
            int[] arrayOfInt = solicitarDimensiones();
            if (arrayOfInt != null)
                new MazeFrame(arrayOfInt[0], arrayOfInt[1]);
        });
    }

    public static int[] solicitarDimensiones() {
        int filas = -1;
        int columnas = -1;
        boolean inputValido = false;

        while (!inputValido) {
            String str1 = JOptionPane.showInputDialog("Ingrese n de filas:");
            if (str1 == null) {
                return null;
            }

            try {
                filas = Integer.parseInt(str1.trim());
                if (filas <= 4) {
                    JOptionPane.showMessageDialog(null, "Las filas deben ser mayores a 4.");
                    continue;
                }
                inputValido = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido para las filas.");
                continue;
            }
        }

        inputValido = false;

        while (!inputValido) {
            String str2 = JOptionPane.showInputDialog("Ingrese n de columnas:");
            if (str2 == null) {
                return null;
            }

            try {
                columnas = Integer.parseInt(str2.trim());
                if (columnas <= 4) {
                    JOptionPane.showMessageDialog(null, "Las columnas deben ser mayores a 4.");
                    continue;
                }
                inputValido = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido para las columnas.");
                continue;
            }
        }

        return new int[] { filas, columnas };
    }
}