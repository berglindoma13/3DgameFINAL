package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.BoxGraphic;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Berglind on 28.10.2016.
 */
public class Obstacle {

    float top;
    float bottom;
    float xpos;
    float ypos;
    float zpos;

    public Obstacle(){
        top = 57f;
        bottom = -48f;
    }

    public void generateObstacle(float z){
        //Top wall
        if(Math.random() < 0.5f){
            ypos = top;
            xpos = 0f;
            zpos = z + 140.0f;
        }
        //Bottom wall
        else {
            ypos = bottom;
            xpos = 0f;
            zpos = z + 140.0f;
        }

    }

    public void display(Shader shader){
        Texture tex = new Texture(Gdx.files.internal("core/assets/textures/dice.png"));
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(xpos,ypos,zpos);
        ModelMatrix.main.addScale(120f,105f,0.2f);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        BoxGraphic.drawSolidCube(shader, tex);
        //GateModel.draw(shader);
        ModelMatrix.main.popMatrix();
    }

    public boolean collision (float x, float y){
        if((x < (xpos + 0.5f) && x > (xpos - 0.5f)) && (y > (ypos - 0.5f) && y < (ypos + 0.5f))){
            return true;
        }
        return false;
    }
}
