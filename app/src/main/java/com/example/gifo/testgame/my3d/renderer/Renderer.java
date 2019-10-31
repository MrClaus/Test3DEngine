package com.example.gifo.testgame.my3d.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.gifo.testgame.my3d.Scene3D;
import com.example.gifo.testgame.my3d.forms.Polygon;
import com.example.gifo.testgame.my3d.renderer.formats.PaintFormat;

import java.util.Collections;

/**
 * Created by gifo on 17.10.2019.
 * Renderer - прорисовка 3д-сцены
 */

public class Renderer {

    private Scene3D scene;
    private float width, height;
    private float x = 0f, y = 0f, xCenter, yCenter;
    private float[] xo = new float[4], yo = new float[4];
    private Path path = new Path();
    private Paint paint = new Paint();
    private boolean clearancePlaneShading = false;
    private Color clearColor = new Color(255, 255, 255, 255);

    public float zoom = 1f;             // масштаб прорисовки кадра
    public float flatCamera = 1f;       // степень плоскости отображения 3д
    public float narrowCamera = 150f;   // степень узости отображения 3д
    public float zHide = -10f;          // позиция Z сокрытия объектов за наблюдателем

    public Renderer(Scene3D scene, float width, float height) {
        this.scene = scene;
        this.width = width;
        this.height = height;
        calcCenter();
    }

    // Устанавливает формат графического отображения рендера
    public void setFormat(PaintFormat format) {
        switch (format) {
            case CLEARANCE_PLANE_SHADING:
                clearancePlaneShading = true;
                break;
            case NO_CLEARANCE_PLANE_SHADING:
                clearancePlaneShading = false;
                break;
            case ANTI_ALIAS:
                paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                break;
            case DITHER_DRAW:
                paint = new Paint(Paint.DITHER_FLAG);
                break;
            case BITMAP_FILTER:
                paint = new Paint(Paint.FILTER_BITMAP_FLAG);
                break;
        }
    }

    // Устанавливает позицию видимого окна на экране
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        calcCenter();
    }

    // Пересчёт координат поля видимости рендера
    private void calcCenter() {
        xCenter = x + width/2;
        yCenter = y + height/2;
        xo[0] = x;          yo[0] = y;
        xo[1] = x + width;  yo[1] = y;
        xo[2] = x + width;  yo[2] = y + height;
        xo[3] = x;          yo[3] = y + height;
    }

    // Возвращает текущую позицию X поля видимости рендера
    public float getPositionX() {
        return this.x;
    }

    // Возвращает текущую позицию Y поля видимости рендера
    public float getPositionY() {
        return this.y;
    }

    // Возвращает ширину поля видимости рендера
    public float width() {
        return this.width;
    }

    // Возвращает высоту поля видимости рендера
    public float height() {
        return this.height;
    }

    // Задаёт цвет очистки экрана
    public void clearColor(int red, int green, int blue) {
        clearColor.red = red;
        clearColor.green = green;
        clearColor.blue = blue;
    }

    // Алгоритм ранжирования полигонов сцены
    private void zBuffering() {
        Collections.sort(scene.polygons());
    }

    // Алгоритм определения видимости полигонов относительно поля видимости рендера
    private boolean isVisiblePolygon(float x1, float y1, float x2, float y2, float x3, float y3) {
        // 1. Принадлежность точек полигона полю видимости
        boolean isVisiblePoint =  ((x1 >= x && x1 < x + width) && (y1 >= y && y1 < y + height) ||
                (x2 >= x && x2 < x + width) && (y2 >= y && y2 < y + height) ||
                (x3 >= x && x3 < x + width) && (y3 >= y && y3 < y + height)) ? true : false;
        if (!isVisiblePoint)  {
            // 2. Пересечение отрезков полигона с отрезками поля видимости
            float[] xx = {x1, x2, x3}, yy = {y1, y2, y3};
            for (int k = 0; k < 3; k++) {
                for (int n = 0; n < 4; n++) {
                    isVisiblePoint = intersection(xx[k], yy[k], xx[(k==2)?0:k+1], yy[(k==2)?0:k+1],
                            xo[n], yo[n], xo[(n==3)?0:n+1], yo[(n==3)?0:n+1]);
                    if (isVisiblePoint) {
                        n = 3;
                        k = 2;
                    }
                }
            }
            if (!isVisiblePoint) {
                // 3. Принадлежность точек поля видимости полигону
                for (int i=0; i<4; i++) {
                    float check1 = (x1 - xo[i])*(y2 - y1) - (x2 - x1)*(y1 - yo[i]);
                    float check2 = (x2 - xo[i])*(y3 - y2) - (x3 - x2)*(y2 - yo[i]);
                    float check3 = (x3 - xo[i])*(y1 - y3) - (x1 - x3)*(y3 - yo[i]);
                    if (Math.signum(check1) == Math.signum(check2)
                            && Math.signum(check1) == Math.signum(check3)
                            && check1 != 0f) {
                        isVisiblePoint = true;
                        break;
                    }
                }
            }
        }
        return isVisiblePoint;
    }

    // Возвращает true, если два заданных отрезка пересекаются
    private boolean intersection(float x1, float y1, float x2, float y2,
                                 float xx1, float yy1, float xx2, float yy2) {
        float v1, v2, v3, v4;
        v1 = (xx2 - xx1)*(y1 - yy1) - (yy2 - yy1)*(x1 - xx1);
        v2 = (xx2 - xx1)*(y2 - yy1) - (yy2 - yy1)*(x2 - xx1);
        v3 = (x2 - x1)*(yy1 - y1) - (y2 - y1)*(xx1 - x1);
        v4 = (x2 - x1)*(yy2 - y1) - (y2 - y1)*(xx2 - x1);
        return (v1 * v2 < 0) && (v3 * v4 < 0);
    }

    // Отрисвка кадра
    private void draw(Canvas canvas, Polygon polygon) {
        if (polygon.getPosition().z > zHide) {
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
                paint.setARGB(color.alpha, color.red, color.green, color.blue);

                path.reset();
                path.moveTo(x1, y1);
                path.lineTo(x2, y2);
                path.lineTo(x3, y3);

                canvas.drawPath(path, paint);

                if (clearancePlaneShading) {
                    paint.setStrokeWidth(2);
                    canvas.drawLine(x1, y1, x3, y3, paint);
                }

                if (polygon.isOutline()) {
                    Color lineColor = polygon.getOutlineColor();
                    paint.setARGB(lineColor.alpha, lineColor.red, lineColor.green, lineColor.blue);
                    paint.setStrokeWidth(polygon.getOutlineWeight());
                    canvas.drawLine(x1, y1, x2, y2, paint);
                    canvas.drawLine(x2, y2, x3, y3, paint);
                    if (!polygon.isOutlineSpecial()) canvas.drawLine(x3, y3, x1, y1, paint);
                }
            }
        }
    }

    // Отрисовка сцены
    public void render(Canvas canvas) {
        zBuffering();
        canvas.drawRGB(clearColor.red, clearColor.green, clearColor.blue);
        for (int i = 0; i < scene.polygons().size(); i++) draw(canvas, scene.polygons().get(i));
    }
}
