package com.dumbpug.dungeony.state.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.ApplicationModel;
import com.dumbpug.dungeony.game.level.Level;
import com.dumbpug.dungeony.game.level.LevelFactory;
import com.dumbpug.dungeony.state.State;

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
     * Creates a new instance of the Game class.
     * @param applicationModel The application model used to share data across application state
     */
    public Game(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    /**
     * Called on moving to this state.
     * @param state The previously active state.
     */
    @Override
    public void onEntry(State state) {
        // Moving to this state means that we are starting a new game from the initial level.
        this.level = LevelFactory.createInitialLevel(this.applicationModel.getPlayerDetails());
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
    public void render(SpriteBatch batch) {
        // Render the collection of level renderables.
        this.level.render(batch);

        // TODO Render the HUD.
        // TODO Render any game dialogs.
    }

    @Override
    public String getStateName() {
        return "GAME";
    }
}
