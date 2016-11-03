package com.ru.tgra.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Model;
import com.ru.tgra.graphics.*;
import com.ru.tgra.graphics.Camera;
import com.ru.tgra.graphics.shapes.*;
import com.ru.tgra.graphics.shapes.g3djmodel.Boxes2D;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

import java.util.Random;

public class LabMeshTexGame extends ApplicationAdapter implements InputProcessor {

	Shader shader;

	private float angle;

	private Camera cam;
	private Camera topCam;
	
	private float fov = 90.0f;

	MeshModel airplaneModel;

	Gates gates;
	Obstacle obstacle;

	private Texture tex;
	private Texture tex1;
    private Texture kari;

	private float planeRotationz;
	private float planeRotationy;
	private Vector3D planedirection;
	private Plane airplane;
	private Plane airplaneLife;

	private boolean right;
	private boolean left;
	private boolean pause;

	private boolean menuscreen;

	private int gamescore;

	int karipos[];

    Menu menu;

	@Override
	public void create () {
		right = false;
		left = false;
		pause = false;

		gamescore = 3;
		menuscreen = true;


		Gdx.input.setInputProcessor(this);

		gates = new Gates();
		obstacle = new Obstacle();
		obstacle.generateObstacle(70f);

		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();

		tex = new Texture(Gdx.files.internal("core/assets/textures/download.jpg"));
		tex1 = new Texture(Gdx.files.internal("core/assets/textures/phobos2k.png"));
		kari = new Texture(Gdx.files.internal("core/assets/textures/kari.jpg"));
		karipos = new int[20];
		Random pos = new Random();
		for (int i = 0; i < 20; i++){
			karipos[i] = pos.nextInt(10) - 5;
		}

		airplaneModel = G3DJModelLoader.loadG3DJFromFile("core/assets/models/germanColored.g3dj");

		BoxGraphic.create();
		SphereGraphic.create();

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		cam = new Camera();
		cam.look(new Point3D(0f, 4f, -3f), new Point3D(0,4,0), new Vector3D(0,1,0));

		topCam = new Camera();
		topCam.perspectiveProjection(30.0f, 1, 3, 100);

		planeRotationz = 0.0f;
		planeRotationy = 0.0f;
		planedirection = new Vector3D(0.0f,0.0f,0.0f);

		airplane = new Plane(cam.eye.x,cam.eye.y,cam.eye.z,planedirection);
		airplaneLife = new Plane(cam.eye.x,cam.eye.y,cam.eye.z,planedirection);

		gates.generateRandomGate(airplane.planecoords.z);

        menu = new Menu(shader);


		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}

	private void restart(){
		cam.look(new Point3D(0f, 4f, -3f), new Point3D(0,4,0), new Vector3D(0,1,0));
		airplane = new Plane(cam.eye.x,cam.eye.y,cam.eye.z,planedirection);
		gates.generateRandomGate(airplane.planecoords.z);
		obstacle.generateObstacle(70f);
		gamescore = 3;
	}

	private void input()
	{
	}
	
	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		cam.slide(0, 0, -42.0f * deltaTime);
		airplane.planecoords.z += 42.0f * deltaTime;

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (airplane.planerotationZ > -50.0f) {
				airplane.rotateZ(-180.0f * deltaTime);
			}

			if (airplane.planecoords.x < 2.0f) {
				airplane.planecoords.x += 2.0f * deltaTime;
			}

		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (airplane.planerotationZ < 50.0f) {
				airplane.rotateZ(180.0f * deltaTime);
			}

			if (airplane.planecoords.x > -2.0f) {
				airplane.planecoords.x -= 2.0f * deltaTime;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (airplane.planerotationX > -50.0f) {
				airplane.rotateX(-180.0f * deltaTime);

			}

			if (airplane.planecoords.y < 7.0f) {
				airplane.planecoords.y += 2.0f * deltaTime;
			}

		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

			if (airplane.planerotationX < 50.0f) {

				airplane.rotateX(180.0f * deltaTime);
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

		System.out.println("x : " + airplane.planecoords.x);
		System.out.println("y : " + airplane.planecoords.y);
		System.out.println("z : " + airplane.planecoords.z);


		if(airplane.planecoords.z < 1800){
			if((gates.zpos - airplane.planecoords.z) < 1.0f){
				if(!gates.collision(airplane.planecoords.x, airplane.planecoords.y)){
					gamescore--;
					if(gamescore <= 0){
						menuscreen = true;
						restart();
					}
					else{
						gates.generateRandomGate(airplane.planecoords.z);

					}
				}
				else{
					gates.generateRandomGate(airplane.planecoords.z);
				}

			}
			if((obstacle.zpos - airplane.planecoords.z) < 1.0f){
				if(obstacle.collision(airplane.planecoords.y)){
					menuscreen = true;
					restart();
				}
				else{
					obstacle.generateObstacle(airplane.planecoords.z);

				}
			}
		}



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

		//Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.perspectiveProjection(fov, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 0.2f, 2800.0f);
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


	}

	public void displaygame(){

		//drawing the plane
		ModelMatrix.main.pushMatrix();
		airplane.display(shader);
		airplaneModel.draw(shader);
		ModelMatrix.main.popMatrix();


		//lives
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(cam.eye.x-0.43f,cam.eye.y-0.4f,cam.eye.z+0.5f);
		ModelMatrix.main.addScale(0.003f,0.003f,0.003f);
		ModelMatrix.main.addRotationX(-90f);
		for(int i = 0; i < gamescore; i++){
			ModelMatrix.main.addTranslation(25f, 0f, 0f);
			airplaneModel.draw(shader);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
		}
		ModelMatrix.main.popMatrix();


		//draw the rings
		gates.display(shader);

		//draw obstacles
		obstacle.display(shader);

		//draw the environment
		drawWorld();
		drawKari();

	}


	@Override
	public void render () {
		if(menuscreen){
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				Gdx.graphics.setDisplayMode(500, 500, false);
				Gdx.app.exit();
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
				menuscreen = false;
			}
			else{
				display();
				menu.display();
			}
		}

		else{
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
				displaygame();
			}
		}
	}

	private void drawWorld(){

			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(0.0f,4.0f,800.0f);
			ModelMatrix.main.addScale(120.0f,120.0f,1200.0f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawSolidSphere(shader, tex);
			ModelMatrix.main.popMatrix();

	}

	private void drawKari(){
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(10.0f,13.5f,2100.0f);
		ModelMatrix.main.addScale(20.0f,20.0f,1.0f);
		ModelMatrix.main.addRotationX(180.0f);
		ModelMatrix.main.addRotationY(180.0f);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		Boxes2D.drawSolidSquare(shader,kari);
		for(int i = 0; i < 10; i++){
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(karipos[i],karipos[i+10],80.0f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			Boxes2D.drawSolidSquare(shader,kari);
			ModelMatrix.main.popMatrix();

		}
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