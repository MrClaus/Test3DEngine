package com.example.gifo.testgame.my3d.forms;

import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Point3D;
import com.example.gifo.testgame.my3d.renderer.Color;

import java.util.ArrayList;

/**
 * Created by gifo on 16.10.2019.
 */

public class Box extends Object3D {

    private float scale = 1f;
    private Point3D position;
    private ArrayList<Polygon> poly = new ArrayList<Polygon>();
    private float rotationX = 0f, rotationY = 0f, rotationZ = 0f;
    Plane planeRight, planeLeft, planeBottom, planeTop, planeFront, planeBehind;
    Color color = new Color(255, 128, 128, 128);

    public Box(float width, float height, float length,
               float x, float y, float z) {

        float w = Math.abs(width);
        float h = Math.abs(height);
        float l = Math.abs(length);

        planeRight = new Plane(l, h, x - w/2, y, z);
        planeRight.setRotationY(90);

        planeLeft = new Plane(l, h, x + w/2, y, z);
        planeLeft.setRotationY(-90);

        planeBottom = new Plane(w, l, x, y - h/2, z);
        planeBottom.setRotationX(90);

        planeTop = new Plane(w, l, x, y + h/2, z);
        planeTop.setRotationX(-90);

        planeFront = new Plane(w, h, x, y, z - l/2);
        planeBehind = new Plane(w, h, x, y, z + l/2);
        planeBehind.setRotationY(180);

        poly.addAll(planeRight.polygons());
        poly.addAll(planeLeft.polygons());
        poly.addAll(planeBottom.polygons());
        poly.addAll(planeTop.polygons());
        poly.addAll(planeFront.polygons());
        poly.addAll(planeBehind.polygons());
        for (int i = 0; i < poly.size(); i++) poly.get(i).restartRotationAngle();

        calcPosition();
    }

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
    public void color(int alpha, int red, int green, int blue) {
        for (int i = 0; i < poly.size(); i++) poly.get(i).color(alpha, red, green, blue);
        color.alpha = alpha;
        color.red = red;
        color.green = green;
        color.blue = blue;
    }

    public void colorRight(int alpha, int red, int green, int blue) {
        for (int i = 0; i < planeRight.polygons().size(); i++)
            planeRight.polygons().get(i).color(alpha, red, green, blue);
    }

    public void colorLeft(int alpha, int red, int green, int blue) {
        for (int i = 0; i < planeLeft.polygons().size(); i++)
            planeLeft.polygons().get(i).color(alpha, red, green, blue);
    }

    public void colorBottom(int alpha, int red, int green, int blue) {
        for (int i = 0; i < planeBottom.polygons().size(); i++)
            planeBottom.polygons().get(i).color(alpha, red, green, blue);
    }

    public void colorTop(int alpha, int red, int green, int blue) {
        for (int i = 0; i < planeTop.polygons().size(); i++)
            planeTop.polygons().get(i).color(alpha, red, green, blue);
    }

    public void colorFront(int alpha, int red, int green, int blue) {
        for (int i = 0; i < planeFront.polygons().size(); i++)
            planeFront.polygons().get(i).color(alpha, red, green, blue);
    }

    public void colorBehind(int alpha, int red, int green, int blue) {
        for (int i = 0; i < planeBehind.polygons().size(); i++)
            planeBehind.polygons().get(i).color(alpha, red, green, blue);
    }

    @Override
    public Color getColor() {
        return color;
    }
}
