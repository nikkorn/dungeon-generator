package com.dumbpug.dungeony.engine.particles;

import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import java.util.ArrayList;
import java.util.Iterator;

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
     * The list of all emitter particles.
     */
    private ArrayList<Particle<TRenderContext>> particles = new ArrayList<Particle<TRenderContext>>();
    /**
     * The list of inactive emitter particles.
     */
    private ArrayList<Particle<TRenderContext>> inactiveParticles = new ArrayList<Particle<TRenderContext>>();
    /**
     * Whether the emitter is active.
     */
    private boolean isActive = true;
    /**
     * The maximum number of particles permitted.
     */
    private int maxParticleCount = 300;
    /**
     * Whether to destroy inactive particles or reuse them.
     */
    private boolean destroyInactiveParticles = true;

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
     * Sets the maximum number of active particles permitted.
     * @param maxParticleCount The maximum number of active particles permitted.
     */
    public void setMaxParticleCount(int maxParticleCount) {
        this.maxParticleCount = maxParticleCount;
    }

    /**
     * Sets whether to destroy inactive particles or reuse them.
     * @param destroyInactiveParticles Whether to destroy inactive particles or reuse them.
     */
    public void setDestroyInactiveParticles(boolean destroyInactiveParticles) {
        this.destroyInactiveParticles = destroyInactiveParticles;
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

        System.out.println("PART: " + this.particles.size());

        Iterator<Particle<TRenderContext>> iterator = this.particles.iterator();

        // Iterate over and remove any inactive particles from the environment and this collection if we are not reusing inactive particles.
        while (iterator.hasNext()) {
            Particle particle = iterator.next();

            if (particle.getState() == ParticleState.ACTIVE) {
                continue;
            }

            if (this.destroyInactiveParticles) {
                environment.removeEntity(particle);
                iterator.remove();
            } else {
                this.inactiveParticles.add(particle);
            }
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

        // We should not be spawning any new particles if we already have the max number of active particles allowed.
        if ((this.particles.size() - this.inactiveParticles.size()) >= this.maxParticleCount) {
            return;
        }

        // Check whether we have any inactive particles we can reuse rather than having to create a new one.
        if (!this.inactiveParticles.isEmpty()) {
            // Grab an existing inactive particle.
            Particle nextInactiveParticle = this.inactiveParticles.get(0);

            // Remove the particle from the set of inactive particles
            this.inactiveParticles.remove(nextInactiveParticle);

            nextInactiveParticle.setState(ParticleState.ACTIVE);

            // TODO: Find a way to reset particle life nicely.
            nextInactiveParticle.setLife(1f);

            nextInactiveParticle.onActivate(this.getX(), this.getY());
        } else {
            // Generate a brand new particle.
            Particle particle = this.particleGenerator.generate();

            // Add it to our collection of particles.
            particles.add(particle);

            // Add the particle to the environment.
            this.environment.addEntity(particle);

            particle.onActivate(this.getX(), this.getY());
        }
    }
}
