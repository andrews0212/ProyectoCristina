package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

public class Rey extends Pieza {
    public Rey(int x, int y, boolean color) {
        super(x, y, color);
    }
    @Override
    public List<Casilla> validar(Casilla casilla) {
        List<Casilla> lista = new ArrayList<Casilla>();
        if(Math.abs(x - casilla.x)<2 && Math.abs(y - casilla.y)<2 && !Tablero.asedio(!color,casilla)){
            return lista;
        }
        return null;
    }
}
