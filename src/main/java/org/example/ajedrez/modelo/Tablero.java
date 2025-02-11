package org.example.ajedrez.modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tablero {
    public static Map<Casilla,Pieza> tablero= new HashMap<Casilla,Pieza>();
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
                        tablero.put(new Casilla(x, y), new Rey(x, y, false));
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
                        tablero.put(new Casilla(x, y), new Rey(x, y, true));
                    }
                }
                // Resto del tablero: casillas vacías
                else {
                    tablero.put(new Casilla(x, y), null);
                }
            }
        }
    }

    public boolean mover(Casilla origen, Casilla destino) {
        if(tablero.get(origen)!=null) {
            try {
                List<Casilla> obstaculos = tablero.get(origen).validar(destino);

            for (Casilla casilla : obstaculos) {
                if (tablero.get(casilla) != null) {
                    return false;
                }
            }
            } catch (Exception e) {
                return false;
            }
            if(tablero.get(destino)!=null) {
                if (tablero.get(origen).color == tablero.get(destino).color) {
                    return false;
                }
            }
            //Traslado
            tablero.get(origen).x=destino.x;
            tablero.get(origen).y=destino.y;
            tablero.put(destino, tablero.get(origen));
            tablero.put(origen, null);
            return true;
        }else{
            return false;
        }
    }
}
