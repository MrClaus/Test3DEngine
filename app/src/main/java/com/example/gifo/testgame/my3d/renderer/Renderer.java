package com.example.gifo.testgame.my3d.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.gifo.testgame.my3d.Scene3D;
import com.example.gifo.testgame.my3d.forms.Polygon;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by gifo on 17.10.2019.
 */

public class Renderer {

    private Scene3D scene;
    private float width, height;
    private float x = 0f, y = 0f, xCenter, yCenter;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Color clearColor = new Color(255, 255, 255, 255);

    public float zoom = 1f;
    public float flatCamera = 1f;
    public float narrowCamera = 150f;

    private static final Comparator<Polygon> Z_BUFFER_SORT = new Comparator<Polygon>() {
        @Override
        public int compare(Polygon lhs, Polygon rhs) {
            return (int) (lhs.getPosition().z - rhs.getPosition().z);
        }
    };

    public Renderer(Scene3D scene, float width, float height) {
        this.scene = scene;
        this.width = width;
        this.height = height;
        calcCenter();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        calcCenter();
    }

    private void calcCenter() {
        xCenter = x + width/2;
        yCenter = y + height/2;
    }

    public float getPositionX() {
        return this.x;
    }

    public float getPositionY() {
        return this.y;
    }

    public float width() {
        return this.width;
    }

    public float height() {
        return this.height;
    }

    public void clearColor(int red, int green, int blue) {
        clearColor.red = red;
        clearColor.green = green;
        clearColor.blue = blue;
    }

    private void zBuffering() {
        Collections.sort(scene.polygons(), Collections.reverseOrder(Renderer.Z_BUFFER_SORT));
    }

    private boolean isVisiblePolygon(float x1, float y1, float x2, float y2, float x3, float y3) {
        return ((x1 >= x && x1 < x + width) && (y1 >= y && y1 < y + height) ||
                (x2 >= x && x2 < x + width) && (y2 >= y && y2 < y + height) ||
                (x3 >= x && x3 < x + width) && (y3 >= y && y3 < y + height)) ? true : false;
    }

    private void draw(Canvas canvas, Polygon polygon) {
        if (polygon.getPosition().z > -100) {
            float z_deform;
            z_deform = (flatCamera / (flatCamera + polygon.mesh().get(0).z / narrowCamera));
            float x1 = xCenter - zoom * polygon.mesh().get(0).x * z_deform;
            float y1 = yCenter - zoom * polygon.mesh().get(0).y * z_deform;

            z_deform = (flatCamera / (flatCamera + polygon.mesh().get(1).z / narrowCamera));
            float x2 = xCenter - zoom * polygon.mesh().get(1).x * z_deform;
            float y2 = yCenter - zoom * polygon.mesh().get(1).y * z_deform;

            z_deform = (flatCamera / (flatCamera + polygon.mesh().get(2).z / narrowCamera));
            float x3 = xCenter - zoom * polygon.mesh().get(2).x * z_deform;
            float y3 = yCenter - zoom * polygon.mesh().get(2).y * z_deform;

            if (isVisiblePolygon(x1, y1, x2, y2, x3, y3)) {
                Color color = polygon.getColor();
                this.paint.setARGB(color.alpha, color.red, color.green, color.blue);

                Path path = new Path();
                path.moveTo(x1, y1);
                path.lineTo(x2, y2);
                path.lineTo(x3, y3);

                canvas.drawPath(path, this.paint);
            }
        }
    }

    public void render(Canvas canvas) {
        zBuffering();
        canvas.drawRGB(clearColor.red, clearColor.green, clearColor.blue);
        for (int i = 0; i < scene.polygons().size(); i++) draw(canvas, scene.polygons().get(i));
    }
}
