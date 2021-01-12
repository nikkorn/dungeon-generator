package com.dumbpug.dungeony.engine.particles;

/**
 * An interface which represents an activity to be carried out by the emitter.
 */
public interface IEmitterActivity {
    /**
     * Do the activity
     * @param emitter The emitter.
     * @param delta the game delta.
     */
    void act(Emitter emitter, float delta);
}
