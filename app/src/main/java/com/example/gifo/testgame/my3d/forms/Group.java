package com.example.gifo.testgame.my3d.forms;

import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Point3D;
import com.example.gifo.testgame.my3d.renderer.Color;

import java.util.ArrayList;

/**
 * Created by gifo on 20.10.2019.
 * Реализация объекта - Группа (совокупная модель из 3д-примитивов) как наследника класса Object3D
 */

public class Group extends Object3D {

    private float scale = 1f;
    private Point3D position;
    private ArrayList<Polygon> poly = new ArrayList<Polygon>();
    private float rotationX = 0f, rotationY = 0f, rotationZ = 0f;
    private Color color = new Color(255, 128, 128, 128);

    private boolean isOutlined = false;
    private float outlineWeight = 1f;
    private Color outlineColor = new Color(255, 0, 0, 0);

    private Group original = this;

    public Group(Object3D firstObject) {
        addObject(firstObject);
    }

    // Добавление в Группу нового 3д-примитива
    public void addObject(Object3D object) {
        poly.addAll(object.polygons());
        object.merge();
        calcPosition();
    }

    // Авто-пересчёт координат позиции объекта
    private void calcPosition() {
        float xo = 0, yo = 0, zo = 0;
        for (int i = 0; i < poly.size(); i++) {
            xo += poly.get(i).getPosition().x;
            yo += poly.get(i).getPosition().y;
            zo += poly.get(i).getPosition().z;
        }
        position = new Point3D(xo/poly.size(), yo/poly.size(), zo/poly.size());
    }

    @Override
    public void setPositionX(float x) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).moveX(x - position.x);
        position.x = x;
    }

    @Override
    public void setPositionY(float y) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).moveY(y - position.y);
        position.y = y;
    }

    @Override
    public void setPositionZ(float z) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).moveZ(z - position.z);
        position.z = z;
    }

    @Override
    public Point3D getPosition() {
        return position;
    }

    @Override
    public void moveX(float dx) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).moveX(dx);
        position.x += dx;
    }

    @Override
    public void moveY(float dy) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).moveY(dy);
        position.y += dy;
    }

    @Override
    public void moveZ(float dz) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).moveZ(dz);
        position.z += dz;
    }

    // Обнуление динамичных параметров Группы (модели)
    private void restartValues() {
        rotationX = 0;
        rotationY = 0;
        rotationZ = 0;
        scale = 1;
    }

    @Override
    public void setRotationX(float ox) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setGlobalRotationX(ox, getPosition());
        rotationX = ox;
    }

    @Override
    public void setRotationY(float oy) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setGlobalRotationY(oy, getPosition());
        rotationY = oy;
    }

    @Override
    public void setRotationZ(float oz) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setGlobalRotationZ(oz, getPosition());
        rotationZ = oz;
    }

    @Override
    public void setGlobalRotationX(float ox, Point3D position) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setGlobalRotationX(ox, position);
        rotationX = ox;
        calcPosition();
    }

    @Override
    public void setGlobalRotationY(float oy, Point3D position) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setGlobalRotationY(oy, position);
        rotationY = oy;
        calcPosition();
    }

    @Override
    public void setGlobalRotationZ(float oz, Point3D position) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setGlobalRotationZ(oz, position);
        rotationZ = oz;
        calcPosition();
    }

    @Override
    public float getRotationX() {
        return rotationX;
    }

    @Override
    public float getRotationY() {
        return rotationY;
    }

    @Override
    public float getRotationZ() {
        return rotationZ;
    }

    @Override
    public void setScale(float scale) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setGlobalScale(scale, getPosition());
        this.scale = scale;
    }

    @Override
    public void setGlobalScale(float scale, Point3D position) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setGlobalScale(scale, position);
        this.scale = scale;
        calcPosition();
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public ArrayList<Polygon> polygons() {
        return poly;
    }

    @Override
    public void color(int red, int green, int blue) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).color(red, green, blue);
        color.red = red;
        color.green = green;
        color.blue = blue;
    }

    @Override
    public void color(int alpha, int red, int green, int blue) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).color(alpha, red, green, blue);
        color.alpha = alpha;
        color.red = red;
        color.green = green;
        color.blue = blue;
    }

    @Override
    public void setAlpha(int alpha) {
        for (int i = 0; i < poly.size(); i++) {
            int a =  original.polygons().get(i).getAlpha();
            poly.get(i).setAlpha(a * alpha/255);
            Color lineColor = original.polygons().get(i).getOutlineColor();
            poly.get(i).outlineColor(lineColor.alpha * alpha/255,
                    lineColor.red,
                    lineColor.green,
                    lineColor.blue);
        }
        color.alpha = alpha;
    }

    @Override
    public int getAlpha() {
        return color.alpha;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void outline(boolean isOutlined) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).outline(isOutlined);
        this.isOutlined = isOutlined;
    }

    @Override
    public boolean isOutline() {
        return isOutlined;
    }

    @Override
    public void outlineWeight(float px) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).outlineWeight(px);
        outlineWeight = px;
    }

    @Override
    public float getOutlineWeight() {
        return outlineWeight;
    }

    @Override
    public void outlineColor(int alpha, int red, int green, int blue) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).outlineColor(alpha, red, green, blue);
        outlineColor.alpha = alpha;
        outlineColor.red = red;
        outlineColor.green = green;
        outlineColor.blue = blue;
    }

    @Override
    public Color getOutlineColor() {
        return outlineColor;
    }

    @Override
    public void merge() {
        original = (Group) copy();
        restartValues();
    }

    @Override
    public Object3D copy() {
        Group object = null;
        for (int i=0; i<polygons().size(); i++) {
            Polygon poly = polygons().get(i);
            Polygon copy = new Polygon(poly.mesh().get(0).x, poly.mesh().get(0).y, poly.mesh().get(0).z,
                    poly.mesh().get(1).x, poly.mesh().get(1).y, poly.mesh().get(1).z,
                    poly.mesh().get(2).x, poly.mesh().get(2).y, poly.mesh().get(2).z);
            copy.color(poly.getAlpha(),
                    poly.getColor().red,
                    poly.getColor().green,
                    poly.getColor().blue);
            copy.outline(poly.isOutline());
            copy.outlineSpecial(poly.isOutlineSpecial());
            copy.outlineColor(poly.getOutlineColor().alpha,
                    poly.getOutlineColor().red,
                    poly.getOutlineColor().green,
                    poly.getOutlineColor().blue);
            copy.outlineWeight(poly.getOutlineWeight());
            if (i==0) object = new Group(copy);
            else object.addObject(copy);
        }
        object.restartValues();
        return object;
    }
}
