package com.example.gifo.testgame;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Point3D;
import com.example.gifo.testgame.my3d.Scene3D;
import com.example.gifo.testgame.my3d.forms.Box;
import com.example.gifo.testgame.my3d.renderer.Renderer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new View3D(this));
    }


    // Вьюпорт для 3д - представления
    class View3D extends View {

        public View3D(Context context) {
            super(context);
            initScene();
        }

        Scene3D scene;
        Box cube, trek1, trek2, trek3;
        Renderer render;
        float angle = 0, scale=1, znak=1;

        // Инициализация сцены
        protected void initScene() {
            scene = new Scene3D();

            cube = new Box(25,25,25,0,0,0);
            cube.color(255,128,128,128);
            cube.colorFront(255,255,0,0);
            cube.colorRight(255,0,0,255);
            cube.colorLeft(255, 0, 255, 0);
            cube.colorTop(255, 16, 16, 16);
            cube.colorBottom(255, 196, 196, 196);

            trek1 = new Box(5,5,5, -50, 50, 0);
            trek1.color(128, 255, 0, 0);

            trek2 = new Box(5,5,5, 50, 50, 0);
            trek2.color(128, 0, 255, 0);

            trek3 = new Box(5,5,5, 0, -50, 0);
            trek3.color(128, 0, 0, 255);

            scene.add((Object3D) cube);
            scene.add((Object3D) trek1);
            scene.add((Object3D) trek2);
            scene.add((Object3D) trek3);

            render = new Renderer(scene, 1080, 1512);
            render.narrowCamera = 85;
            render.zoom = 7;
        }

        // Цикличная прорисовка сцены
        @Override
        protected void onDraw(Canvas canvas) {
            cube.setScale(scale);
            cube.setRotation(angle, angle, angle);

            trek1.setGlobalRotationX(angle, new Point3D(0, 0, 0));
            trek1.setGlobalRotationY(angle, new Point3D(0, 0, 0));

            trek2.setGlobalRotationX(angle, new Point3D(0, 0, 0));
            trek2.setGlobalRotationY(-angle, new Point3D(0, 0, 0));

            trek3.setGlobalRotationX(angle, new Point3D(0, 0, 0));

            render.render(canvas);

            angle += 0.5;
            scale+=0.005*znak;
            if (scale>1.618) znak*=-1;
            if (scale<1) znak*=-1;

            invalidate();
        }
    }
}
