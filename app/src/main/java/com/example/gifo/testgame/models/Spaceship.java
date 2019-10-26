package com.example.gifo.testgame.models;

import com.example.gifo.testgame.my3d.Object3D;
import com.example.gifo.testgame.my3d.forms.Group;
import com.example.gifo.testgame.my3d.forms.Polygon;

/**
 * Created by gifo on 21.10.2019.
 */

public class Spaceship {

    Group model;

    public Spaceship() {
        Object3D poly1 = new Polygon(0, 0, 2.5f,
                                     0, 10, 15,
                                     10, 2.5f, 25);
        Object3D poly2 = new Polygon(0, 0, 2.5f,
                                     0, 10, 15,
                                     -10, 2.5f, 25);
        Object3D poly3 = new Polygon(7.5f, 10, 27.5f,
                                     0, 10, 15,
                                     10, 2.5f, 25);
        Object3D poly4 = new Polygon(-7.5f, 10, 27.5f,
                                     0, 10, 15,
                                     -10, 2.5f, 25);
        Object3D poly5 = new Polygon(7.5f, 10, 27.5f,
                                     0, 10, 15,
                                     0, 12.5f, 30);
        Object3D poly6 = new Polygon(-7.5f, 10, 27.5f,
                                     0, 10, 15,
                                     0, 12.5f, 30);

        Group glass = new Group(poly1);
        glass.addObject(poly2);
        glass.addObject(poly3);
        glass.addObject(poly4);
        glass.addObject(poly5);
        glass.addObject(poly6);
        glass.color(64, 64, 64, 255);

        Object3D poly7 = new Polygon(0, 12.5f, 30,
                                     7.5f, 10, 27.5f,
                                     0, 10, 50);
        Object3D poly8 = new Polygon(0, 12.5f, 30,
                                     -7.5f, 10, 27.5f,
                                     0, 10, 50);
        Object3D poly9 = new Polygon(10, 2.5f, 25,
                                     7.5f, 10, 27.5f,
                                     0, 10, 50);
        Object3D poly10 = new Polygon(-10, 2.5f, 25,
                                     -7.5f, 10, 27.5f,
                                     0, 10, 50);
        Object3D poly11 = new Polygon(0, 0, 60,
                                     10f, 2.5f, 25,
                                     0, 10, 50);
        Object3D poly12 = new Polygon(0, 0, 60,
                                     -10f, 2.5f, 25,
                                     0, 10, 50);
        Object3D poly13 = new Polygon(0, 0, 60,
                                     10f, 2.5f, 25,
                                     0, -10, 20);
        Object3D poly14 = new Polygon(0, 0, 60,
                                     -10f, 2.5f, 25,
                                     0, -10, 20);
        Object3D poly15 = new Polygon(0, -20f, 10,
                                     10f, 2.5f, 25,
                                     0, -10, 20);
        Object3D poly16 = new Polygon(0, -20f, 10,
                                     -10f, 2.5f, 25,
                                     0, -10, 20);
        Object3D poly17 = new Polygon(0, -20f, 10,
                                     10f, 2.5f, 25,
                                     0, 0, 2.5f);
        Object3D poly18 = new Polygon(0, -20f, 10,
                                     -10f, 2.5f, 25,
                                     0, 0, 2.5f);

        Group body = new Group(poly7);
        body.addObject(poly8);
        body.addObject(poly9);
        body.addObject(poly10);
        body.addObject(poly11);
        body.addObject(poly12);
        body.addObject(poly13);
        body.addObject(poly14);
        body.addObject(poly15);
        body.addObject(poly16);
        body.addObject(poly17);
        body.addObject(poly18);
        body.outline(true);
        body.outlineWeight(4);

        Object3D poly19 = new Polygon(-12.5f, 15, 35,
                                     -30, -5f, 35,
                                     -15, -2.5f, 27.5f);
        Object3D poly20 = new Polygon(-10, -5, -20,
                                     -30, -5f, 35,
                                     -15, -2.5f, 27.5f);
        Object3D poly21 = new Polygon(-10, -5, -20,
                                     -30, -5f, 35,
                                     -10, -15f, 35f);
        Object3D poly22 = new Polygon(-10, -5, -20,
                                     -15, -2.5f, 27.5f,
                                     -10, -15f, 35f);
        Object3D poly23 = new Polygon(-12.5f, 15, 35,
                                     -30, -5f, 35,
                                     -15, 0, 60);
        Object3D poly24 = new Polygon(-10f, -15, 35,
                                     -30, -5f, 35,
                                     -15, 0, 60);
        Object3D poly25 = new Polygon(-10f, -15, 35,
                                     -15, -2.5f, 27.5f,
                                     -15, 0, 60);
        Object3D poly26 = new Polygon(-12.5f, 15, 35,
                                     -15, -2.5f, 27.5f,
                                     -15, 0, 60);

        Group flayerL = new Group(poly19);
        flayerL.addObject(poly20);
        flayerL.addObject(poly21);
        flayerL.addObject(poly22);
        flayerL.addObject(poly23);
        flayerL.addObject(poly24);
        flayerL.addObject(poly25);
        flayerL.addObject(poly26);
        flayerL.outline(true);
        flayerL.outlineWeight(4);
        flayerL.color(255, 64, 64, 64);

        Object3D poly27 = new Polygon(12.5f, 15, 35,
                                     30, -5f, 35,
                                     15, -2.5f, 27.5f);
        Object3D poly28 = new Polygon(10, -5, -20,
                                     30, -5f, 35,
                                     15, -2.5f, 27.5f);
        Object3D poly29 = new Polygon(10, -5, -20,
                                     30, -5f, 35,
                                     10, -15f, 35f);
        Object3D poly30 = new Polygon(10, -5, -20,
                                     15, -2.5f, 27.5f,
                                     10, -15f, 35f);
        Object3D poly31 = new Polygon(12.5f, 15, 35,
                                     30, -5f, 35,
                                     15, 0, 60);
        Object3D poly32 = new Polygon(10f, -15, 35,
                                     30, -5f, 35,
                                     15, 0, 60);
        Object3D poly33 = new Polygon(10f, -15, 35,
                                     15, -2.5f, 27.5f,
                                     15, 0, 60);
        Object3D poly34 = new Polygon(12.5f, 15, 35,
                                     15, -2.5f, 27.5f,
                                     15, 0, 60);

        Group flayerR = new Group(poly34);
        flayerR.addObject(poly27);
        flayerR.addObject(poly28);
        flayerR.addObject(poly29);
        flayerR.addObject(poly30);
        flayerR.addObject(poly31);
        flayerR.addObject(poly32);
        flayerR.addObject(poly33);
        flayerR.outline(true);
        flayerR.outlineWeight(4);
        flayerR.color(255, 64, 64, 64);

        model = new Group((Object3D) glass);
        model.addObject((Object3D) body);
        model.addObject((Object3D) flayerL);
        model.addObject((Object3D) flayerR);

        model.merge();
    }

    public Object3D getObject() {
        return model;
    }
}
