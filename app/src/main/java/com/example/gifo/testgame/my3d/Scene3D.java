package com.example.gifo.testgame.my3d;

import com.example.gifo.testgame.my3d.forms.Polygon;

import java.util.ArrayList;

/**
 * Created by gifo on 16.10.2019.
 * Абстракция - сцена.
 */

public class Scene3D {

    private ArrayList<Polygon> polygons = new ArrayList<>();
    private ArrayList<Object3D> sceneList = new ArrayList<>();

    // Добавление объекта на сцену
    public void add(Object3D obj) {
        sceneList.add(obj);
        polygons.addAll(obj.polygons());
    }

    // Возвращает список 3д-объектов сцены
    public ArrayList<Object3D> getScene() {
        return sceneList;
    }

    // Возвращает список всех полигонов сцены
    public ArrayList<Polygon> polygons() {
        return polygons;
    }

    // Возвращает количество объектов на сцене
    public int size() {
        return sceneList.size();
    }

    // Полность ощищает сцену
    public void clear() {
        sceneList.clear();
    }

    // Удаляет заданный объект сцены по ссылке
    public void remove(Object3D obj) {
        sceneList.remove(obj);
        polygons.clear();
        for (int i = 0; i < sceneList.size(); i++) polygons.addAll(sceneList.get(i).polygons());
    }
}
