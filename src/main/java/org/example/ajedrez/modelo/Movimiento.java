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
        }// Traslado de la pieza provisional
        tablero.get(origen).x = destino.x;
        tablero.get(origen).y = destino.y;
        tablero.put(destino, tablero.get(origen));
        tablero.put(origen, null);

        // Verificar si el rey está en jaque
        boolean retorno=true;
        if (jaque(obtenerRey(tablero.get(destino).color))) {
            retorno=false;
        }
        //Retorno forzado tanto si es jaque o no
        tablero.get(destino).x = origen.x;
        tablero.get(destino).y = origen.y;
        tablero.put(origen, tablero.get(destino));
        tablero.put(destino, null);
        //Si era jaque movimiento no valido
        if(!retorno) {
            return false;
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
