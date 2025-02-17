package org.example.ajedrez.modelo;

import javafx.scene.control.Alert;

import java.util.*;

/**
 * La clase Tablero representa el tablero de ajedrez en el que se juega la partida.
 * Contiene la lógica para realizar movimientos de las piezas, verificar condiciones de jaque y jaque mate,
 * y gestionar las piezas en el tablero.
 *
 * @author Raul Mora
 * @version 1.0
 * @since 1.0
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
     * Lista que contiene ambos reyes.
     */
    public static ArrayList<Rey> reys = new ArrayList<>();

    /**
     * Constructor de la clase Tablero.
     * Inicializa el tablero con las piezas en sus posiciones iniciales.
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
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
            //Apartamos la pieza en objetivo por si hay que devolverla
            tablero.put(new Casilla(0, 0), tablero.get(destino));
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
                //Recuperamos la pieza de destino de 0,0
                tablero.put(destino, tablero.get(new Casilla(0, 0)));
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
     * @since 1.0
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
     * @since 1.0
     */
    public boolean jaque(Rey victima) {
        return asedio(!victima.color, victima.getCasilla());
    }

    /**
     * Verifica si el rey está en jaque mate.
     *
     * @param victima El rey a verificar si está en jaque mate.
     * @return true si el rey está en jaque mate, false si no lo está.
     * @since 1.0
     */
    public boolean jaqueMate(Rey victima) {
        boolean colorVictima = victima.color;
        boolean jaqueMate = true;

        // Primero, verificar si el rey está en jaque
        if (!jaque(victima)) {
            return false; // Si no está en jaque, no puede ser jaque mate
        }

        // Iterar sobre todas las piezas del color del rey
        for (Pieza pieza : new ArrayList<>(tablero.values())) { // Usar copia para evitar ConcurrentModification
            if (pieza != null && pieza.color == colorVictima) {
                Casilla origen = pieza.getCasilla();
                // Iterar sobre todas las casillas posibles
                for (Casilla destino : tablero.keySet()) {
                    if (destino.equals(new Casilla(0, 0))) continue;

                    // Verificar si el movimiento es válido según las reglas de la pieza (sin considerar jaque)
                    if (mover(origen, destino, false)) {
                        // Guardar estado original
                        Pieza piezaDestinoOriginal = tablero.get(destino);
                        Pieza piezaOrigenOriginal = tablero.get(origen);

                        // Simular el movimiento: mover la pieza
                        tablero.put(destino, piezaOrigenOriginal);
                        tablero.put(origen, null);
                        pieza.x = destino.x;
                        pieza.y = destino.y;

                        // Verificar si el rey sigue en jaque después del movimiento
                        if (!jaque(victima)) {
                            jaqueMate = false;
                        }

                        // Restaurar el movimiento
                        pieza.x = origen.x;
                        pieza.y = origen.y;
                        tablero.put(origen, piezaOrigenOriginal);
                        tablero.put(destino, piezaDestinoOriginal);

                        // Si encontramos un movimiento que evita el jaque, salir temprano
                        if (!jaqueMate) {
                            return false;
                        }
                    }
                }
            }
        }
        return jaqueMate;
    }
    //Los movimientos son teóricos de cada pieza no consideran el contexto del tablero
    ArrayList<Movimiento> movimientosPosibles = new ArrayList<>();
    /**
     * Realiza el movimiento del bot de una pieza en el tablero.
     * @param color
     * @return El movimiento realizado.
     * @since 1.0
     */
    public Movimiento bot(boolean color){
        Movimiento mejorMovimiento = null;
        for(Pieza pieza : tablero.values()) {
            if (pieza != null && pieza.color == color) {
                for (Casilla casilla : tablero.keySet()) {
                    if(mover(pieza.getCasilla(),casilla,false)){
                        Movimiento nuevoMovimiento = new Movimiento(pieza, casilla);
                        movimientosPosibles.add(nuevoMovimiento);
                    }
                }
            }
        }
        movimientosPosibles.sort(new Comparator<Movimiento>() {
            @Override
            public int compare(Movimiento o1, Movimiento o2) {
                if(o1.getValor()==o2.getValor()){
                    return Math.random()>0.5?1:-1;
                }
                return o2.getValor()-o1.getValor();
            }
        });
        System.out.println(movimientosPosibles.toString());
        for(Movimiento movimiento : movimientosPosibles) {
            if (mover(movimiento.getInicio(true), movimiento.getObjetivo(true))) {
                movimientosPosibles.clear();
                return movimiento;
            }
        }
        return null;
    }
}
