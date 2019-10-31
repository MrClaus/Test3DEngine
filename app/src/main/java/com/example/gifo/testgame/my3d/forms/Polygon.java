package com.example.gifo.testgame.my3d.forms;

import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Point3D;
import com.example.gifo.testgame.my3d.renderer.Color;

import java.util.ArrayList;

/**
 * Created by gifo on 15.10.2019.
 * Реализация объекта - Полигон как наследника класса Object3D
 * Дополнительно реализует интерфейс Comparable для задания правила сравнения
 * и адекватного порядка прорисовки Полигонов через Renderer
 */

public class Polygon extends Object3D implements Comparable<Polygon> {

    @Override
    public int compareTo(Polygon obj) {

        // Ранжирование полигонов при отрисовке
        int result = -1 * ((Float) this.position.z).compareTo(obj.getPosition().z);
        //if (result == 0) result = ((Float) this.position.y).compareTo(obj.getPosition().y);
        //if (result == 0) result = ((Float) this.position.x).compareTo(obj.getPosition().x);
        return result;
    }

    private float scale = 1f;
    private Point3D position;
    private ArrayList<Polygon> poly = new ArrayList<Polygon>();
    private ArrayList<Point3D> plots = new ArrayList<Point3D>();
    private float rotationX = 0f, rotationY = 0f, rotationZ = 0f;
    private Color color = new Color(255, 128, 128, 128);

    private boolean isOutlined = false, isOutlineSpecial = false;
    private float outlineWeight = 1f;
    private Color outlineColor = new Color(255, 0, 0, 0);

    private Polygon original = this;

    public Polygon(float x1, float y1, float z1,
                   float x2, float y2, float z2,
                   float x3, float y3, float z3) {

        plots.add(new Point3D(x1, y1, z1));
        plots.add(new Point3D(x2, y2, z2));
        plots.add(new Point3D(x3, y3, z3));
        calcPosition();

        poly.add(this);
    }

    // Авто-пересчёт координат позиции объекта
    private void calcPosition() {
        position = new Point3D((plots.get(0).x + plots.get(1).x + plots.get(2).x)/3,
                               (plots.get(0).y + plots.get(1).y + plots.get(2).y)/3,
                               (plots.get(0).z + plots.get(1).z + plots.get(2).z)/3);
    }

    @Override
    public void setPositionX(float x) {
        for(int i = 0; i < plots.size(); i++) plots.get(i).x += x - position.x;
        position.x = x;
    }

    @Override
    public void setPositionY(float y) {
        for(int i = 0; i < plots.size(); i++) plots.get(i).y += y - position.y;
        position.y = y;
    }

    @Override
    public void setPositionZ(float z) {
        for(int i = 0; i < plots.size(); i++) plots.get(i).z += z - position.z;
        position.z = z;
    }

    @Override
    public Point3D getPosition() {
        return position;
    }

    @Override
    public void moveX(float dx) {
        for(int i = 0; i < plots.size(); i++) plots.get(i).x += dx;
        position.x += dx;
    }

    @Override
    public void moveY(float dy) {
        for(int i = 0; i < plots.size(); i++) plots.get(i).y += dy;
        position.y += dy;
    }

    @Override
    public void moveZ(float dz) {
        for(int i = 0; i < plots.size(); i++) plots.get(i).z += dz;
        position.z += dz;
    }

    @Override
    public void setRotationX(float ox) {
        super.setRotationX(ox);
        rotationX = ox;
    }

    @Override
    public void setRotationY(float oy) {
        super.setRotationY(oy);
        rotationY = oy;
    }

    @Override
    public void setRotationZ(float oz) {
        super.setRotationZ(oz);
        rotationZ = oz;
    }

    @Override
    public void setGlobalRotationX(float ox, Point3D position) {
        super.setGlobalRotationX(ox, position);
        rotationX = ox;
        calcPosition();
    }

    @Override
    public void setGlobalRotationY(float oy, Point3D position) {
        super.setGlobalRotationY(oy, position);
        rotationY = oy;
        calcPosition();
    }

    @Override
    public void setGlobalRotationZ(float oz, Point3D position) {
        super.setGlobalRotationZ(oz, position);
        rotationZ = oz;
        calcPosition();
    }
    // Обнуление динамичных параметров Полигона
    private void restartValues() {
        rotationX = 0;
        rotationY = 0;
        rotationZ = 0;
        scale = 1;
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
        super.setScale(scale);
        this.scale = scale;
    }

    @Override
    public void setGlobalScale(float scale, Point3D position) {
        super.setGlobalScale(scale, position);
        this.scale = scale;
        calcPosition();
    }

    @Override
    public float getScale() {
        return scale;
    }

    public ArrayList<Point3D> mesh() {
        return plots;
    }

    @Override
    public ArrayList<Polygon> polygons() {
        return poly;
    }

    @Override
    public void color(int red, int green, int blue) {
        color.red = red;
        color.green = green;
        color.blue = blue;
    }

    @Override
    public void color(int alpha, int red, int green, int blue) {
        color.alpha = alpha;
        color.red = red;
        color.green = green;
        color.blue = blue;
    }

    @Override
    public void setAlpha(int alpha) {
        int a = original.getAlpha();
        color.alpha = a * alpha/255;
        Color lineColor = original.getOutlineColor();
        outlineColor(lineColor.alpha * alpha/255,
                lineColor.red,
                lineColor.green,
                lineColor.blue);
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
        this.isOutlined = isOutlined;
    }

    @Override
    public boolean isOutline() {
        return isOutlined;
    }

    @Override
    public void outlineWeight(float px) {
        outlineWeight = px;
    }

    @Override
    public float getOutlineWeight() {
        return outlineWeight;
    }

    @Override
    public void outlineColor(int alpha, int red, int green, int blue) {
        outlineColor.alpha = alpha;
        outlineColor.red = red;
        outlineColor.green = green;
        outlineColor.blue = blue;
    }

    @Override
    public Color getOutlineColor() {
        return outlineColor;
    }

    // Специальная обводка контура для полигона по двум границам (кроме диагональной)
    public void outlineSpecial(boolean isOutlineSpecial) {
        this.isOutlineSpecial = isOutlineSpecial;
        this.isOutlined = isOutlineSpecial;
    }

    // Возвращает true, если объект Полигон является выделенным контуром по двум границам (кроме диагональной)
    public boolean isOutlineSpecial() {
        return isOutlineSpecial;
    }

    @Override
    public void merge() {
        original = (Polygon) copy();
        restartValues();
    }

    @Override
    public Object3D copy() {
        Polygon copy = new Polygon(this.mesh().get(0).x, this.mesh().get(0).y, this.mesh().get(0).z,
                this.mesh().get(1).x, this.mesh().get(1).y, this.mesh().get(1).z,
                this.mesh().get(2).x, this.mesh().get(2).y, this.mesh().get(2).z);
        copy.color(this.getAlpha(),
                this.getColor().red,
                this.getColor().green,
                this.getColor().blue);
        copy.outline(this.isOutlined);
        copy.outlineSpecial(this.isOutlineSpecial);
        copy.outlineColor(this.getOutlineColor().alpha,
                this.getOutlineColor().red,
                this.getOutlineColor().green,
                this.getOutlineColor().blue);
        copy.outlineWeight(this.getOutlineWeight());
        copy.restartValues();
        return copy;
    }
}
