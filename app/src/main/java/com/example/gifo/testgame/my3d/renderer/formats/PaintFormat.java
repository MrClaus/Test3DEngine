package com.example.gifo.testgame.my3d.renderer.formats;

/**
 * Created by gifo on 29.10.2019.
 */

public enum PaintFormat {
    CLEARANCE_PLANE_SHADING,    // включает покрытие прощелин между полигонами на плоскости
    NO_CLEARANCE_PLANE_SHADING, // отключает CLEARANCE_PLANE_SHADING
    BITMAP_FILTER,              // включает билинейную интерполяцию изображений при масштабировании
    DITHER_DRAW,                // включает сглаживание при блиттинге
    ANTI_ALIAS                  // включает сглаживание на всех операциях прорисовки
}
