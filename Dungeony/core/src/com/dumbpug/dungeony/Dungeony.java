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

	@Override
	public void create () {
		// Create the application model used to share data across application states.
		ApplicationModel applicationModel = new ApplicationModel();

		// Create the state manager and add the application states.
		stateManager = new StateManager();
		stateManager.addState(new Splash());
		stateManager.addState(new Title());
		stateManager.addState(new CharacterSelection(applicationModel));
		stateManager.addState(new Game(applicationModel));

		// Set the initial application state.
		stateManager.setCurrentState("SPLASH");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Write the FPS to the console.
		System.out.println(Gdx.graphics.getFramesPerSecond() + " FPS");

		// Update the current application state.
		this.stateManager.update();

		// Render the current application state.
		this.stateManager.render();
	}

	@Override
	public void resize(int width, int height){
		this.stateManager.onResize(width, height);
	}
	
	@Override
	public void dispose () { }
}
