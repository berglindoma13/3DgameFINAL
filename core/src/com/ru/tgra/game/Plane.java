package com.ru.tgra.game;

import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.Vector3D;

/**
 * Created by Berglind on 27.10.2016.
 */
public class Plane {
    Point3D planecoords;
    Vector3D planedirection;
    float planerotationZ;
    float planerotationX;

    public Plane(float x, float y, float z, Vector3D dir){
        this.planecoords = new Point3D(x,y - 0.5f,z+1.5f);
        this.planedirection = new Vector3D(dir.x,dir.y,dir.z);
        planerotationZ = 0.0f;
    }

    public void updatePosition(float x, float y, float z){
        planecoords.x = x;
        planecoords.y = y;
        planecoords.z = z;
    }

    public void rotateZ(float angle){
        planerotationZ += angle;
    }
    public void rotateX(float angle){
        planerotationX += angle;
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

    public void display(Shader shader){
        ModelMatrix.main.addTranslation(planecoords.x , planecoords.y - 0.5f, planecoords.z + 1.5f);
        ModelMatrix.main.addScale(0.09f,0.09f,0.09f);
        ModelMatrix.main.addRotationZ(planerotationZ);
        ModelMatrix.main.addRotationX(planerotationX);

        //ModelMatrix.main.addTranslation(planedirection.x ,0.0f,planedirection.z);

        shader.setModelMatrix(ModelMatrix.main.getMatrix());

    }

}
