package org.example.ajedrez.modelo;

import javafx.scene.control.Alert;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La clase Tablero representa el tablero de ajedrez en el que se juega la partida.
 * Contiene la lógica para realizar movimientos de las piezas, verificar condiciones de jaque y jaque mate,
 * y gestionar las piezas en el tablero.
 *
 * @author Raul Mora
 */
public class Tablero {

    /**
     * Mapa que asocia una casilla a una pieza en el tablero.
     */
    public static Map<Casilla, Pieza> tablero = new HashMap<>();

    /**
     * El rey blanco.
     */
    public static Rey reyB;

    /**
     * El rey negro.
     */
    public static Rey reyN;

    /**
     * Constructor de la clase Tablero.
     * Inicializa el tablero con las piezas en sus posiciones iniciales.
     */
    public Tablero() {
        for (int y = 1; y < 9; y++) {
            for (int x = 1; x < 9; x++) {
                // Fila 1: piezas mayores blancas
                if (y == 1) {
                    if (x == 1 || x == 8) {
                        tablero.put(new Casilla(x, y), new Torre(x, y, false));
                    } else if (x == 2 || x == 7) {
                        tablero.put(new Casilla(x, y), new Caballo(x, y, false));
                    } else if (x == 3 || x == 6) {
                        tablero.put(new Casilla(x, y), new Alfil(x, y, false));
                    } else if (x == 4) {
                        tablero.put(new Casilla(x, y), new Reina(x, y, false));  // La reina en su color (blanca en d1)
                    } else if (x == 5) {
                        reyB = new Rey(x, y, false);
                        tablero.put(new Casilla(x, y), reyB);
                    }
                }
                // Fila 2: peones blancos
                else if (y == 2) {
                    tablero.put(new Casilla(x, y), new Peon(x, y, false));
                }
                // Fila 7: peones negros
                else if (y == 7) {
                    tablero.put(new Casilla(x, y), new Peon(x, y, true));
                }
                // Fila 8: piezas mayores negras
                else if (y == 8) {
                    if (x == 1 || x == 8) {
                        tablero.put(new Casilla(x, y), new Torre(x, y, true));
                    } else if (x == 2 || x == 7) {
                        tablero.put(new Casilla(x, y), new Caballo(x, y, true));
                    } else if (x == 3 || x == 6) {
                        tablero.put(new Casilla(x, y), new Alfil(x, y, true));
                    } else if (x == 4) {
                        tablero.put(new Casilla(x, y), new Reina(x, y, true));  // La reina en su color (negra en d8)
                    } else if (x == 5) {
                        reyN = new Rey(x, y, true);
                        tablero.put(new Casilla(x, y), reyN);
                    }
                }
                // Resto del tablero: casillas vacías
                else {
                    tablero.put(new Casilla(x, y), null);
                }
                tablero.put(new Casilla(0, 0), null);
            }
        }
    }

    /**
     * Verifica si es posible un asedio por parte de las piezas de un color.
     *
     * @param color El color de las piezas que realizan el asedio (true para negro, false para blanco).
     * @param objetivo La casilla del objetivo del asedio.
     * @return true si el asedio es factible, false si no lo es.
     */
    public static boolean asedio(boolean color, Casilla objetivo) {
        boolean factible = false;
        for (Pieza p : tablero.values()) {
            if (p != null) {
                if (p.color == color && mover(p.getCasilla(), objetivo, false)) {
                    System.out.println("La pieza "+p+" amenaza "+objetivo);
                    factible = true;
                }
            }
        }
        return factible;
    }


    /**
     * Verifica si el movimiento de una pieza es válido según las reglas del ajedrez.
     * Si el parámetro "mover" es true, la pieza será movida aunque el movimiento no sea válido
     *
     * @param origen La casilla de origen de la pieza.
     * @param destino La casilla de destino de la pieza.
     * @param mover Si es true, la pieza será movida.
     * @return true si el movimiento es válido, false si no lo es.
     */
    public static boolean mover(Casilla origen, Casilla destino, boolean mover) {
        if (mover && tablero.get(origen) != null) {
            tablero.get(origen).x = destino.x;
            tablero.get(origen).y = destino.y;
            tablero.put(destino, tablero.get(origen));
            tablero.put(origen, null);
            return true;
        }
        if (origen.equals(new Casilla(0, 0)) || destino.equals(new Casilla(0, 0))) {
            return false;
        }
        if (origen.equals(destino)) {
            return false;
        }
        if (tablero.get(origen) != null) {
            List<Casilla> obstaculos = tablero.get(origen).validar(destino);
            //Traslado

            if (obstaculos == null) {
                return false;
            }
            for (Casilla casilla : obstaculos) {
                if (tablero.get(casilla) != null) {
                    return false;
                }
            }
            if (tablero.get(destino) != null) {
                if (tablero.get(origen).color == tablero.get(destino).color) {
                    return false;
                }
            }
            /*
            // Traslado de la pieza provisional
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
            }*/
            return true;
        } else {
            return false;
        }
    }

