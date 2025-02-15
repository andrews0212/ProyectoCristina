package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Alfil representa la pieza de ajedrez Alfil.
 * El Alfil se mueve en líneas diagonales en el tablero.
 *
 * @author Raul Mora
 */
public class Alfil extends Pieza {

    /**
     * Constructor de la clase Alfil.
     *
     * @param x La posición X de la pieza Alfil en el tablero.
     * @param y La posición Y de la pieza Alfil en el tablero.
     * @param b El color de la pieza (true para blanca, false para negra).
     */
    public Alfil(int x, int y, boolean b) {
        super(x, y, b);
    }

    /**
     * Sobrescribe el método validar para verificar si el movimiento del Alfil
     * es válido. El movimiento del Alfil se limita a las diagonales del tablero,
     * lo que significa que la diferencia entre las coordenadas X y Y debe ser igual.
     *
     * @param casilla La casilla a la que se desea mover el Alfil.
     * @return Una lista con las casillas que el Alfil recorrería si el movimiento es válido.
     *         Si el movimiento no es válido, retorna null.
     */
    @Override
    public List<Casilla> validar(Casilla casilla) {
        ArrayList<Casilla> casillas = new ArrayList<>();

        // Se verifica si el movimiento es diagonal (la diferencia entre x y y debe ser igual).
        if (Math.abs(y - casilla.y) == Math.abs(x - casilla.x)) {
            int xi = x;
            int yi = y;

            // Generar las casillas que el Alfil recorrería para llegar a la casilla destino.
            while (xi != casilla.x) {
                // Moverse en la dirección horizontal (x) y vertical (y).
                if (xi < casilla.x) {
                    xi++;
                } else {
                    xi--;
                }
                if (yi < casilla.y) {
                    yi++;
                } else {
                    yi--;
                }
                // Agregar la casilla al recorrido.
                casillas.add(new Casilla(xi, yi));
            }

            // Eliminar la última casilla agregada, ya que es la casilla de destino (no debe estar en el recorrido).
            if (!casillas.isEmpty()) {
                casillas.remove(casillas.size() - 1);
            }
            return casillas;  // Devolver las casillas válidas que recorrerá el Alfil.
        }

        // Si el movimiento no es válido (no es diagonal), se devuelve null.
        return null;
    }

    @Override
    public String toString() {
        return super.toString();  // Devuelve la representación en cadena de la pieza Alfil.
    }
    @Override
    public int getValor() {
        return 2;
    }
}
