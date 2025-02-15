package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Peon representa un peón en el juego de ajedrez.
 * Implementa la lógica para validar el movimiento del peón en el tablero.
 * El peón se mueve una casilla hacia adelante, o dos casillas en su primer movimiento.
 * También puede capturar piezas en diagonal.
 *
 * @author Raul Mora
 */
public class Peon extends Pieza {

    /**
     * Constructor de la clase Peon.
     *
     * @param x La posición X inicial del peón en el tablero.
     * @param y La posición Y inicial del peón en el tablero.
     * @param color El color del peón (true para negro, false para blanco).
     */
    public Peon(int x, int y, boolean color) {
        super(x, y, color);
    }

    /**
     * Método que valida los movimientos del peón.
     * El peón se mueve de una manera particular en el ajedrez:
     * 1. Se mueve una casilla hacia adelante, y dos casillas en su primer movimiento.
     * 2. Puede capturar piezas en diagonal.
     *
     * @param casilla La casilla de destino a la que se quiere mover el peón.
     * @return Una lista de casillas válidas para el movimiento, o null si el movimiento no es válido.
     */
    @Override
    public List<Casilla> validar(Casilla casilla) {
        ArrayList<Casilla> casillas = new ArrayList<>();

        // Fila inicial dependiendo del color (2 para blanco, 7 para negro).
        int filaInicial = color ? 7 : 2;

        // El peón se puede mover dos casillas hacia adelante en su primer movimiento.
        if (!color && (casilla.y == y + 2) && y == filaInicial && x==casilla.x) {
            casillas.add(casilla);
            return casillas;  // Movimiento válido.
        }
        if (color && (casilla.y == y - 2) && y == filaInicial && x==casilla.x) {
            casillas.add(casilla);
            return casillas;  // Movimiento válido.
        }

        // Movimiento normal de una casilla hacia adelante (captura o movimiento recto).
        if (!color && casilla.y == y + 1) {
            // Movimiento en diagonal para capturar una pieza.
            if (Math.abs(casilla.x - x) == 1 && Tablero.tablero.get(casilla) != null) {
                return casillas;  // Captura válida.
            }
            // Movimiento recto hacia adelante (sin captura).
            if (Math.abs(casilla.x - x) == 0) {
                casillas.add(casilla);
                return casillas;  // Movimiento válido.
            }
        }
        if (color && casilla.y == y - 1) {
            // Movimiento en diagonal para capturar una pieza.
            if (Math.abs(casilla.x - x) == 1 && Tablero.tablero.get(casilla) != null) {
                return casillas;  // Captura válida.
            }
            // Movimiento recto hacia adelante (sin captura).
            if (Math.abs(casilla.x - x) == 0) {
                casillas.add(casilla);
                return casillas;  // Movimiento válido.
            }
        }

        // Si el movimiento no es válido, devuelve null.
        return null;
    }
    @Override
    public int getValor() {
        return 1;
    }
}
