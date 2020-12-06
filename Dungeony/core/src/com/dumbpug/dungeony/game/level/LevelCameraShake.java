package com.dumbpug.dungeony.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class LevelCameraShake {
    /**
     * The duration of the shake in millis.
     */
    private long duration;
    /**
     * The power of the shake.
     */
    private float power;
    /**
     * The RNG to use in generating camera offset values.
     */
    private Random random = new Random();
    /**
     * The shake x/y offset.
     */
    private float xOffset, yOffset;

    /**
     * Creates a new instance of the LevelCameraShake class.
     * @param duration The duration of the shake in millis.
     * @param power The power of the shake.
     */
    public LevelCameraShake(long duration, float power) {
        this.duration = duration;
        this.power    = power;
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    public void update(float delta, OrthographicCamera camera, float x, float y) {
        // If the shake is expired then just reset the camera position.
        if (this.isExpired()) {
            camera.position.lerp(new Vector3(x, y, 0), .02f);
            return;
        }

        // generate random new x and y values taking into account
        // how much force was passed in
        xOffset = (random.nextFloat() - 0.5f) * 2 * power;
        yOffset = (random.nextFloat() - 0.5f) * 2 * power;

        // Set the camera to this new x/y position
        camera.translate(-xOffset, -yOffset);

        this.duration -= delta;
    }
}
