package modelo;

import java.util.ArrayList;
import java.util.List;

public class Peon extends Pieza {
    public Peon(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public List<Casilla> validar(Casilla casilla) {
        ArrayList<Casilla> casillas = new ArrayList<>();
        if (!color && casilla.y == y + 1) {
            if (Math.abs(casilla.x - x) == 1 && Tablero.tablero.get(casilla)!=null) {
                return casillas;
            }
            if (Math.abs(casilla.x - x) == 0) {
                casillas.add(casilla);
                return casillas;
            }
        }
        if (color && casilla.y == y - 1) {
            if (Math.abs(casilla.x - x) == 1 && Tablero.tablero.get(casilla)!=null) {
                return casillas;
            }
            if (Math.abs(casilla.x - x) == 0) {
                casillas.add(casilla);
                return casillas;
            }
        }
        return null;
    }
}
