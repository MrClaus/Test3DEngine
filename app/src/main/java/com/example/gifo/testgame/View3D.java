package com.example.gifo.testgame;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.example.gifo.testgame.models.Spaceship;
import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Scene3D;
import com.example.gifo.testgame.my3d.forms.Box;
import com.example.gifo.testgame.my3d.forms.Plane;
import com.example.gifo.testgame.my3d.renderer.Renderer;

import java.util.ArrayList;

/**
 * Created by gifo on 22.10.2019.
 */

public class View3D extends View {

    public View3D(Context context) {
        super(context);
        initScene();
    }

    Scene3D scene;
    Renderer render;
    Object3D spaceship;
    Box box;
    float angle = 0;

    // Инициализация сцены
    protected void initScene() {
        scene = new Scene3D();

        spaceship = new Spaceship().getObject();
        spaceship.setPosition(0,-16,0);
        spaceship.merge();

        Plane plane = new Plane(64, 64, 0, -45, 0);
        plane.setRotationX(90);
        plane.outlinePlane(true);
        plane.outlineWeight(2);
        plane.color(255, 255, 255, 0);
        plane.merge();

        box = new Box(10, 10, 10, 0, 20, 0);
        box.color(255, 200, 200, 200);
        box.outlineBox(true);
        box.merge();

        scene.add(spaceship);
        scene.add(plane);
        scene.add(box);

        render = new Renderer(scene, 1080, 1512);
        render.narrowCamera = 85;
        render.zoom = 8;
    }

    int fps = 60, counter = 0;
    long frameTime = System.currentTimeMillis();

    // Цикличная прорисовка сцены
    @Override
    protected void onDraw(Canvas canvas) {
        angle += 1.0f;

        box.setRotation(angle, angle, angle);
        spaceship.setRotationY(angle);

        render.render(canvas);

        // Подсчёт фпс
        if (System.currentTimeMillis() - frameTime < 1000) counter++;
        else {
            fps = counter;
            counter = 0;
            frameTime = System.currentTimeMillis();
            System.out.println("Viev3D-frame FPS: " + fps);
        }

        invalidate();
    }
}
