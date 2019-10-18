package com.example.gifo.testgame.my3d;

import com.example.gifo.testgame.my3d.forms.Polygon;

import java.util.ArrayList;

/**
 * Created by gifo on 16.10.2019.
 */

public class Scene3D {

    private ArrayList<Polygon> polygons = new ArrayList<>();
    private ArrayList<Object3D> sceneList = new ArrayList<>();

    public void add(Object3D obj) {
        sceneList.add(obj);
        polygons.addAll(obj.polygons());
    }

    public ArrayList<Object3D> getScene() {
        return sceneList;
    }

    public ArrayList<Polygon> polygons() {
        return polygons;
    }

    public int size() {
        return sceneList.size();
    }

    public void clear() {
        sceneList.clear();
    }

    public void remove(Object3D obj) {
        sceneList.remove(obj);
        polygons.clear();
        for (int i = 0; i < sceneList.size(); i++) polygons.addAll(sceneList.get(i).polygons());
    }
}
