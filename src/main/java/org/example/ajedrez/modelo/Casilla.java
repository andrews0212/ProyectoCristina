package org.example.ajedrez.modelo;

import java.util.Objects;

/**
 * La clase Casilla representa una casilla en el tablero de ajedrez.
 * Cada casilla tiene una posición en el tablero indicada por las coordenadas (x, y).
 *
 * @author Raul Mora
 */
public class Casilla {
    // Atributos que representan las coordenadas de la casilla en el tablero.
    public int x, y;

    /**
     * Constructor de la clase Casilla.
     *
     * @param x La posición X de la casilla en el tablero.
     * @param y La posición Y de la casilla en el tablero.
     */
    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sobrescribe el método equals para comparar dos objetos Casilla.
     * Dos casillas son iguales si tienen las mismas coordenadas.
     *
     * @param obj El objeto con el que se va a comparar.
     * @return true si las casillas son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Si ambos objetos son el mismo, son iguales.
        if (obj == null || getClass() != obj.getClass()) return false;  // Si el objeto es nulo o no es de la misma clase, no son iguales.

        Casilla casilla = (Casilla) obj;  // Cast del objeto a tipo Casilla.

        // Compara las coordenadas de la casilla actual con la casilla proporcionada.
        return x == casilla.x && y == casilla.y;
    }

    /**
     * Sobrescribe el método hashCode para generar un código hash basado en las coordenadas (x, y).
     * Este método se usa, por ejemplo, en estructuras de datos como HashMap y HashSet.
     *
     * @return El código hash generado a partir de las coordenadas.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);  // Usa el método hash de la clase Objects para generar el código hash.
    }

    /**
     * Sobrescribe el método toString para devolver una representación en formato cadena de la casilla.
     *
     * @return Una cadena representando las coordenadas de la casilla.
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";  // Devuelve una cadena con las coordenadas de la casilla.
    }
}
