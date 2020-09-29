package com.dumbpug.dungeony.game.character.particles.walking;

import com.dumbpug.dungeony.engine.particles.Emitter;
import com.dumbpug.dungeony.engine.particles.IEmitterActivity;

/**
 * Emitter activity for a waling characters dust particle emitter.
 */
public class WalkingDustParticleEmitterActivity implements IEmitterActivity {
    private float secondsSinceLastParticle = 0.2f;

    @Override
    public void act(Emitter emitter, float delta) {
        if (secondsSinceLastParticle >= 0.2f) {
            emitter.spawnParticle();
            secondsSinceLastParticle = 0;
        } else {
            secondsSinceLastParticle += delta;
        }
    }
}
