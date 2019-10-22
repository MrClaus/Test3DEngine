package com.example.gifo.testgame;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.gifo.testgame.models.Spaceship;
import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Scene3D;
import com.example.gifo.testgame.my3d.forms.Box;
import com.example.gifo.testgame.my3d.forms.Plane;
import com.example.gifo.testgame.my3d.renderer.Renderer;

/**
 * Created by gifo on 22.10.2019.
 */

public class SurfaceView3D extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    public SurfaceView3D(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(holder);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class DrawThread extends Thread {

        private boolean running = false;
        private SurfaceHolder surfaceHolder;

        Scene3D scene;
        Renderer render;
        Object3D spaceship;
        Box box;
        float angle = 0;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            init();
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        void init() {
            scene = new Scene3D();
            spaceship = new Spaceship().getObject();
            spaceship.setPosition(0,-16,0);
            scene.add(spaceship);

            Plane plane = new Plane(64, 64, 0, -50, 0);
            plane.setRotationX(90);
            plane.outlinePlane(true);
            plane.outlineWeight(2);
            plane.color(255, 255, 255, 0);
            scene.add(plane);

            box = new Box(10, 10, 10, 0, 20, 0);
            box.color(255, 200, 200, 200);
            box.outlineBox(true);
            scene.add(box);

            render = new Renderer(scene, 1080, 1512);
            render.narrowCamera = 85;
            render.zoom = 8;
        }

        void onDraw(Canvas canvas) {

            box.setRotation(angle, angle, angle);
            spaceship.setRotationY(angle);
            angle += 1.0f;

            render.render(canvas);
        }

        @Override
        public void run() {
            Canvas canvas = null;
            int fps = 60, counter = 0;
            long frameTime = System.currentTimeMillis();

            while (running) {
                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas == null) {
                        Thread.sleep(1);
                        continue;
                    }
                    synchronized (surfaceHolder) {
                        onDraw(canvas);
                    }
                } catch (Exception e) { e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);

                        // Подсчёт фпс
                        if (System.currentTimeMillis() - frameTime < 1000) counter++;
                        else {
                            fps = counter;
                            counter = 0;
                            frameTime = System.currentTimeMillis();
                            System.out.println("Viev3D-frame FPS: " + fps);
                        }
                    }
                }
            }
        }
    }
}
