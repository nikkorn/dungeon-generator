package com.dumbpug.dungeony;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dumbpug.dungeony.state.StateManager;
import com.dumbpug.dungeony.state.states.CharacterSelection;
import com.dumbpug.dungeony.state.states.Game;
import com.dumbpug.dungeony.state.states.Splash;
import com.dumbpug.dungeony.state.states.Title;

/**
 * The Dungeony application.
 */
public class Dungeony extends ApplicationAdapter {
	/**
	 * The state manager.
	 */
	private StateManager stateManager;
	/**
	 * The sprite batch to use throughout the application.
	 */
	private SpriteBatch batch;
	/**
	 * The application camera.
	 */
	private OrthographicCamera camera;
	/**
	 * The application viewport.
	 */
	private Viewport viewport;
	
	@Override
	public void create () {
		// Create the application model used to share data across application states.
		ApplicationModel applicationModel = new ApplicationModel();

		camera = new OrthographicCamera();
		viewport = new ExtendViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, camera);
		viewport.apply();

		// Create the state manager and add the application states.
		stateManager = new StateManager();
		stateManager.addState(new Splash());
		stateManager.addState(new Title());
		stateManager.addState(new CharacterSelection(applicationModel));
		stateManager.addState(new Game(applicationModel, camera));

		// Set the initial application state.
		stateManager.setCurrentState("SPLASH");

		// Create the application sprite batch.
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		// Write the FPS to the console.
		System.out.println(Gdx.graphics.getFramesPerSecond() + " FPS");

		// Update the current application state.
		this.stateManager.update();

		// Update the sprite batch position to match the camera.
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0.219f, 0.219f, 0.239f, 1);

		// Render the current application state.
		batch.begin();
		this.stateManager.render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
		camera.position.set(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2,0);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
