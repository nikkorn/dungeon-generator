package com.dumbpug.dungeony.game.weapon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.projectile.Projectile;
import java.util.ArrayList;

/**
 * A melee weapon that does not use ammunition.
 */
public abstract class MeleeWeapon extends Weapon {
    /**
     * Creates a new instance of the MeleeWeapon class.
     * @param quality The initial weapon quality.
     */
    public MeleeWeapon(WeaponQuality quality) {
        super(quality);
    }

    /**
     * Attempt to use the weapon.
     * @param environment The interactive environment.
     * @param isTriggerJustPressed Whether the button pressed to use weapon has only just been pressed.
     * @param delta The application delta time.
     */
    @Override
    public void use(InteractiveEnvironment environment, boolean isTriggerJustPressed, float delta) {
        // A melee weapon should only be used one per trigger press.
        if (!isTriggerJustPressed) {
            return;
        }

        // Get the current time.
        long currentTime = System.currentTimeMillis();

        // Have we waited long enough since we last used this weapon?
        if (System.currentTimeMillis() >= (lastUsed + this.getCoolDown())) {
            // Reset the last used time.
            this.lastUsed = currentTime;

            // We have successfully used our weapon!
            this.onUse(environment, delta);
        }
    }

    /**
     * Render the weapon using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // TODO Render melee weapon.
        // The IN_USE weapon state animation should be the animation of the melee weapon swiping.
    }
}
