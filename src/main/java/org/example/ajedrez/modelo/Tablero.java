package org.example.ajedrez.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tablero {
    public static Map<Casilla, Pieza> tablero = new HashMap<Casilla, Pieza>();
    public static Rey reyB;
    public static Rey reyN;
    public static ArrayList<Rey> reys = new ArrayList<Rey>();

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

    public static boolean asedio(boolean color, Casilla objetivo) {
        boolean factible = false;
        for (Pieza p : tablero.values()) {
            if (p != null) {
                if (p.color == color && moverAsedio(p.getCasilla(), objetivo, false)) {
                    factible = true;
                }
            }
        }
        return factible;
    }

    public static boolean moverAsedio(Casilla origen, Casilla destino, boolean mover) {
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
            return true;
        } else {
            return false;
        }
    }

    //Con este metodo podemos obtener el booleano sin mover o mover sin evaluar
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

    public boolean mover(Casilla origen, Casilla destino) {
        System.out.println(tablero.get(origen));
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

            //Traslado
            tablero.get(origen).x = destino.x;
            tablero.get(origen).y = destino.y;
            tablero.put(destino, tablero.get(origen));
            tablero.put(origen, null);
            if (jaque(obtenerRey(tablero.get(destino).color))) {
                String color;
                if (tablero.get(destino).color) {
                    color = "Negras";
                } else {
                    color = "Blancas";
                }
                System.out.println("Tu rey está en jaque (" + color + ")");
                tablero.get(destino).x = origen.x;
                tablero.get(destino).y = origen.y;
                tablero.put(origen, tablero.get(destino));
                tablero.put(destino, null);
                return false;
            }
            if (jaque(obtenerRey(!tablero.get(destino).color))) {
                String color;
                if (!tablero.get(destino).color) {
                    color = "negro";
                } else {
                    color = "blanco";
                }
                if (jaqueMate(obtenerRey(!tablero.get(destino).color))) {
                    System.out.println("Jaque Mate al rey " + color);
                } else {
                    System.out.println("Jaque al rey " + color);
                }
            }
            return true;
        }
        return false;
    }

    public Rey obtenerRey(boolean color) {
        if (color) {
            return reyN;
        } else {
            return reyB;
        }
    }

    //devuelve true si el rey por parametro esta en jaque
    public boolean jaque(Rey victima) {
        return asedio(!victima.color, victima.getCasilla());
    }

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

}

