package com.example.gifo.testgame.my3d.renderer;

/**
 * Created by gifo on 17.10.2019.
 * Абстракция - цвет ARGB
 */

public class Color {

    public int alpha = 255;
    public int red = 0;
    public int green = 0;
    public int blue = 0;

    public Color(int alpha, int red, int green, int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}
