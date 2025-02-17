package org.example.ajedrez.modelo;

public class Movimiento {
    private  boolean huir;
    private Casilla inicio;
    private Casilla objetivo;
    private boolean asedio;
    private int valor=0;

    @Override
    public String toString() {
        return "Movimiento{" +
                "inicio=" + inicio +
                ", objetivo=" + objetivo +
                ", valor=" + valor +
                '}';
    }

    public Movimiento(Casilla inicio, Casilla objetivo) {
        this.inicio = inicio;
        this.objetivo = objetivo;
        this.asedio = Tablero.asedio(!Tablero.tablero.get(inicio).color,objetivo);
        this.huir = Tablero.asedio(!Tablero.tablero.get(inicio).color,inicio);
        if (asedio) {
            valor = valor-Tablero.tablero.get(inicio).getValor();
        }
        if (Tablero.tablero.get(objetivo)!=null) {
            valor = valor + Tablero.tablero.get(objetivo).getValor();
        }
        if(huir){
            valor = valor + Tablero.tablero.get(inicio).getValor();
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
}
