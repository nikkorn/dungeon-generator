package com.dumbpug.dungeony.state.states;

import com.dumbpug.dungeony.state.State;

/**
 * The 'title' state of the application.
 */
public class Title extends State {

    // TODO Add an ExtendedViewport just for this.

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
    public void render() { }

    @Override
    public void onResize(int width, int height) { }

    @Override
    public String getStateName() {
        return "TITLE";
    }
}
