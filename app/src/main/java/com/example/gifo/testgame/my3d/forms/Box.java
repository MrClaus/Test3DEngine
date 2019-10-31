package com.example.gifo.testgame.my3d.forms;

import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Point3D;
import com.example.gifo.testgame.my3d.renderer.Color;

import java.util.ArrayList;

/**
 * Created by gifo on 16.10.2019.
 * Реализация объекта - Бокс (параллелепипед) как наследника класса Object3D
 */

public class Box extends Object3D {

    private float scale = 1f;
    private Point3D position;
    private ArrayList<Polygon> poly = new ArrayList<Polygon>();
    private float rotationX = 0f, rotationY = 0f, rotationZ = 0f;
    private Plane planeRight, planeLeft, planeBottom, planeTop, planeFront, planeBehind;
    private Color color = new Color(255, 128, 128, 128);

    private boolean isOutlined = false, isBoxOutlined = false;
    private float outlineWeight = 1f;
    private Color outlineColor = new Color(255, 0, 0, 0);

    private Box original = this;
    private float width, height, length;

    public Box(float width, float height, float length,
               float x, float y, float z) {

        this.width = Math.abs(width);
        this.height = Math.abs(height);
        this.length = Math.abs(length);

        planeRight = new Plane(this.length, this.height, x - this.width/2, y, z);
        planeRight.setRotationY(90);

        planeLeft = new Plane(this.length, this.height, x + this.width/2, y, z);
        planeLeft.setRotationY(-90);

        planeBottom = new Plane(this.width, this.length, x, y - this.height/2, z);
        planeBottom.setRotationX(90);

        planeTop = new Plane(this.width, this.length, x, y + this.height/2, z);
        planeTop.setRotationX(-90);

        planeFront = new Plane(this.width, this.height, x, y, z - this.length/2);
        planeBehind = new Plane(this.width, this.height, x, y, z + this.length/2);
        planeBehind.setRotationY(180);

        poly.addAll(planeRight.polygons());
        poly.addAll(planeLeft.polygons());
        poly.addAll(planeBottom.polygons());
        poly.addAll(planeTop.polygons());
        poly.addAll(planeFront.polygons());
        poly.addAll(planeBehind.polygons());
        for (int i = 0; i < poly.size(); i++) poly.get(i).merge();

        calcPosition();
    }

    // Авто-пересчёт координат позиции объекта
    private void calcPosition() {
        position = new Point3D((planeRight.getPosition().x + planeLeft.getPosition().x +
                planeBottom.getPosition().x + planeTop.getPosition().x +
                planeFront.getPosition().x + planeBehind.getPosition().x)/6,

                (planeRight.getPosition().y + planeLeft.getPosition().y +
                        planeBottom.getPosition().y + planeTop.getPosition().y +
                        planeFront.getPosition().y + planeBehind.getPosition().y)/6,

                (planeRight.getPosition().z + planeLeft.getPosition().z +
                        planeBottom.getPosition().z + planeTop.getPosition().z +
                        planeFront.getPosition().z + planeBehind.getPosition().z)/6);
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

    // Обнуление динамичных параметров Бокса
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

    // Изменение цвета правой плоскости Бокса
    public void colorRight(int alpha, int red, int green, int blue) {
        planeRight.color(alpha, red, green, blue);
    }

    // Изменение цвета левой плоскости Бокса
    public void colorLeft(int alpha, int red, int green, int blue) {
        planeLeft.color(alpha, red, green, blue);
    }

    // Изменение цвета нижней плоскости Бокса
    public void colorBottom(int alpha, int red, int green, int blue) {
        planeBottom.color(alpha, red, green, blue);
    }

    // Изменение цвета верхней плоскости Бокса
    public void colorTop(int alpha, int red, int green, int blue) {
        planeTop.color(alpha, red, green, blue);
    }

    // Изменение цвета передней плоскости Бокса
    public void colorFront(int alpha, int red, int green, int blue) {
        planeFront.color(alpha, red, green, blue);
    }

    // Изменение цвета задней плоскости Бокса
    public void colorBehind(int alpha, int red, int green, int blue) {
        planeBehind.color(alpha, red, green, blue);
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

    // Специальная обводка контура для Бокса по прямоугольным плоскостям (по умолчанию - обводка по полигонам)
    public void outlineBox(boolean isBoxOutlined) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).outlineSpecial(isBoxOutlined);
        this.isBoxOutlined = isBoxOutlined;
        this.isOutlined = isBoxOutlined;
    }

    // Возвращает true, если объект Бокс является выделенным контуром по прямоугольным плоскостям
    public boolean isOutlineBox() {
        return isBoxOutlined;
    }

    @Override
    public void merge() {
        original = (Box) copy();
        restartValues();
    }

    @Override
    public Object3D copy() {
        Box copy = new Box(width, height, length, position.x, position.y, position.z);
        copy.setRotation(rotationX, rotationY, rotationZ);
        copy.setScale(scale);
        copy.color(getAlpha(),
                getColor().red,
                getColor().green,
                getColor().blue);
        copy.planeLeft.color(planeLeft.getColor().alpha,
                planeLeft.getColor().red,
                planeLeft.getColor().green,
                planeLeft.getColor().blue);
        copy.planeRight.color(planeRight.getColor().alpha,
                planeRight.getColor().red,
                planeRight.getColor().green,
                planeRight.getColor().blue);
        copy.planeTop.color(planeTop.getColor().alpha,
                planeTop.getColor().red,
                planeTop.getColor().green,
                planeTop.getColor().blue);
        copy.planeBottom.color(planeBottom.getColor().alpha,
                planeBottom.getColor().red,
                planeBottom.getColor().green,
                planeBottom.getColor().blue);
        copy.planeFront.color(planeFront.getColor().alpha,
                planeFront.getColor().red,
                planeFront.getColor().green,
                planeFront.getColor().blue);
        copy.planeBehind.color(planeBehind.getColor().alpha,
                planeBehind.getColor().red,
                planeBehind.getColor().green,
                planeBehind.getColor().blue);
        copy.outline(this.isOutlined);
        copy.outlineBox(this.isOutlineBox());
        copy.outlineColor(this.getOutlineColor().alpha,
                this.getOutlineColor().red,
                this.getOutlineColor().green,
                this.getOutlineColor().blue);
        copy.outlineWeight(this.getOutlineWeight());
        copy.restartValues();
        return copy;
    }
}
