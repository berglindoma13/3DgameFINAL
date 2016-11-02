package com.ru.tgra.graphics.shapes.g3djmodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import java.nio.FloatBuffer;
import com.badlogic.gdx.graphics.GL20;
import com.ru.tgra.graphics.Shader;

/**
 * Created by Berglind on 30.10.2016.
 */
public class Boxes2D {
    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;
    private static FloatBuffer uvBuffer;


    public static void create(int vertexPointer) {
        Boxes2D.vertexPointer = vertexPointer;

        float[] array = {0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f};

        vertexBuffer = BufferUtils.newFloatBuffer(8);
        vertexBuffer.put(array);
        vertexBuffer.rewind();

        //UV TEXTURE COORD ARRAY IS FILLED HERE
        float[] uvArray = {0.0f,0.0f,
                1.0f,0.0f,
                1.0f,1.0f,
                0.0f,1.0f};

        uvBuffer = BufferUtils.newFloatBuffer(8);
        BufferUtils.copy(uvArray, 0, uvBuffer, 8);
        uvBuffer.rewind();
    }

    public static void drawSolidSquare(Shader shader, Texture tex){

        shader.setDiffuseTexture(tex);

        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
    }


}
