package com.example.gifo.testgame.myPhysics.actions;

import com.example.gifo.testgame.my3d.Object3D;

/**
 * Created by gifo on 26.10.2019.
 * PhysicalAction - интерфейс-список физических явлений, которые реализует физический движок
 */

public interface PhysicalAction {
    void bang(Object3D object, float speed);        // взрыв объекта в координатах xy
    //void bang3d(Object3D object, float speed);    // взрыв объекта в координатах xyz
    void fade(Object3D object, int milliseconds);   // исчезновение объекта
}
