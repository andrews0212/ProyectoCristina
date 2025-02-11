package modelo;

import java.util.ArrayList;
import java.util.List;

public class Rey extends Pieza {
    public Rey(int x, int y, boolean b) {
        super(x, y, b);
    }
    @Override
    public List<Casilla> validar(Casilla casilla) {
        List<Casilla> lista = new ArrayList<Casilla>();
        if(Math.abs(x - casilla.x)<2 && Math.abs(y - casilla.y)<2){
            return null;
        }
        return lista;
    }
}
