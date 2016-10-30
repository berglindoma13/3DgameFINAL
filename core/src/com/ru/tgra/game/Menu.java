package com.ru.tgra.game;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.BoxGraphic;
import com.ru.tgra.graphics.shapes.g3djmodel.Boxes2D;

/**
 * Created by Berglind on 30.10.2016.
 */
public class Menu {
    Shader shader;

    public Menu(Shader shader){
        this.shader = shader;
        Boxes2D.create(this.shader.getVertexPointer());
    }

    public void display(Texture tex){

        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(0.0f,3.5f,4.0f);
        ModelMatrix.main.addScale(5.0f,5.0f,5.0f);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        Boxes2D.drawSolidSquare();  
        ModelMatrix.main.popMatrix();
    }

}
