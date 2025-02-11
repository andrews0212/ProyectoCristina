package org.example.ajedrez.modelo;

import javafx.scene.image.ImageView;

import java.util.List;

public class Pieza {
    public int x;
    public int y;
    public boolean color;

    public Pieza(int x, int y, boolean color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public List<Casilla> validar(Casilla casilla) {
        return null;
    }
}
