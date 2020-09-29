package com.dumbpug.dungeony.engine.particles;

import com.dumbpug.dungeony.engine.Position;

/**
 * A particle generator.
 */
public interface IParticleGenerator {
    /**
     * Generate a Particle.
     * @return A generated particle
     */
    Particle generate(float emitterPosX, float emitterPosY);
}
