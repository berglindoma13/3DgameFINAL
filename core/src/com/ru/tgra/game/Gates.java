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
        GateModel = G3DJModelLoader.loadG3DJFromFile("core/assets/models/ring2.g3dj");
        top = 7.0f;
        bottom = 2.0f;
        left = 2.0f;
        right = -2.0f;
        generateRandomGate(0.0f);
    }

    public void generateRandomGate(float z){
        Random pos = new Random();
        ypos = pos.nextFloat() * (top - bottom) + bottom;
        xpos = pos.nextFloat() * (left - right) + right;
        zpos = z + 100.0f;
    }

    public void display(Shader shader){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(xpos,ypos,zpos);
        ModelMatrix.main.addScale(1.5f,1.5f,0.2f);
        GateModel.draw(shader);
        ModelMatrix.main.popMatrix();
    }

    public boolean collision (float x, float y){
        if((x < (xpos + 0.5f) && x > (xpos - 0.5f)) && (y > (ypos - 0.5f) && y < (ypos + 0.5f))){
            return true;
        }
        return false;
    }
}
