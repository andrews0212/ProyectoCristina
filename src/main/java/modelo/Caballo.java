package modelo;

import java.util.ArrayList;
import java.util.List;

public class Caballo extends Pieza {
    public Caballo(int x, int y, boolean b) {
        super(x, y, b);
    }
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
}
