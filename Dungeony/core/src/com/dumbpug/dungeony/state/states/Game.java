package com.dumbpug.dungeony.state.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.game.level.Level;
import com.dumbpug.dungeony.state.State;

/**
 * The 'game' state of the application.
 */
public class Game extends State {
    /**
     * The current level.
     */
    private Level level;

    /**
     * Called on moving to this state.
     * @param state The previously active state.
     */
    @Override
    public void onEntry(State state) {
        // Moving to this state means that we are starting a new game from the initial level.
        // TODO Create the initial level via the LevelFactory.
        // ...


    }

    @Override
    public void onExit() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        // Render the level.
        this.level.render(batch);

        // TODO Drender the HUD.
        // TODO Render any game dialogs.
    }

    @Override
    public String getStateName() {
        return "GAME";
    }
}
