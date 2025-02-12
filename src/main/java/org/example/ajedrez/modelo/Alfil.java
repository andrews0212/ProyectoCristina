package org.example.ajedrez.modelo;

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
            int xi = x;
            int yi = y;

            while(xi!=casilla.x){
                if(xi<casilla.x){
                    xi++;
                }else{
                    xi--;
                }
                if(yi<casilla.y){
                    yi++;
                }else{
                    yi--;
                }
                casillas.add(new Casilla(xi,yi));
            }
            return casillas;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
