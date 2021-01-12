package com.dumbpug.dungeony.game.character.particles.walking;

import com.dumbpug.dungeony.engine.particles.IParticleGenerator;
import com.dumbpug.dungeony.engine.particles.Particle;

/**
 * Generator of character walking dust particles.
 */
public class WalkingDustParticleGenerator implements IParticleGenerator {
    @Override
    public Particle generate() {
        return new WalkingDustParticle();
    }
}
