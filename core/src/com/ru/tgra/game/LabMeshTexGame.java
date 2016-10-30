package com.ru.tgra.game;


import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ru.tgra.graphics.*;
import com.ru.tgra.graphics.Camera;
import com.ru.tgra.graphics.shapes.*;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;
import com.sun.org.apache.xpath.internal.operations.Mod;

public class LabMeshTexGame extends ApplicationAdapter implements InputProcessor {

	Shader shader;

	private float angle;

	private Camera cam;
	private Camera topCam;
	
	private float fov = 90.0f;

	MeshModel airplaneModel;

	Gates gates;

	private Texture tex;
	private Texture tex1;

	private float planeRotationz;
	private float planeRotationy;
	private Vector3D planedirection;
	private Plane airplane;

	private boolean right;
	private boolean left;
	private boolean pause;

	private int gamescore;

	Random rand = new Random();

    Menu menu;

	@Override
	public void create () {
		right = false;
		left = false;
		pause = false;

		gamescore = 3;
        menu = new Menu();

		Gdx.input.setInputProcessor(this);

		gates = new Gates();

		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();

		tex = new Texture(Gdx.files.internal("core/assets/textures/download.jpg"));
		tex1 = new Texture(Gdx.files.internal("core/assets/textures/phobos2k.png"));

		airplaneModel = G3DJModelLoader.loadG3DJFromFile("core/assets/models/germanColored.g3dj");


		BoxGraphic.create();
		SphereGraphic.create();

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		cam = new Camera();
		cam.look(new Point3D(0f, 4f, -3f), new Point3D(0,4,0), new Vector3D(0,1,0));

		topCam = new Camera();
		//orthoCam.orthographicProjection(-5, 5, -5, 5, 3.0f, 100);
		topCam.perspectiveProjection(30.0f, 1, 3, 100);

		planeRotationz = 0.0f;
		planeRotationy = 0.0f;
		planedirection = new Vector3D(0.0f,0.0f,0.0f);

		airplane = new Plane(cam.eye.x,cam.eye.y,cam.eye.z,planedirection);
		gates.generateRandomGate(airplane.planecoords.z);



		//TODO: try this way to create a texture image
		/*Pixmap pm = new Pixmap(128, 128, Format.RGBA8888);
		for(int i = 0; i < pm.getWidth(); i++)
		{
			for(int j = 0; j < pm.getWidth(); j++)
			{
				pm.drawPixel(i, j, rand.nextInt());
			}
		}
		tex = new Texture(pm);*/

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}

	private void input()
	{
	}
	
