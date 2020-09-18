package com.dumbpug.dungeony.game.weapon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.game.Position;
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
     * @param origin The origin of the player using the weapon.
     * @param angleOfUse The angle at which the weapon was used.
     * @return Any projectiles generated.
     */
    @Override
    public ArrayList<Projectile> use(Position origin, float angleOfUse, boolean isTriggerJustPressed) {
        ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

        // A melee weapon should only be used one per trigger press.
        if (!isTriggerJustPressed) {
            return projectiles;
        }

        // Get the current time.
        long currentTime = System.currentTimeMillis();

        // Have we waited long enough since we last used this weapon?
        if (System.currentTimeMillis() >= (lastUsed + this.getCoolDown())) {
            // Reset the last used time.
            this.lastUsed = currentTime;

            // We have successfully used our weapon!
            return this.onUse(origin, angleOfUse);
        }

        return projectiles;
    }

    /**
     * Render the weapon using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     * @param origin The origin of the player using the weapon.
     * @param angle The angle at which the weapon is positioned.
     */
    @Override
    public void render(SpriteBatch batch, Position origin, float angle) {
        // TODO Render melee weapon.
        // The IN_USE weapon state animation should be the animation of the melee weapon swiping.
    }
}
