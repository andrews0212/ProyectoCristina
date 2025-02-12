package org.example.ajedrez.modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tablero {
    public static Map<Casilla, Pieza> tablero = new HashMap<Casilla, Pieza>();
    public static Rey reyB;
    public static Rey reyN;

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
            }
        }
    }

    public boolean asedio(boolean color, Casilla objetivo) {
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

    public boolean mover(Casilla origen, Casilla destino, boolean mover) {
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
            if (mover) {
                tablero.get(origen).x = destino.x;
                tablero.get(origen).y = destino.y;
                tablero.put(destino, tablero.get(origen));
                tablero.put(origen, null);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean mover(Casilla origen, Casilla destino) {
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
                System.out.println("Tu rey está en jaque (" + tablero.get(destino).color + "), movimiento no válido");
                tablero.get(destino).x = origen.x;
                tablero.get(destino).y = origen.y;
                tablero.put(origen, tablero.get(destino));
                tablero.put(destino, null);
                return false;
            }
            if(jaque(obtenerRey(!tablero.get(destino).color))) {
                String color;
                if(!tablero.get(destino).color){
                    color= "negro";
                }else{
                    color= "blanco";
                }
                if(jaqueMate(obtenerRey(!tablero.get(destino).color))){
                    System.out.println("Jaque Mate al rey "+color);
                }else{
                    System.out.println("Jaque al rey "+color);
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
        boolean jaqueMate = true;
        for(Pieza p : tablero.values()) {
            if(p!=null) {
                Casilla origen = p.getCasilla();
                if (p.color == victima.color) {
                    for (Casilla casilla : tablero.keySet()) {
                        boolean objetivo = false;
                        Pieza pieza = null;
                        if(tablero.get(casilla)!=null) {
                            pieza = tablero.get(casilla);
                            objetivo=true;
                        }
                        mover(origen, casilla);
                        if (!jaque(victima)) {
                            jaqueMate = false;
                        }
                        mover(casilla, origen, true);
                        if(objetivo){
                            tablero.put(casilla,pieza);
                        }

                    }
                }
            }
        }
        return jaqueMate;
    }
}
