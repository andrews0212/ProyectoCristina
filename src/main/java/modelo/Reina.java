package modelo;

import java.util.ArrayList;
import java.util.List;

public class Reina extends Pieza {
    public Reina(int x, int y, boolean b) {
        super(x, y, b);
    }

    @Override
    public List<Casilla> validar(Casilla casilla) {
        List<Casilla> lista = new ArrayList<Casilla>();
        if (x == casilla.x) {
            int i = y;
            while (i < casilla.y) {
                i++;
                lista.add(new Casilla(casilla.x, i));
            }
            return lista;
        } else if (y == casilla.y) {
            int i = x;
            while (i < casilla.x) {
                i++;
                lista.add(new Casilla(i, casilla.y));
            }
            return lista;
        } else if (Math.abs(y - casilla.y) == Math.abs(x - casilla.x)) {
            return lista;
        } else {
            return null;
        }
    }
}
