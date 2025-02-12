package org.example.ajedrez.modelo;

public class Casilla {
    public int x;
    public int y;

    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Casilla other = (Casilla) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "Casilla{" + "x=" + x + ", y=" + y + '}';
    }
}
