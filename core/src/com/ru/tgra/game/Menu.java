package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.BoxGraphic;
import com.ru.tgra.graphics.shapes.g3djmodel.Boxes2D;
import com.sun.org.apache.xpath.internal.operations.Mod;

/**
 * Created by Berglind on 30.10.2016.
 */
public class Menu {
    Shader shader;
    Texture welcome;
    Texture startButton;
    Texture instructions1;
    Texture instruction2;
    Texture startAgain;
    Texture didWin;
    public static boolean won;

    public Menu(Shader shader){
        this.shader = shader;
        Boxes2D.create(this.shader.getVertexPointer());
        welcome = new Texture(Gdx.files.internal("core/assets/textures/Welcome3.png"));
        startButton = new Texture(Gdx.files.internal("core/assets/textures/PressToStart.png"));
        instructions1 = new Texture(Gdx.files.internal("core/assets/textures/Instructions.png"));
        instruction2 = new Texture(Gdx.files.internal("core/assets/textures/InstructionsPause.png"));
        startAgain = new Texture(Gdx.files.internal("core/assets/textures/PlayAgain.png"));
        didWin = new Texture(Gdx.files.internal("core/assets/textures/Congrats.png"));
        won = false;
    }

    public void display(){
        header();
        startButton(won);
        instructions();
        if(won){
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(2.0f,4.5f,0.0f);
            ModelMatrix.main.addScale(4.0f,1.5f,1.0f);
            ModelMatrix.main.addRotationY(180.0f);
            ModelMatrix.main.addRotationX(180.0f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            Boxes2D.drawSolidSquare(shader,didWin);
            ModelMatrix.main.popMatrix();
        }

    }

    public void header(){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(2.0f,6.5f,0.0f);
        ModelMatrix.main.addRotationY(180.0f);
        ModelMatrix.main.addRotationX(180.0f);
        ModelMatrix.main.addScale(4.0f,1.0f,1.0f);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        Boxes2D.drawSolidSquare(shader,welcome);
        ModelMatrix.main.popMatrix();
    }

    public void startButton(boolean won){
        if(won){
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(2.5f,2.5f,0.0f);
            ModelMatrix.main.addRotationY(180.0f);
            ModelMatrix.main.addRotationX(180.0f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            Boxes2D.drawSolidSquare(shader,startAgain);
            ModelMatrix.main.popMatrix();
        }
        else{
            ModelMatrix.main.pushMatrix();
            ModelMatrix.main.addTranslation(2.5f,2.5f,0.0f);
            ModelMatrix.main.addRotationY(180.0f);
            ModelMatrix.main.addRotationX(180.0f);
            shader.setModelMatrix(ModelMatrix.main.getMatrix());
            Boxes2D.drawSolidSquare(shader,startButton);
            ModelMatrix.main.popMatrix();
        }

    }

    public void instructions(){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(-1.1f,2.5f,0.0f);
        ModelMatrix.main.addRotationY(180.0f);
        ModelMatrix.main.addRotationX(180.0f);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        Boxes2D.drawSolidSquare(shader,instructions1);
        ModelMatrix.main.popMatrix();

        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(0.0f,2.5f,0.0f);
        ModelMatrix.main.addRotationY(180.0f);
        ModelMatrix.main.addRotationX(180.0f);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        Boxes2D.drawSolidSquare(shader,instruction2);
        ModelMatrix.main.popMatrix();
    }

}
