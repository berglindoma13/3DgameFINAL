package com.ru.tgra.graphics.shapes.g3djmodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.BufferUtils;
import java.nio.FloatBuffer;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Berglind on 30.10.2016.
 */
public class Boxes2D {
    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;

    public static void create(int vertexPointer) {
        Boxes2D.vertexPointer = vertexPointer;

        float[] array = {-0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,};

        vertexBuffer = BufferUtils.newFloatBuffer(8);
        vertexBuffer.put(array);
        vertexBuffer.rewind();
    }

    public static void drawSolidSquare(){

        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
    }

    public static void drawSquareLines(){
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, 4);
    }
}
