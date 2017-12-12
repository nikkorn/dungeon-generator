package com.dumbpug.dungeondoom;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.dumbpug.dungeondoom.level.Wall;

/**
 * The main game class.
 */
public class DungeonDoom extends ApplicationAdapter {
	public Environment environment;
	public PerspectiveCamera cam;
	public FirstPersonCameraController camController;
	public ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;
	
	private ArrayList<Wall> walls = new ArrayList<Wall>();

	@Override
	public void create() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 1f));
		environment.add(new PointLight().set(1f, 0.2f, 0.2f, 22.5f, 5f, 22.5f, 100f));
		
		modelBatch = new ModelBatch();
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 0f, 0f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		
		createWalls();
        
        camController = new FirstPersonCameraController(cam);
        Gdx.input.setInputProcessor(camController);
	}
	
	/**
	 * Randomly create some walls.
	 */
	public void createWalls() {
		for (int z = 0; z < 10; z++) {
			for (int x = 0; x < 10; x++) {
				if (new Random().nextInt(3) == 1) {
					Wall wall = new Wall(null, null, null, null);
					wall.setCellPosition(z, x);
					this.walls.add(wall);
				}
			}
		}
	}

	@Override
	public void render() {
		camController.update();
		
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
 
        modelBatch.begin(cam);
        for (Wall wall : this.walls) {
        	wall.render(modelBatch, environment);
        }
        modelBatch.end();
	}
	
	@Override
	public void dispose() {
		modelBatch.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}