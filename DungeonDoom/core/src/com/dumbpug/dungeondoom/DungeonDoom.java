package com.dumbpug.dungeondoom;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.dumbpug.dungeondoom.dungen.Configuration;
import com.dumbpug.dungeondoom.dungen.Dungeon;
import com.dumbpug.dungeondoom.dungen.DungeonGenerator;
import com.dumbpug.dungeondoom.dungen.cell.CellType;
import com.dumbpug.dungeondoom.dungen.cell.ICell;
import com.dumbpug.dungeondoom.level.Enemy;
import com.dumbpug.dungeondoom.level.FloorTile;
import com.dumbpug.dungeondoom.level.Wall;

/**
 * The main game class.
 */
public class DungeonDoom extends ApplicationAdapter {
	public Environment environment;
	public PerspectiveCamera cam;
	public FirstPersonCameraController camController;
	
	public ModelBatch modelBatch;
	
	private ModelCache modelCache = new ModelCache();
	
	private Enemy enemy;

	@Override
	public void create() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 5f));
		environment.add(new PointLight().set(1f, 0.2f, 0.2f, 22.5f, 5f, 22.5f, 100f));
		
		modelBatch = new ModelBatch();
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 0f, 0f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		
		// Create a test dungeon, with a default configuration.
		Dungeon dungeon = new DungeonGenerator().generate();
		
		this.modelCache.begin();
		
		// Create the dungeon walls.
		createDungeonWalls(dungeon);
		
		// Create the dungeon floor tiles.
		createFloorTiles(dungeon);
		
		this.modelCache.end();
		
		 // Create a test enemy.
		enemy = new Enemy(null);
		enemy.setCellPosition(4, 4);
        
        camController = new FirstPersonCameraController(cam);
        Gdx.input.setInputProcessor(camController);
	}
	
	/**
	 * Randomly create some dungeon walls.
	 */
	public void createDungeonWalls(Dungeon dungeon) {
		// Get the dungeon configuration.
		Configuration config = dungeon.getConfiguration();
		// Create a list to hold our wall model instances.
		ArrayList<ModelInstance> wallModelInstances = new ArrayList<ModelInstance>();
		// Go over the entire area of the dungeon and draw some sweet walls.
		for (int y = 0; y < config.height; y++) {
			for (int x = 0; x < config.width; x++) {
				// Get the dungeon call at this position.
				ICell cell = dungeon.getCellAt(x, y);
				// Make a piece of wall where we have a reachable wall.
				if (cell != null && cell.getType() == CellType.REACHABLE_WALL) {
					Wall wall = new Wall(null, null, null, null);
					wall.setCellPosition(x, y);
					wallModelInstances.add(wall.getModelInstance());
				}
			}
		}
		// Add our wall models to our model cache.
		this.modelCache.add(wallModelInstances);
	}
	
	/**
	 * Create some floor tiles.
	 */
	public void createFloorTiles(Dungeon dungeon) {
		//Get the dungeon configuration.
		Configuration config = dungeon.getConfiguration();
		// Create a list to hold our floor model instances.
		ArrayList<ModelInstance> floorModelInstances = new ArrayList<ModelInstance>();
		// Go over the entire area of the dungeon and draw some sweet walls.
		for (int y = 0; y < config.height; y++) {
			for (int x = 0; x < config.width; x++) {
				// Get the dungeon call at this position.
				ICell cell = dungeon.getCellAt(x, y);
				// Make a piece of floor where we need it.
				if (cell != null && cell.getType() != CellType.WALL && cell.getType() != CellType.REACHABLE_WALL) {
					FloorTile tile = new FloorTile(null);
					tile.setCellPosition(x, y);
					floorModelInstances.add(tile.getModelInstance());
				}
			}
		}
		// Add our floor tile models to our model cache.
		this.modelCache.add(floorModelInstances);
	}

	@Override
	public void render() {
		camController.update();
		
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
 
        modelBatch.begin(cam);
  
        modelBatch.render(this.modelCache, environment);
        
        enemy.render(modelBatch, environment);
        
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