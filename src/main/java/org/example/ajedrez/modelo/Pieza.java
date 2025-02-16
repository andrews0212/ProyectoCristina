package org.example.ajedrez.modelo;

import java.util.List;

/**
 * La clase Pieza es la clase base para todas las piezas del ajedrez.
 * Contiene la posición de la pieza y su color, y métodos comunes que pueden
 * ser heredados por las clases de las piezas específicas (Reina, Rey, etc.).
 *
 * @author Raul Mora
 * @version 1.0
 * @since 1.0
 */
public class Pieza {
    public int x;       // Coordenada X de la posición de la pieza en el tablero.
    public int y;       // Coordenada Y de la posición de la pieza en el tablero.
    public boolean color; // El color de la pieza (true para negro, false para blanco).

    /**
     * Constructor de la clase Pieza.
     *
     * @param x La posición X de la pieza en el tablero.
     * @param y La posición Y de la pieza en el tablero.
     * @param color El color de la pieza (true para negro, false para blanco).
     * @since 1.0
     */
    public Pieza(int x, int y, boolean color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Método de validación que debe ser sobrecargado por las subclases.
     * Cada pieza específica implementará su propia lógica de validación de movimiento.
     *
     * @param casilla La casilla de destino a la que se desea mover.
     * @return Una lista de casillas válidas para el movimiento, o null si el movimiento no es válido.
     * @since 1.0
     */
    public List<Casilla> validar(Casilla casilla) {
        return null;  // Implementado en las subclases (por ejemplo, Reina, Rey, etc.)
    }

    /**
     * Devuelve una nueva instancia de Casilla con las coordenadas de la pieza.
     *
     * @return Un objeto Casilla que representa la posición actual de la pieza.
     * @since 1.0
     */
    public Casilla getCasilla() {
        return new Casilla(x, y);
    }

    /**
     * Método toString que devuelve una representación en forma de cadena de la pieza.
     *
     * @return Un String que describe la pieza, incluyendo su posición y color.
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Pieza{" + "x=" + x + ", y=" + y + ", color=" + color + '}';
    }

    /**
     * Método que devuelve el valor de la pieza.
     *
     * @return El valor de la pieza.
     * @since 1.0
     */
    public int getValor() {
        return 0;
    }
}
