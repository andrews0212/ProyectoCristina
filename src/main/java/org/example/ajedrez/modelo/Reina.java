package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

public class Reina extends Pieza {
    public Reina(int x, int y, boolean b) {
        super(x, y, b);
    }

    @Override
    public List<Casilla> validar(Casilla casilla) {
        List<Casilla> lista = new ArrayList<Casilla>();
        if(x==casilla.x){
            int i = y;
            if(i<casilla.y) {
                while (i < casilla.y) {
                    i++;
                    lista.add(new Casilla(casilla.x, i));
                }
            }
            if(i>casilla.y){
                while (i > casilla.y) {
                    i--;
                    lista.add(new Casilla(casilla.x, i));
                }
            }
            if(!lista.isEmpty()) {
                lista.remove(lista.size() - 1);
            }
            return lista;
        } else if (y==casilla.y) {
            int i = x;
            if(i<casilla.x) {
                while (i < casilla.x) {
                    i++;
                    lista.add(new Casilla(i, casilla.y));
                }
            }
            if(i>casilla.x){
                while (i > casilla.x) {
                    i--;
                    lista.add(new Casilla(i, casilla.y));
                }
            }
            if(!lista.isEmpty()) {
                lista.remove(lista.size() - 1);
            }
            return lista;
        }else if(Math.abs(y-casilla.y)==Math.abs(x-casilla.x)) {
            int xi = x;
            int yi = y;

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
            if(!lista.isEmpty()) {
                lista.remove(lista.size() - 1);
            }
            return lista;
        } else {
            return null;
        }
    }
}
