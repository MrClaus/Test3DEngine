package com.example.gifo.testgame.my3d;

import com.example.gifo.testgame.my3d.forms.Polygon;
import com.example.gifo.testgame.my3d.renderer.Color;

import java.util.ArrayList;

/**
 * Created by gifo on 15.10.2019.
 */

public abstract class Object3D {

    public abstract ArrayList<Polygon> polygons();

    public abstract Point3D getPosition();

    public void setPosition(float x, float y, float z) {
        setPositionX(x);
        setPositionY(y);
        setPositionZ(z);
    }

    public abstract void setPositionX(float x);
    public abstract void setPositionY(float y);
    public abstract void setPositionZ(float z);

    public void move(float dx, float dy, float dz) {
        moveX(dx);
        moveY(dy);
        moveZ(dz);
    }

    public abstract void moveX(float dx);
    public abstract void moveY(float dy);
    public abstract void moveZ(float dz);

    public abstract float getRotationX();
    public abstract float getRotationY();
    public abstract float getRotationZ();

    public abstract float getScale();

    public void setRotation(float ox, float oy, float oz) {
        setRotationX(ox);
        setRotationY(oy);
        setRotationZ(oz);
    }

    public void setRotationX(float ox) {
        setGlobalRotationX(ox, getPosition());
    }

    public void setRotationY(float oy) {
        setGlobalRotationY(oy, getPosition());
    }

    public void setRotationZ(float oz) {
        setGlobalRotationZ(oz, getPosition());
    }

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

    public void setScale(float scale) {
        setGlobalScale(scale, getPosition());
    }

    public void setGlobalScale(float scale, Point3D position) {
        Polygon pl = ((Polygon) this);
        float scaled = scale / getScale();
        for(int i = 0; i < pl.mesh().size(); i++) {
            pl.mesh().get(i).x = (pl.mesh().get(i).x - position.x) * scaled + position.x;
            pl.mesh().get(i).y = (pl.mesh().get(i).y - position.y) * scaled + position.y;
            pl.mesh().get(i).z = (pl.mesh().get(i).z - position.z) * scaled + position.z;
        }
    }

    public abstract void color(int red, int green, int blue);
    public abstract void color(int alpha, int red, int green, int blue);
    public abstract Color getColor();
    public abstract void setAlpha(int alpha);
    public abstract int getAlpha();

    public abstract void outline(boolean isOutlined);
    public abstract boolean isOutline();
    public abstract void outlineWeight(float px);
    public abstract void outlineColor(int alpha, int red, int green, int blue);
    public abstract float getOutlineWeight();
    public abstract Color getOutlineColor();

    public abstract void merge();
    public abstract Object3D copy();
}
