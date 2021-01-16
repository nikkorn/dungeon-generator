package com.dumbpug.dungeony.state.states;

import com.badlogic.gdx.graphics.Texture;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.state.State;

/**
 * The 'splash' state of the application.
 */
public class Splash extends State {
    /**
     * The time since the state was last entered.
     */
    private long startTime;

    // TODO Add an ExtendedViewport just for this.

    /**
     * Create a new instance of the Splash class.
     */
    public Splash() {
    }

    @Override
    public void onEntry(State state) {
        // Reset the splash start time.
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onExit() {
    }

    @Override
    public void update() {
        // Have we shown the splash long enough? If so then move to the terminal.
        if ((System.currentTimeMillis() - this.startTime) >= Constants.SPLASH_DURATION) {
            this.changeState("TITLE");
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void onResize(int width, int height) { }

    @Override
    public String getStateName() {
        return "SPLASH";
    }
}
