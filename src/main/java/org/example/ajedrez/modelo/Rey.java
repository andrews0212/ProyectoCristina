package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Rey representa la pieza del rey en el juego de ajedrez.
 * El rey puede moverse una casilla en cualquier dirección y debe evitar quedar en jaque.
 *
 * @author Raul Mora
 */
public class Rey extends Pieza {

    /**
     * Constructor para crear un rey en una posición específica y con un color determinado.
     *
     * @param x Coordenada X de la casilla inicial del rey.
     * @param y Coordenada Y de la casilla inicial del rey.
     * @param color El color del rey (true para negro, false para blanco).
     */
    public Rey(int x, int y, boolean color) {
        super(x, y, color);
    }

    /**
     * Valida si el movimiento del rey a una casilla destino es válido.
     * El rey puede moverse solo una casilla en cualquier dirección, pero no puede moverse
     * a una casilla que ponga en peligro su propia seguridad (jaque).
     *
     * @param casilla La casilla destino que se desea validar.
     * @return Una lista de casillas válidas a las que el rey puede moverse, o null si no es válido.
     */
    @Override
    public List<Casilla> validar(Casilla casilla) {
        List<Casilla> lista = new ArrayList<Casilla>();

        // Verifica si el rey se mueve a una casilla dentro de un rango de 1 casilla en cualquier dirección
        // y si no está poniendo al rey en jaque.
        if (Math.abs(x - casilla.x) < 2 && Math.abs(y - casilla.y) < 2 && !Tablero.asedio(!color, casilla)) {
            return lista;  // Retorna la lista vacía si el movimiento es válido
        }

        // Si las condiciones no se cumplen, retorna null, indicando que el movimiento no es válido
        return null;
    }
    @Override
    public int getValor() {
        return 5;
    }
}
