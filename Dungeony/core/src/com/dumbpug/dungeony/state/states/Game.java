package com.dumbpug.dungeony.state.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.dumbpug.dungeony.ApplicationModel;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.level.Level;
import com.dumbpug.dungeony.game.level.LevelCamera;
import com.dumbpug.dungeony.game.level.LevelFactory;
import com.dumbpug.dungeony.state.State;
import com.dumbpug.levelgeneration.SimpleLevelGenerator;

/**
 * The 'game' state of the application.
 */
public class Game extends State {
    /**
     * The application model used to share data across application state.
     */
    private ApplicationModel applicationModel;
    /**
     * The current level.
     */
    private Level level;
    /**
     * The application camera.
     */
    private OrthographicCamera camera;
    /**
     * The sprite batch to use in rendering the level.
     */
    private SpriteBatch batch;
    /**
     * The viewport to use in rendering the level.
     */
    private ExtendViewport viewport;

    /**
     * Creates a new instance of the Game class.
     * @param applicationModel The application model used to share data across application state.
     */
    public Game(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;

        // Create the game camera that we will use in drawing portions of the level.
        this.camera = new OrthographicCamera();

        // Create the viewport to use in rendering the level.
        this.viewport = new ExtendViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, camera);
        this.viewport.apply();

        // Create the sprite batch to use in rendering the level.
        this.batch = new SpriteBatch();
    }

    /**
     * Called on moving to this state.
     * @param state The previously active state.
     */
    @Override
    public void onEntry(State state) {
        // Moving to this state means that we are starting a new game from the initial level so ...

        // ... create a level camera that wraps our game camera and ...
        LevelCamera levelCamera = new LevelCamera(this.camera);

        // ... create the initial level.
        this.level = LevelFactory.createInitialLevel(levelCamera, new SimpleLevelGenerator(), this.applicationModel.getPlayerDetails());
    }

    @Override
    public void onExit() {

    }

    @Override
    public void update() {
        // Update the level.
        this.level.update();
    }

    @Override
    public void render() {
        // We are going to render the level first, so update the sprite batch position to match the camera.
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Render the collection of level renderables.
        batch.begin();
        this.level.render(batch, camera);
        batch.end();

        // TODO Render the HUD.
        // TODO Render any game dialogs.
    }

    @Override
    public void onResize(int width, int height) {
        this.viewport.update(width, height);
    }

    @Override
    public String getStateName() {
        return "GAME";
    }
}
