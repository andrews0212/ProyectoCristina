package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Reina representa la pieza de la reina en el juego de ajedrez.
 * La reina tiene la capacidad de moverse tanto horizontalmente, verticalmente
 * como diagonalmente, a cualquier distancia en esas direcciones.
 *
 * @author Raul Mora
 */
public class Reina extends Pieza {

    /**
     * Constructor para crear una reina en una posición específica y con un color determinado.
     *
     * @param x Coordenada X de la casilla inicial de la reina.
     * @param y Coordenada Y de la casilla inicial de la reina.
     * @param b El color de la reina (true para negro, false para blanco).
     */
    public Reina(int x, int y, boolean b) {
        super(x, y, b);
    }

    /**
     * Valida si el movimiento de la reina a una casilla destino es válido.
     * La reina puede moverse cualquier número de casillas en línea recta en cualquiera de las 4 direcciones cardinales
     * o diagonalmente. La validación también comprueba que no haya obstáculos en el camino.
     *
     * @param casilla La casilla destino que se desea validar.
     * @return Una lista de casillas válidas a las que la reina puede moverse, o null si no es válido.
     */
    @Override
    public List<Casilla> validar(Casilla casilla) {
        List<Casilla> lista = new ArrayList<Casilla>();

        // Movimiento en la misma columna (horizontal)
        if (x == casilla.x) {
            int i = y;
            // Movimiento hacia abajo (aumento de y)
            if (i < casilla.y) {
                while (i < casilla.y) {
                    i++;
                    lista.add(new Casilla(casilla.x, i));
                }
            }
            // Movimiento hacia arriba (disminución de y)
            if (i > casilla.y) {
                while (i > casilla.y) {
                    i--;
                    lista.add(new Casilla(casilla.x, i));
                }
            }
            // Elimina la última casilla que es la misma que la de destino
            if (!lista.isEmpty()) {
                lista.remove(lista.size() - 1);
            }
            return lista;
        }
        // Movimiento en la misma fila (vertical)
        else if (y == casilla.y) {
            int i = x;
            // Movimiento hacia la derecha (aumento de x)
            if (i < casilla.x) {
                while (i < casilla.x) {
                    i++;
                    lista.add(new Casilla(i, casilla.y));
                }
            }
            // Movimiento hacia la izquierda (disminución de x)
            if (i > casilla.x) {
                while (i > casilla.x) {
                    i--;
                    lista.add(new Casilla(i, casilla.y));
                }
            }
            // Elimina la última casilla que es la misma que la de destino
            if (!lista.isEmpty()) {
                lista.remove(lista.size() - 1);
            }
            return lista;
        }
        // Movimiento en diagonal (diferencia absoluta entre x y y es igual)
        else if (Math.abs(y - casilla.y) == Math.abs(x - casilla.x)) {
            int xi = x;
            int yi = y;

            // Movimiento en la diagonal hasta llegar a la casilla destino
            while (xi != casilla.x) {
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
                lista.add(new Casilla(xi, yi));
            }
            // Elimina la última casilla que es la misma que la de destino
            if (!lista.isEmpty()) {
                lista.remove(lista.size() - 1);
            }
            return lista;
        } else {
            // Si el movimiento no corresponde a ninguna de las opciones válidas
            return null;
        }
    }
    @Override
    public int getValor() {
        return 3;
    }
}
