package org.example.ajedrez.modelo;

public class Movimiento {
    private Casilla inicio;
    private Casilla objetivo;
    private boolean asedio;
    private int valor;
    public Movimiento(Casilla inicio, Casilla objetivo) {
        this.inicio = inicio;
        this.objetivo = objetivo;
        this.asedio = Tablero.asedio(!Tablero.tablero.get(inicio).color,objetivo);
        if (asedio) {
            if (Tablero.tablero.get(objetivo)!=null) {
                valor = Tablero.tablero.get(objetivo).getValor() - Tablero.tablero.get(inicio).getValor();
            }
        }else{
            if (Tablero.tablero.get(objetivo)!=null) {
                valor = Tablero.tablero.get(objetivo).getValor();
            }
        }
    }
    public Casilla getInicio() {
        return new Casilla(inicio.x-1, 9-inicio.y);
    }
    public Casilla getObjetivo() {
        return new Casilla(objetivo.x+1, 9-objetivo.y);
    }
    public int getValor() {
        return valor;
    }
}
