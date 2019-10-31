package com.example.gifo.testgame.my3d;

import com.example.gifo.testgame.my3d.forms.Polygon;
import com.example.gifo.testgame.my3d.renderer.Color;

import java.util.ArrayList;

/**
 * Created by gifo on 15.10.2019.
 * Абстрактный класс-родитель 3д-объектов
 */

public abstract class Object3D {

    public abstract ArrayList<Polygon> polygons();  // возвращает массив полигонов текущего объекта

    public abstract Point3D getPosition();          // возвращает позицию текущего объекта в xyz

    // Задаёт позицию для текущего объекта в xyz
    public void setPosition(float x, float y, float z) {
        setPositionX(x);
        setPositionY(y);
        setPositionZ(z);
    }

    public abstract void setPositionX(float x); // задаёт позицию для текущего объекта в x
    public abstract void setPositionY(float y); // задаёт позицию для текущего объекта в y
    public abstract void setPositionZ(float z); // задаёт позицию для текущего объекта в z

    // Относительное перемещение позиции текущего объекта в xyz
    public void move(float dx, float dy, float dz) {
        moveX(dx);
        moveY(dy);
        moveZ(dz);
    }

    public abstract void moveX(float dx); // относительное перемещение позиции текущего объекта в x
    public abstract void moveY(float dy); // относительное перемещение позиции текущего объекта в y
    public abstract void moveZ(float dz); // относительное перемещение позиции текущего объекта в z

    // Задаёт углы поворота объекта вокруг локальных осей OX OY OZ
    public void setRotation(float ox, float oy, float oz) {
        setRotationX(ox);
        setRotationY(oy);
        setRotationZ(oz);
    }

    // Задаёт угл поворота объекта вокруг локальной оси OX
    public void setRotationX(float ox) {
        setGlobalRotationX(ox, getPosition());
    }

    // Задаёт угл поворота объекта вокруг локальной оси OY
    public void setRotationY(float oy) {
        setGlobalRotationY(oy, getPosition());
    }

    // Задаёт угл поворота объекта вокруг локальной оси OZ
    public void setRotationZ(float oz) {
        setGlobalRotationZ(oz, getPosition());
    }

    public abstract float getRotationX(); // возвращает угол поворота объекта вокруг оси OX
    public abstract float getRotationY(); // возвращает угол поворота объекта вокруг оси OY
    public abstract float getRotationZ(); // возвращает угол поворота объекта вокруг оси OZ

    // Осуществляет поворот объекта вокруг оси OX относительно заданной точки
    public void setGlobalRotationX(float ox, Point3D position) {
        Polygon pl = ((Polygon) this);
        for (int i = 0; i < pl.mesh().size(); i++) {
            double theta = Math.atan2(pl.mesh().get(i).y - position.y, pl.mesh().get(i).z - position.z);
            theta += Math.PI/2.0;
            double currentAngle = Math.toDegrees(theta);
            if (currentAngle < 0) currentAngle += 360;
            double toAngle = Math.toRadians(currentAngle + ox - pl.getRotationX());
            double rotationRadius = Math.sqrt(Math.pow((pl.mesh().get(i).y - position.y), 2d) +
                    Math.pow((pl.mesh().get(i).z - position.z), 2d));
            pl.mesh().get(i).z = (float) (position.z + rotationRadius * Math.sin(toAngle));
            pl.mesh().get(i).y = (float) (position.y - rotationRadius * Math.cos(toAngle));
        }
    }

    // Осуществляет поворот объекта вокруг оси OY относительно заданной точки
    public void setGlobalRotationY(float oy, Point3D position) {
        Polygon pl = ((Polygon) this);
        for (int i = 0; i < pl.mesh().size(); i++) {
            double theta = Math.atan2(pl.mesh().get(i).z - position.z, pl.mesh().get(i).x - position.x);
            theta += Math.PI/2.0;
            double currentAngle = Math.toDegrees(theta);
            if (currentAngle < 0) currentAngle += 360;
            double toAngle = Math.toRadians(currentAngle + oy - pl.getRotationY());
            double rotationRadius = Math.sqrt(Math.pow((pl.mesh().get(i).z - position.z), 2d) +
                    Math.pow((pl.mesh().get(i).x - position.x), 2d));
            pl.mesh().get(i).x = (float) (position.x + rotationRadius * Math.sin(toAngle));
            pl.mesh().get(i).z = (float) (position.z - rotationRadius * Math.cos(toAngle));
        }
    }

    // Осуществляет поворот объекта вокруг оси OZ относительно заданной точки
    public void setGlobalRotationZ(float oz, Point3D position) {
        Polygon pl = ((Polygon) this);
        for (int i = 0; i < pl.mesh().size(); i++) {
            double theta = Math.atan2(pl.mesh().get(i).y - position.y, pl.mesh().get(i).x - position.x);
            theta += Math.PI/2.0;
            double currentAngle = Math.toDegrees(theta);
            if (currentAngle < 0) currentAngle += 360;
            double toAngle = Math.toRadians(currentAngle + oz - pl.getRotationZ());
            double rotationRadius = Math.sqrt(Math.pow((pl.mesh().get(i).y - position.y), 2d) +
                    Math.pow((pl.mesh().get(i).x - position.x), 2d));
            pl.mesh().get(i).x = (float) (position.x + rotationRadius * Math.sin(toAngle));
            pl.mesh().get(i).y = (float) (position.y - rotationRadius * Math.cos(toAngle));
        }
    }

    // Задаёт значение масштаба для текущего объекта
    public void setScale(float scale) {
        setGlobalScale(scale, getPosition());
    }

    // Возвращает значение масштаба текущего объекта
    public abstract float getScale(); // возвращает текущее значение масштаба объекта

    // Осуществляет масштабирование текущего объекта относительно заданной точки
    public void setGlobalScale(float scale, Point3D position) {
        Polygon pl = ((Polygon) this);
        float scaled = scale / getScale();
        for(int i = 0; i < pl.mesh().size(); i++) {
            pl.mesh().get(i).x = (pl.mesh().get(i).x - position.x) * scaled + position.x;
            pl.mesh().get(i).y = (pl.mesh().get(i).y - position.y) * scaled + position.y;
            pl.mesh().get(i).z = (pl.mesh().get(i).z - position.z) * scaled + position.z;
        }
    }

    public abstract void color(int red, int green, int blue);               // задаёт цвет объекту RGB
    public abstract void color(int alpha, int red, int green, int blue);    // задаёт цвет объекту ARGB
    public abstract Color getColor();           // возвращает цвет текущего объекта
    public abstract void setAlpha(int alpha);   // задаёт прозрачность текущему объекту
    public abstract int getAlpha();             // возвращает значение прозрачности текущего объекта

    public abstract void outline(boolean isOutlined);   // задаёт контур объекту по-умолчанию
    public abstract boolean isOutline();                // возвращает true, если объекту задан контур
    public abstract void outlineWeight(float px);       // задаёт толщину контура в пикселях
    public abstract void outlineColor(int alpha, int red, int green, int blue); // задаёт цвет контура ARGB
    public abstract float getOutlineWeight();           // возвращает значение толщины контура
    public abstract Color getOutlineColor();            // возвращает цвет контура

    public abstract Object3D copy();    // возвращает копию текущего объекта
    public abstract void merge();       // осуществляет сборку объекта как готовой совокупности после всех изменений
}
