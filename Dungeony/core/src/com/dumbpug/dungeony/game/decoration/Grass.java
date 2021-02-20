package com.dumbpug.dungeony.game.decoration;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;

import java.util.Random;

public class Grass extends Decoration {
    private final static float SWAY_PS = 0.25f;
    private final static float SWAY_RANGE = 0.5f;
    private static Random rng;
    private static Sprite sprite;
    private float sway;

    static {
        rng    = new Random();
        sprite = new Sprite(new Texture("images/level/GRASS_LONG.png"));
        sprite.setOrigin(sprite.getRegionWidth() / 2f, sprite.getRegionHeight());
    }

    /**
     * Creates a new instance of the Prop class.
     * @param origin The initial origin of the entity.
     */
    public Grass(Position origin) {
        super(origin);
        this.sway = (this.rng.nextFloat() * (SWAY_RANGE * 2f)) - SWAY_RANGE;
    }

    /**
     * Gets the renderable layer to use in sorting.
     * The renderable layer will take precedence over the renderable index.
     * @return The renderable layer to use in sorting.
     */
    @Override
    public int getRenderLayer() {
        // Underlay tiles should be rendered early as they render below higher tiles.
        return 0;
    }

    @Override
    public float getLengthZ() {
        return 5f;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) {

    }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) {

    }

    @Override
    public void update(InteractiveEnvironment environment, float delta) {
        sway += delta * SWAY_PS;
        if (sway > SWAY_RANGE)
            sway = -SWAY_RANGE;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getLengthX(), this.getLengthZ());

        sprite.setPosition(this.getX(), this.getY());

        sprite.setRotation(10 * sway);

        // Draw the sprite.
        sprite.draw(spriteBatch);
    }

    @Override
    public float getLengthX() {
        return 19f;
    }

    @Override
    public float getLengthY() {
        return 0f;
    }
}
