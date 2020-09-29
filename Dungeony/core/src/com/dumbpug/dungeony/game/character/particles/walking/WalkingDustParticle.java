package com.dumbpug.dungeony.game.character.particles.walking;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.engine.particles.Particle;
import com.dumbpug.dungeony.game.rendering.GameObjectSprite;
import com.dumbpug.dungeony.game.rendering.Resources;
import java.util.Random;

public class WalkingDustParticle extends Particle<SpriteBatch> {
    private float angle;
    /**
     * Creates a new instance of the WalkingDustParticle class.
     * @param origin The initial origin of the particle.
     */
    public WalkingDustParticle(Position origin) {
        super(origin);
        this.angle = new Random().nextFloat() * 360;
    }

    @Override
    public int getCollisionLayers() {
        return 0;
    }

    @Override
    public int getCollisionMask() {
        return 0;
    }

    @Override
    public void onUpdate(InteractiveEnvironment environment, float delta) {
        environment.moveByAngle(this, this.angle, 0.5f, delta);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // Get the particle sprite.
        Sprite sprite = Resources.getSprite(GameObjectSprite.POT);

        // Set the width/height of the sprite to match the tile size.
        sprite.setSize(this.getLengthX(), this.getLengthY());

        // Set the x/y of the sprite to match the tile position.
        sprite.setPosition(this.getX(), this.getY());

        // Draw the sprite.
        sprite.draw(spriteBatch);
    }

    @Override
    public float getLengthX() {
        return Constants.LEVEL_TILE_SIZE * 0.2f;
    }

    @Override
    public float getLengthY() {
        return Constants.LEVEL_TILE_SIZE * 0.2f;
    }

    @Override
    public float getLengthZ() {
        return Constants.LEVEL_TILE_SIZE * 0.2f;
    }
}
