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
    boolean topwall;

    public Obstacle(){
        this.top = 57f;
        this.bottom = -48f;
    }

    public void generateObstacle(float z){
        //Top wall
        topwall = Math.random() < 0.5f;
        if(topwall){
            this.ypos = this.top;
            this.xpos = 0f;
            this.zpos = z + 140.0f;
        }
        //Bottom wall
        else {
            this.ypos = this.bottom;
            this.xpos = 0f;
            this.zpos = z + 140.0f;
        }

    }

    public void display(Shader shader){
        Texture tex = new Texture(Gdx.files.internal("core/assets/textures/bricks.jpg"));
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(this.xpos,this.ypos,this.zpos);
        ModelMatrix.main.addScale(120f,105f,0.2f);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        BoxGraphic.drawSolidCube(shader, tex);
        ModelMatrix.main.popMatrix();
    }

    public boolean collision (float y){
        if(topwall && y > (this.ypos - 52.5f) || (!topwall && y < (this.ypos + 52.5f))){
            System.out.println("Plane y: " + y + " obstacle Y: " + this.ypos);
            return true;
        }
        return false;
    }
}
