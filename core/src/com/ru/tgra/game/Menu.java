package com.ru.tgra.game;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.BoxGraphic;

/**
 * Created by Berglind on 30.10.2016.
 */
public class Menu {
    public Menu(){

    }

    public void display(Shader shader,Texture tex){
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addScale(2.0f,2.0f,2.0f);
        BoxGraphic.drawSolidCube(shader,tex);
        ModelMatrix.main.popMatrix();
    }

}
