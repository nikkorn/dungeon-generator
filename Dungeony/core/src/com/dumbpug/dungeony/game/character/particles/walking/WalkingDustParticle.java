package com.dumbpug.dungeony.game.character.particles.walking;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.particles.Particle;
import com.dumbpug.dungeony.game.projectile.ProjectileType;
import com.dumbpug.dungeony.game.rendering.Animation;
import com.dumbpug.dungeony.game.rendering.Resources;
import java.util.Random;

public class WalkingDustParticle extends Particle<SpriteBatch> {
    private float angle;

    // The particle animation.
    private Animation animation;

    /**
     * Creates a new instance of the WalkingDustParticle class.
     */
    public WalkingDustParticle() {
        this.animation = Resources.getProjectileAnimation(ProjectileType.BULLET);
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
    public void onActivate(float emitterPosX, float emitterPosY) {
        // Set the initial particle position.
        this.setX(emitterPosX);
        this.setY(emitterPosY);
        // Pick an angle to move the particle.
        this.angle = new Random().nextFloat() * 360;
    }

    @Override
    public void onUpdate(InteractiveEnvironment environment, float delta) {
        environment.moveByAngle(this, this.angle, 100f, delta);
    }

    @Override
    public void onRender(SpriteBatch spriteBatch) {
        // Get the current animation frame for the animation.
        TextureRegion currentFrame = this.animation.getCurrentFrame();

        // Draw the current animation frame.
        spriteBatch.draw(currentFrame, this.getX(), this.getY(), this.getLengthX(), this.getLengthZ());
    }

    @Override
    public float getLengthX() {
        return Constants.LEVEL_TILE_SIZE * 0.2f;
    }

    @Override
    public float getLengthY() {
        return Constants.LEVEL_TILE_SIZE * 0.25f;
    }

    @Override
    public float getLengthZ() {
        return Constants.LEVEL_TILE_SIZE * 0.25f;
    }
}
