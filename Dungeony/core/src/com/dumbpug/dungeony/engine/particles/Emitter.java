package com.dumbpug.dungeony.engine.particles;

import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import java.util.ArrayList;

/**
 * A particle emitter entity.
 * @param <TRenderContext> The render context.
 */
public class Emitter<TRenderContext> extends Entity<TRenderContext> {
    /**
     * A reference to the environment that the emitter is currently in.
     */
    private InteractiveEnvironment environment;
    /**
     * The particle generator.
     */
    private IParticleGenerator particleGenerator;
    /**
     * The emitter activity.
     */
    private IEmitterActivity emitterActivity;
    /**
     * The list of emitter particles.
     */
    private ArrayList<Particle<TRenderContext>> particles = new ArrayList<Particle<TRenderContext>>();
    /**
     * Whether the emitter is active.
     */
    private boolean isActive = true;
    /**
     * The maximum number of particles permitted.
     */
    private int maxParticleCount = 100;

    /**
     * Create a new instance of the Emitter class.
     * @param position The emitter position.
     * @param emitterActivity The emitter activity.
     * @param particleGenerator The particle generator.
     */
    public Emitter(Position position, IEmitterActivity emitterActivity, IParticleGenerator particleGenerator) {
        super(position);
        this.emitterActivity   = emitterActivity;
        this.particleGenerator = particleGenerator;
    }

    /**
     * Sets the maximum number of particles permitted.
     * @param maxParticleCount The maximum number of particles permitted.
     */
    public void setMaxParticleCount(int maxParticleCount) {
        this.maxParticleCount = maxParticleCount;
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
    public void onEnvironmentEntry(InteractiveEnvironment environment) {
        // Keep a reference to the environment that the emitter is placed in.
        this.environment = environment;

    }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) {
        // Remove the reference to the environment that the emitter was placed in.
        this.environment = null;
    }

    @Override
    public void update(InteractiveEnvironment environment, float delta) {
        // If the emitter is active then invoke the emitter activity.
        if (this.isActive) {
            this.emitterActivity.act(this, delta);
        }
    }

    @Override
    public final void render(TRenderContext tRenderContext) {
        // We never need to render the emitter, we just care about the particles which will be drawn as part of the environment render.
    }

    @Override
    public float getLengthZ() {
        return 0;
    }

    @Override
    public float getLengthX() {
        return 0;
    }

    @Override
    public float getLengthY() {
        return 0;
    }

    /**
     * Attempts to spawn a new particle and ad it to the game environment.
     */
    public void spawnParticle() {
        if (this.environment == null) {
            throw new RuntimeException("cannot add emitter particle when emitter is not part of an environment.");
        }

        // We can only spawn a particle if we have no hit out limit.
        if(particles.size() < this.maxParticleCount) {
            // Generate a new particle.
            Particle particle = this.particleGenerator.generate(this.getX(), this.getY());

            // Add it to our collection of particles.
            particles.add(particle);

            // Add the particle to the environment.
            this.environment.addEntity(particle);
        }
    }

    /**
     * Returns whether there are still any active particles.
     * @return Whether there are still any active particles.
     */
    public boolean hasActiveParticles() {
        for(Particle particle : particles) {
            if(!particle.isExpired()) {
                // At least one particle is still active.
                return true;
            }
        }
        return false;
    }
}
