package com.dumbpug.dungeony.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardInputProvider implements IPlayerInputProvider {
    @Override
    public float getMovementAxisX() {
        if (this.isControlPressed(Control.LEFT))  {
            return -1f;
        } else if (this.isControlPressed(Control.RIGHT)) {
            return 1f;
        } else {
            return 0;
        }
    }

    @Override
    public float getMovementAxisY() {
        if (this.isControlPressed(Control.UP))  {
            return 1f;
        } else if (this.isControlPressed(Control.DOWN)) {
            return -1f;
        } else {
            return 0;
        }
    }

    @Override
    public float getAimAxisX() {
        return 0;
    }

    @Override
    public float getAimAxisY() {
        return 0;
    }

    @Override
    public boolean isControlPressed(Control control) {
        // Determine which control we are checking the state of.
        switch (control) {
            case PRIMARY_ACTION:
                return Gdx.input.isKeyPressed(Input.Keys.SPACE);
            case SECONDARY_ACTION:
                return Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
            case UP:
                return Gdx.input.isKeyPressed(Input.Keys.W);
            case DOWN:
                return Gdx.input.isKeyPressed(Input.Keys.S);
            case LEFT:
                return Gdx.input.isKeyPressed(Input.Keys.A);
            case RIGHT:
                return Gdx.input.isKeyPressed(Input.Keys.D);
        }
        return false;
    }

    @Override
    public boolean isControlJustPressed(Control control) {
        // Determine which control we are checking the state of.
        switch (control) {
            case PRIMARY_ACTION:
                return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
            case SECONDARY_ACTION:
                return Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT);
            case UP:
                return Gdx.input.isKeyJustPressed(Input.Keys.A);
            case DOWN:
                return Gdx.input.isKeyJustPressed(Input.Keys.D);
            case LEFT:
                return Gdx.input.isKeyJustPressed(Input.Keys.A);
            case RIGHT:
                return Gdx.input.isKeyJustPressed(Input.Keys.D);
        }
        return false;
    }

    @Override
    public void reset() {}
}
