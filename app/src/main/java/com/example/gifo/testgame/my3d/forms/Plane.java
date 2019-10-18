package com.example.gifo.testgame.my3d.forms;

import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Point3D;
import com.example.gifo.testgame.my3d.renderer.Color;

import java.util.ArrayList;

/**
 * Created by gifo on 16.10.2019.
 */

public class Plane extends Object3D {

    private float scale = 1f;
    private Point3D position;
    private ArrayList<Polygon> poly = new ArrayList<Polygon>();
    private float rotationX = 0f, rotationY = 0f, rotationZ = 0f;
    Polygon polyRight, polyLeft;
    Color color = new Color(255, 128, 128, 128);

    public Plane(float width, float height,
                 float xPosition,
                 float yPosition,
                 float zPosition) {

        float w = Math.abs(width);
        float h = Math.abs(height);

        polyRight = new Polygon(xPosition + w/2, yPosition + h/2, zPosition,
                                xPosition - w/2, yPosition + h/2, zPosition,
                                xPosition - w/2, yPosition - h/2, zPosition);

        polyLeft = new Polygon(xPosition + w/2, yPosition + h/2, zPosition,
                               xPosition - w/2, yPosition - h/2, zPosition,
                               xPosition + w/2, yPosition - h/2, zPosition);

        poly.add(polyRight);
        poly.add(polyLeft);
        calcPosition();
    }

    private void calcPosition() {
        position = new Point3D((polyRight.getPosition().x + polyLeft.getPosition().x)/2,
                               (polyRight.getPosition().y + polyLeft.getPosition().y)/2,
                               (polyRight.getPosition().z + polyLeft.getPosition().z)/2);
    }

    @Override
    public void setPositionX(float x) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setPositionX(x);
        position.x = x;
    }

    @Override
    public void setPositionY(float y) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setPositionY(y);
        position.y = y;
    }

    @Override
    public void setPositionZ(float z) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).setPositionZ(z);
        position.z = z;
    }

    @Override
    public Point3D getPosition() {
        return position;
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
    public ArrayList<Polygon> polygons() {
        return poly;
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
    public Color getColor() {
        return color;
    }
}
