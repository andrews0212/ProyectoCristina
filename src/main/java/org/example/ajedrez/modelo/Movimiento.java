package org.example.ajedrez.modelo;

public class Movimiento {
    private Casilla inicio;
    private Casilla objetivo;
    private int valor=0;

    /**
     * Constructor de la clase Movimiento. Se inicializa el valor de la pieza y el valor del objetivo.
     * 
     * @param pieza
     * @param objetivo
     * @since 1.0
     */
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

    /**
     * Metodo getInicio. Se obtiene la casilla de inicio.
     * 
     * @since 1.0
     */
    public Casilla getInicio() {
        return new Casilla(inicio.x-1, 9-inicio.y);
    }

    /**
     * Metodo getObjetivo. Se obtiene la casilla de objetivo.
     * 
     * @since 1.0
     */
    public Casilla getObjetivo() {
        return new Casilla(objetivo.x-1, 9-objetivo.y);
    }

    /**
     * Metodo getValor. Se obtiene el valor de la pieza.
     * 
     * @since 1.0
     */
    public Casilla getInicio(Boolean b) {
        return inicio;
    }

    /**
     * Metodo getValor. Se obtiene el valor de la pieza.
     * 
     * @since 1.0
     */
    public Casilla getObjetivo(Boolean b) {
        return objetivo;
    }

    /**
     * Metodo getValor. Se obtiene el valor de la pieza.
     * 
     * @since 1.0
     */
    public int getValor() {
        return valor;
    }

    /**
     * Metodo toString. Se imprime el movimiento.
     * 
     * @since 1.0
     */
    @Override
    public String toString() {
        return "inicio=" + inicio + ", objetivo=" + objetivo + ", valor=" + valor;
    }
}