	private void update()
	{
		if(gamescore == 0){
			System.out.println("GAME OVER");
		}
		float deltaTime = Gdx.graphics.getDeltaTime();

		cam.slide(0, 0, -42.0f * deltaTime);
		airplane.planecoords.z += 42.0f * deltaTime;


	/*if(Gdx.input.isKeyPressed(Input.Keys.A)) {
		cam.slide(-3.0f * deltaTime, 0, 0);
	}
	if(Gdx.input.isKeyPressed(Input.Keys.D)) {
		cam.slide(3.0f * deltaTime, 0, 0);
	}

	if(Gdx.input.isKeyPressed(Input.Keys.S)) {
		cam.slide(0, 0, 3.0f * deltaTime);
	}
	if(Gdx.input.isKeyPressed(Input.Keys.R)) {
		cam.slide(0, 3.0f * deltaTime, 0);
	}
	if(Gdx.input.isKeyPressed(Input.Keys.F)) {
		cam.slide(0, -3.0f * deltaTime, 0);
	}*/

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (airplane.planerotationZ > -50.0f) {
				airplane.rotateZ(-160.0f * deltaTime);
			}

			if (airplane.planecoords.x < 2.0f) {
				airplane.planecoords.x += 2.0f * deltaTime;
			}

		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (airplane.planerotationZ < 50.0f) {
				airplane.rotateZ(160.0f * deltaTime);
			}

			if (airplane.planecoords.x > -2.0f) {
				airplane.planecoords.x -= 2.0f * deltaTime;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (airplane.planerotationX > -50.0f) {
				airplane.rotateX(-160.0f * deltaTime);

			}

			if (airplane.planecoords.y < 7.0f) {
				airplane.planecoords.y += 2.0f * deltaTime;
			}

		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

			if (airplane.planerotationX < 50.0f) {

				airplane.rotateX(160.0f * deltaTime);
			}

			if (airplane.planecoords.y > 2.0f) {
				airplane.planecoords.y -= 2.0f * deltaTime;
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.graphics.setDisplayMode(500, 500, false);
			Gdx.app.exit();
		} else {
			if (airplane.planerotationZ > 0) {
				airplane.rotateZ(-90.0f * deltaTime);
			}
			if (airplane.planerotationZ < 0) {
				airplane.rotateZ(90.0f * deltaTime);
			}
			if (airplane.planerotationX > 0) {
				airplane.rotateX(-90.0f * deltaTime);
			}
			if (airplane.planerotationX < 0) {
				airplane.rotateX(90.0f * deltaTime);
			}
		}


		airplane.update();
		if((gates.zpos - airplane.planecoords.z) < 1.0f){
			if(!gates.collision(airplane.planecoords.x, airplane.planecoords.y)){
				gamescore --;
			}
			gates.generateRandomGate(airplane.planecoords.z);
		}
		//do all updates to the game
	}
	
	private void display()
	{

		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		//Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

		//Gdx.gl.glEnable(GL20.GL_BLEND);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

			Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			cam.perspectiveProjection(fov, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 0.2f, 2000.0f);
			shader.setViewMatrix(cam.getViewMatrix());
			shader.setProjectionMatrix(cam.getProjectionMatrix());
			shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);

			ModelMatrix.main.loadIdentityMatrix();


			float s = (float)Math.sin((angle / 2.0) * Math.PI / 180.0);
			float c = (float)Math.cos((angle / 2.0) * Math.PI / 180.0);

			//shader.setLightPosition(0.0f + c * 3.0f, 5.0f, 0.0f + s * 3.0f, 1.0f);
			shader.setLightPosition(3.0f, 4.0f, 0.0f, 1.0f);
			//shader.setLightPosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);


			float s2 = Math.abs((float)Math.sin((angle / 1.312) * Math.PI / 180.0));
			float c2 = Math.abs((float)Math.cos((angle / 1.312) * Math.PI / 180.0));

			shader.setSpotDirection(s2, -0.3f, c2, 0.0f);
			//shader.setSpotDirection(-cam.n.x, -cam.n.y, -cam.n.z, 0.0f);
			shader.setSpotExponent(0.0f);
			shader.setConstantAttenuation(1.0f);
			shader.setLinearAttenuation(0.00f);
			shader.setQuadraticAttenuation(0.00f);

			//shader.setLightColor(s2, 0.4f, c2, 1.0f);
			shader.setLightColor(1.0f, 1.0f, 1.0f, 1.0f);
			
			shader.setGlobalAmbient(0.7f, 0.7f, 0.7f, 1);

			//shader.setMaterialDiffuse(s, 0.4f, c, 1.0f);
			shader.setMaterialDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
			shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
			//shader.setMaterialSpecular(0.0f, 0.0f, 0.0f, 1.0f);
			shader.setMaterialEmission(0, 0, 0, 1);
			shader.setShininess(50.0f);


			//drawing the plane
			ModelMatrix.main.pushMatrix();
            //BoxGraphic.drawSolidCube(shader,tex1);
			airplane.display(shader);
			airplaneModel.draw(shader);
			//SphereGraphic.drawSolidSphere(shader, tex);
			ModelMatrix.main.popMatrix();

			//draw the ring
			gates.display(shader);

			//draw the environment
			drawWorld();
            //menu.display(shader,tex);
		
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			if(pause){
				pause = false;
			}
			else{
				pause = true;
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.graphics.setDisplayMode(500, 500, false);
			Gdx.app.exit();
		}
		if(!pause){
			input();
			//put the code inside the update and display methods, depending on the nature of the code
			update();
			display();
		}

	}

	private void drawWorld(){

			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(0.0f,4.0f,600.0f);
			ModelMatrix.main.addScale(120.0f,120.0f,1200.0f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawSolidSphere(shader, tex);
			ModelMatrix.main.popMatrix();

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


}