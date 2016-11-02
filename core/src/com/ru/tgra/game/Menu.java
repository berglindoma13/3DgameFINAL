package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
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

    public Menu(Shader shader){
        this.shader = shader;
        Boxes2D.create(this.shader.getVertexPointer());
        welcome = new Texture(Gdx.files.internal("core/assets/textures/WelcomeToGame.png"));
    }

    public void display(){

        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(0.0f,3.5f,0.0f);
        ModelMatrix.main.addRotationY(180.0f);
        ModelMatrix.main.addRotationX(180.0f);
        //ModelMatrix.main.addScale(7.0f,7.0f,5.0f);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        Boxes2D.drawSolidSquare(shader,welcome);
        ModelMatrix.main.popMatrix();
    }

}
