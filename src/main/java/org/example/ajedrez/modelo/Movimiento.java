package org.example.ajedrez.modelo;

public class Movimiento {
    private Casilla inicio;
    private Casilla objetivo;
    private int valor=0;
    public Movimiento(Pieza pieza, Casilla objetivo) {
        this.inicio = pieza.getCasilla();
        this.objetivo = objetivo;
        boolean asedio = Tablero.asedio(!pieza.color,objetivo);
        boolean asediado = Tablero.asedio(!pieza.color,inicio);
        if (asedio) {
            valor = valor - pieza.getValor();
        }
        if (Tablero.tablero.get(objetivo)!=null) {
            valor = valor + Tablero.tablero.get(objetivo).getValor();
        }
        if(asediado) {
            valor = valor + pieza.getValor();
        }

    }
    public Casilla getInicio() {
        return new Casilla(inicio.x-1, 9-inicio.y);
    }
    public Casilla getObjetivo() {
        return new Casilla(objetivo.x-1, 9-objetivo.y);
    }
    public Casilla getInicio(Boolean b) {
        return inicio;
    }
    public Casilla getObjetivo(Boolean b) {
        return objetivo;
    }
    public int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "inicio=" + inicio + ", objetivo=" + objetivo + ", valor=" + valor;
    }
}
