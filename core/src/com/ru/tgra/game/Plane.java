package com.ru.tgra.game;

import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.Vector3D;

/**
 * Created by Berglind on 27.10.2016.
 */
public class Plane {
    Point3D planecoords;
    Vector3D planedirection;

    public Plane(float x, float y, float z, Vector3D dir){
        this.planecoords = new Point3D(x,y,z);

        this.planedirection = new Vector3D(dir.x,dir.y,dir.z);
    }

    public void direction(Vector3D dir){
        planedirection = dir;
    }
    public void update(){
        planecoords.x += planedirection.x;
        planecoords.y += planedirection.y;
        planecoords.z += planedirection.z;

    }
    public Point3D getPlanecoords(){
        return planecoords;
    }


}
