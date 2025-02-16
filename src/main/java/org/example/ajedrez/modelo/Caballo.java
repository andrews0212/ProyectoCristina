package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase Caballo, que extiende de la clase Pieza.
 * @author Raul Mora
 * @version 1.0
 * @since 1.0
 */
public class Caballo extends Pieza {
    public Caballo(int x, int y, boolean b) {
        super(x, y, b);
    }

    /**
     * Sobrescribe el método validar() de la clase Pieza, para que devuelva un ArrayList de Casillas, si la pieza Caballo no puede moverse.
     * @return un ArrayList de Casillas, si la pieza Caballo no puede moverse.
     * @see org.example.ajedrez.modelo.Pieza#validar(org.example.ajedrez.modelo.Casilla)
     * @since 1.0
     */    
    @Override
    public List<Casilla> validar(Casilla casilla) {
        ArrayList<Casilla> casillas = new ArrayList<>();
        int difX = Math.abs(x-casilla.x);
        int difY = Math.abs(y-casilla.y);
        if(difX!=0 && difY!=0 && difX+difY==3){
            return casillas;
        }
        return null;
    }

    /**
     * Sobrescribe el método getValor() de la clase Pieza, para que devuelva el valor de la pieza Caballo.
     * @return el valor de la pieza Caballo.
     * @see org.example.ajedrez.modelo.Pieza#getValor()
     * @since 1.0
     */
    @Override
    public int getValor() {
        return 2;
    }
}