package modelo;

import java.util.ArrayList;
import java.util.List;

public class Alfil extends Pieza {
    public Alfil(int x, int y, boolean b) {
        super(x, y, b);
    }
    @Override
    public List<Casilla> validar(Casilla casilla) {
        ArrayList<Casilla> casillas = new ArrayList<>();
        if(Math.abs(y-casilla.y)==Math.abs(x-casilla.x)){
            return casillas;
        }
        return null;
    }
}
