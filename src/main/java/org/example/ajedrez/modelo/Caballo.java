package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Caballo representa la pieza de ajedrez Caballo.
 * El Caballo se mueve en forma de "L", es decir, dos casillas en una dirección y una casilla en dirección perpendicular.
 *
 * @author Raul Mora
 */
public class Caballo extends Pieza {
    /**
     * Constructor de la clase Caballo.
     *
     * @param x La posición X de la pieza Caballo en el tablero.
     * @param y La posición Y de la pieza Caballo en el tablero.
     * @param b El color de la pieza (true para blanca, false para negra).
     */
    public Caballo(int x, int y, boolean b) {
        super(x, y, b);
    }

    /**
     * Sobrescribe el método validar para verificar si el movimiento del Caballo
     * es válido. El movimiento de un Caballo se define por una diferencia de
     * 2 casillas en una dirección y 1 casilla en dirección perpendicular.
     *
     * @param casilla La casilla a la que se desea mover el Caballo.
     * @return Una lista con la casilla destino si el movimiento es válido, null si no es válido.
     */
    @Override
    public List<Casilla> validar(Casilla casilla) {
        ArrayList<Casilla> casillas = new ArrayList<>();

        // Se calcula la diferencia absoluta entre las posiciones de la pieza actual y la casilla de destino.
        int difX = Math.abs(x - casilla.x);
        int difY = Math.abs(y - casilla.y);

        // El caballo puede moverse en una L, es decir, una diferencia de 2 casillas en una dirección y 1 en la otra.
        if (difX != 0 && difY != 0 && difX + difY == 3) {
            casillas.add(casilla);  // Si el movimiento es válido, agrega la casilla destino a la lista.
            return casillas;
        }

        return null;  // Si el movimiento no es válido, retorna null.
    }
}
