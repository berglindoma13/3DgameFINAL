package com.ru.tgra.game;

import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Berglind on 28.10.2016.
 */
public class Gates {

    MeshModel GateModel;
    float top;
    float bottom;
    float right;
    float left;
    float xpos;
    float ypos;
    float zpos;

    public Gates(){
        GateModel = G3DJModelLoader.loadG3DJFromFile("core/assets/models/rings.g3dj");
        top = 8.0f;
        bottom = 0.0f;
        left = 6.0f;
        right = -6.0f;
        generateRandomGate(0.0f);
    }

    public void generateRandomGate(float z){
        Random pos = new Random();
        xpos = pos.nextFloat() * (top - bottom) + bottom;
        ypos = pos.nextFloat() * (left - right) + right;
        zpos = z + 100.0f;
    }

    public void display(Shader shader){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(xpos,ypos,zpos);
        GateModel.draw(shader);
        ModelMatrix.main.popMatrix();
    }

}
