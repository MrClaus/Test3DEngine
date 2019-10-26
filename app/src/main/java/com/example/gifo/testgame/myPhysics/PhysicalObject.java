package com.example.gifo.testgame.myPhysics;

import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Point3D;
import com.example.gifo.testgame.my3d.forms.Polygon;
import com.example.gifo.testgame.myPhysics.actions.PhysicalAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by gifo on 25.10.2019.
 */

public class PhysicalObject implements PhysicalAction {

    private ArrayList<PhysicalObject> physicalField;
    private BubblePhysics physics;
    private Object3D object;
    private Point3D position;
    private float radius;

    // PhysicalAction-interface variable
    boolean isAction = false;
    boolean isActionBang = false;
    boolean isActionFade = false;
    private float bangSpeed, bangAngle = 0;
    private float[] bangPolyAngle, bangPolyRadius, bangPolySpeed;
    private int fadeTime, fadeAlpha;
    private long fadeStart;

    // Observer-interface variable
    boolean isListens = false;
    private Observer callback;
    public interface Observer {
        void listener(Object3D object);
    }

    public PhysicalObject(Object3D object, BubblePhysics physics) {
        this.physics = physics;
        this.object = object;
        init();
    }

    private void init() {
        float dx, dy, dz;
        ArrayList<Float> xField = new ArrayList<>();
        ArrayList<Float> yField = new ArrayList<>();
        ArrayList<Float> zField = new ArrayList<>();
        for (int i=0; i<object.polygons().size(); i++) {
            xField.add(object.polygons().get(i).getPosition().x);
            yField.add(object.polygons().get(i).getPosition().y);
            zField.add(object.polygons().get(i).getPosition().z);
        }
        dx = Math.abs(Collections.max(xField) - Collections.min(xField));
        dy = Math.abs(Collections.max(yField) - Collections.min(yField));
        dz = Math.abs(Collections.max(zField) - Collections.min(zField));
        this.radius = (dx >= dy && dx >= dz) ? dx/2 : (dy >= dx && dy >= dz) ? dy/2 : dz/2;
        this.position = object.getPosition();
    }

    protected void hit(ArrayList<PhysicalObject> physicalField, Observer callback) {
        this.physicalField = physicalField;
        this.callback = callback;
        isListens = true;
    }

    protected void checkHit() {
        for (int i=0; i < physicalField.size(); i++) {
            PhysicalObject o = physicalField.get(i);
            if (object != o.object) {
                double distance = Math.sqrt(Math.pow(position.x - o.position.x, 2) +
                                            Math.pow(position.y - o.position.y, 2)) +
                                            Math.pow(position.z - o.position.z, 2);
                if (distance <= radius + o.radius) callback.listener(o.object);
            }
        }
    }

    @Override
    public void bang(Object3D object, float speed) {
        if (!isActionBang) {
            isAction = true;
            isActionBang = true;
            bangSpeed = speed;
            final Random rnd = new Random();
            int len = object.polygons().size();
            bangPolyAngle = new float[len];
            bangPolyRadius = new float[len];
            bangPolySpeed = new float[len];
            float x = object.getPosition().x;
            float y = object.getPosition().y;
            for (int i=0; i<len; i++) {
                float xx = object.polygons().get(i).getPosition().x;
                float yy = object.polygons().get(i).getPosition().y;
                bangPolySpeed[i] = speed + rnd.nextInt((int) (1000*speed * 1.5))/1000f;
                bangPolyRadius[i] = (float) Math.sqrt(Math.pow((yy - y), 2) + Math.pow((xx - x), 2));
                double theta = Math.atan2(yy - y, xx - x);
                theta += Math.PI/2.0;
                if (theta < 0) theta += 2*Math.PI;
                bangPolyAngle[i] = (float) theta;
            }
        }
    }

    @Override
    public void fade(Object3D object, int milliseconds) {
        if (!isActionFade) {
            isAction = true;
            isActionFade = true;
            fadeTime = milliseconds;
            fadeStart = System.currentTimeMillis();
            fadeAlpha = object.getAlpha();
        }
    }

    protected void toBang() {
        bangAngle += 1.5;
        for (int i=0; i<bangPolyAngle.length; i++) {
            bangPolyRadius[i] += bangPolySpeed[i];
            float x = (float) (object.getPosition().x + bangPolyRadius[i] * Math.sin(bangPolyAngle[i]));
            float y = (float) (object.getPosition().y - bangPolyRadius[i] * Math.cos(bangPolyAngle[i]));
            Polygon p = object.polygons().get(i);
            p.setPositionX(x);
            p.setPositionY(y);
            p.setRotation(bangAngle, bangAngle, bangAngle);
        }
    }

    protected void toFade() {
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - fadeStart;
        if (deltaTime <= fadeTime) {
            float alpha = 1f * deltaTime/fadeTime;
            object.setAlpha((int) ((1f - alpha)*fadeAlpha));
        } else {
            physics.scene.remove(object);
        }
    }
}
