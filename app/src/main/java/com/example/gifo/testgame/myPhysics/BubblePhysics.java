package com.example.gifo.testgame.myPhysics;

import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.Scene3D;
import com.example.gifo.testgame.myPhysics.actions.PhysicalAction;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by gifo on 25.10.2019.
 */

public class BubblePhysics {

    Scene3D scene;
    private ArrayList<Object3D> objects = new ArrayList<>();
    private ArrayList<PhysicalObject> physicalField = new ArrayList<>();

    public Action action = new Action();
    public class Action implements PhysicalAction {
        @Override
        public void bang(Object3D object, float speed) {
            int id = objects.indexOf(object);
            if (id >= 0) physicalField.get(id).bang(object, speed);
        }

        @Override
        public void fade(Object3D object, int milliseconds) {
            int id = objects.indexOf(object);
            if (id >= 0) physicalField.get(id).fade(object, milliseconds);
        }

        public boolean isAction(Object3D object) {
            int id = objects.indexOf(object);
            return (id >= 0) ? physicalField.get(id).isAction : false;
        }

        public boolean isActionBang(Object3D object) {
            int id = objects.indexOf(object);
            return (id >= 0) ? physicalField.get(id).isActionBang : false;
        }

        public boolean isActionFade(Object3D object) {
            int id = objects.indexOf(object);
            return (id >= 0) ? physicalField.get(id).isActionFade : false;
        }
    }

    public BubblePhysics(Scene3D scene) {
        this.scene = scene;
    }

    public void addObject(Object3D object) {
        objects.add(object);
        physicalField.add(new PhysicalObject(object, this));
    }

    public void collisions(Object3D object, PhysicalObject.Observer callback) {
        int id = objects.indexOf(object);
        if (id >= 0) physicalField.get(id).hit(physicalField, callback);
    }

    public void execute() {
        refreshSceneObjects();
        for (int i=0; i < physicalField.size(); i++) {
            PhysicalObject o = physicalField.get(i);
            if (o.isListens) o.checkHit();
            if (o.isAction) {
                if (o.isActionBang) o.toBang();
                if (o.isActionFade) o.toFade();
            }
        }
    }

    private void refreshSceneObjects() {
        Iterator<Object3D> iterator = objects.iterator();
        while (iterator.hasNext()) {
            Object3D o = iterator.next();
            if (!scene.getScene().contains(o)) {
                int id = objects.indexOf(o);
                physicalField.remove(id);
                iterator.remove();
            }
        }
    }
}
