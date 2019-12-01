package com.dumbpug.dungeony.state.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.state.State;

/**
 * The 'title' state of the application.
 */
public class Title extends State {
    @Override
    public void onEntry(State state) {
    }

    @Override
    public void onExit() {
    }

    @Override
    public void update() {
        // Go straight to the character selection state.
        this.changeState("CHARACTER_SELECTION");
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public String getStateName() {
        return "TITLE";
    }
}
