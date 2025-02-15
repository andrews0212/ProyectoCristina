package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Torre representa una pieza del ajedrez de tipo Torre, que se mueve horizontal o verticalmente.
 * Hereda de la clase Pieza.
 *
 * @author Raul Mora
 */
public class Torre extends Pieza {

    /**
     * Constructor de la clase Torre.
     * Inicializa la posición de la Torre con las coordenadas x e y, y su color (blanco o negro).
     *
     * @param x La posición x de la Torre en el tablero.
     * @param y La posición y de la Torre en el tablero.
     * @param b El color de la pieza (true para blanco, false para negro).
     */
    public Torre(int x, int y, boolean b) {
        super(x, y, b);
    }

    /**
     * Método para validar los movimientos de la Torre.
     * La Torre puede moverse horizontal o verticalmente en el tablero.
     * Este método devuelve una lista de casillas que la Torre puede recorrer hasta llegar a una posición válida.
     *
     * @param casilla La casilla de destino a la que se quiere mover la Torre.
     * @return Una lista de Casillas que la Torre puede recorrer hasta llegar a la casilla de destino.
     */
    @Override
    public List<Casilla> validar(Casilla casilla) {
        // Lista que contendrá las casillas que la Torre puede recorrer.
        List<Casilla> lista = new ArrayList<Casilla>();

        // Verifica si la Torre se mueve verticalmente (misma columna x)
        if (x == casilla.x) {
            int i = y;

            // Si la casilla de destino está por encima, se mueve hacia arriba
            if (i < casilla.y) {
                while (i < casilla.y) {
                    i++;
                    lista.add(new Casilla(casilla.x, i));
                }
            }

            // Si la casilla de destino está por debajo, se mueve hacia abajo
            if (i > casilla.y) {
                while (i > casilla.y) {
                    i--;
                    lista.add(new Casilla(casilla.x, i));
                }
            }
            return lista;
        }
        // Verifica si la Torre se mueve horizontalmente (misma fila y)
        else if (y == casilla.y) {
            int i = x;

            // Si la casilla de destino está a la derecha, se mueve hacia la derecha
            if (i < casilla.x) {
                while (i < casilla.x) {
                    i++;
                    lista.add(new Casilla(i, casilla.y));
                }
            }

            // Si la casilla de destino está a la izquierda, se mueve hacia la izquierda
            if (i > casilla.x) {
                while (i > casilla.x) {
                    i--;
                    lista.add(new Casilla(i, casilla.y));
                }
            }

            // Elimina la última casilla, que es la casilla de destino
            if (!lista.isEmpty()) {
                lista.remove(lista.size() - 1);
            }

            return lista;
        }
        // Si la Torre no se mueve ni vertical ni horizontalmente, devuelve null (movimiento no válido)
        else {
            return null;
        }
    }
    @Override
    public int getValor() {
        return 2;
    }
}