    /**
     * Realiza el movimiento de una pieza de manera estándar
     *
     * @param origen La casilla de origen de la pieza.
     * @param destino La casilla de destino de la pieza.
     * @return true si el movimiento se realiza con éxito, false si no es posible.
     */
    public boolean mover(Casilla origen, Casilla destino) {
        if (origen.equals(destino)) {
            return false;
        }
        if (origen.equals(new Casilla(0, 0)) || destino.equals(new Casilla(0, 0))) {
            return false;
        }
        if (tablero.get(origen) != null) {
            List<Casilla> obstaculos = tablero.get(origen).validar(destino);
            if (obstaculos == null) {
                return false;
            }
            for (Casilla casilla : obstaculos) {
                if (tablero.get(casilla) != null) {
                    return false;
                }
            }
            if (tablero.get(destino) != null) {
                if (tablero.get(origen).color == tablero.get(destino).color) {
                    return false;
                }
            }

            // Traslado de la pieza
            tablero.get(origen).x = destino.x;
            tablero.get(origen).y = destino.y;
            tablero.put(destino, tablero.get(origen));
            tablero.put(origen, null);

            // Verificar si el rey está en jaque
            if (jaque(obtenerRey(tablero.get(destino).color))) {
                String color = (tablero.get(destino).color) ? "Negras" : "Blancas";
                System.out.println("Tu rey está en jaque (" + color + ")");
                tablero.get(destino).x = origen.x;
                tablero.get(destino).y = origen.y;
                tablero.put(origen, tablero.get(destino));
                tablero.put(destino, null);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Obtiene el rey de un color específico.
     *
     * @param color El color del rey a obtener (true para negro, false para blanco).
     * @return El rey correspondiente al color.
     */
    public static Rey obtenerRey(boolean color) {
        if (color) {
            return reyN;
        } else {
            return reyB;
        }
    }

    /**
     * Verifica si el rey está en jaque.
     *
     * @param victima El rey a verificar si está en jaque.
     * @return true si el rey está en jaque, false si no lo está.
     */
    public static boolean jaque(Rey victima) {
        return asedio(!victima.color, victima.getCasilla());
    }

    /**
     * Verifica si el rey está en jaque mate.
     *
     * @param victima El rey a verificar si está en jaque mate.
     * @return true si el rey está en jaque mate, false si no lo está.
     */
    public boolean jaqueMate(Rey victima) {
        boolean colorVictima = victima.color;
        boolean jaqueMate = true;
        for (Pieza pieza : tablero.values()) {
            if (pieza != null) {
                Casilla origen = pieza.getCasilla();
                if (pieza.color == colorVictima) {
                    for (Casilla casilla : tablero.keySet()) {
                        boolean objetivo = false;
                        if (!casilla.equals(new Casilla(0, 0))) {
                            if (mover(origen, casilla, false)) {
                                if (tablero.get(casilla) != null) {
                                    tablero.put(new Casilla(0, 0), tablero.get(casilla));
                                    objetivo = true;
                                }
                                mover(origen, casilla);
                                if (!jaque(victima)) {
                                    System.out.println(pieza + " " + casilla);
                                    jaqueMate = false;
                                }
                                mover(casilla, origen, true);
                                if (objetivo) {
                                    tablero.put(casilla, tablero.get(new Casilla(0, 0)));
                                    tablero.put(new Casilla(0, 0), null);
                                }
                            }
                        }
                    }
                }
            }
        }
        return jaqueMate;
    }
    public Movimiento bot(boolean color){
        Movimiento mejorMovimiento = null;
        for(Pieza pieza : tablero.values()) {
            if (pieza != null && pieza.color == color) {
                for (Casilla casilla : tablero.keySet()) {
                    if(mover(pieza.getCasilla(),casilla,false)) {
                        Movimiento nuevoMovimiento = new Movimiento(pieza, casilla);
                        if (mejorMovimiento == null || nuevoMovimiento.getValor() > mejorMovimiento.getValor()) {
                            mejorMovimiento = nuevoMovimiento;
                        }
                        if (mejorMovimiento == null || nuevoMovimiento.getValor() == mejorMovimiento.getValor()) {
                            if(Math.random()<0.5) {
                                mejorMovimiento = nuevoMovimiento;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(" Valor movimiento bot "+mejorMovimiento);
        mover(mejorMovimiento.getInicio(true),mejorMovimiento.getObjetivo(true));
        return mejorMovimiento;
    }
}
