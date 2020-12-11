package com.dumbpug.dungeony.game.weapon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.projectile.Projectile;
import com.dumbpug.dungeony.game.rendering.Animation;
import java.util.ArrayList;

/**
 * A weapon that uses ammunition.
 */
public abstract class AmmunitionWeapon extends Weapon {
    /**
     * The ammo level of the weapon.
     */
    private int ammo;

    /**
     * Creates a new instance of the AmmunitionWeapon class.
     * @param quality The initial weapon quality.
     */
    public AmmunitionWeapon(WeaponQuality quality) {
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

        // If this weapon is not an automatic then it should only be used once per trigger press.
        if ((!this.isAutomatic()) && (!isTriggerJustPressed)) {
            return projectiles;
        }

        // Get the current time.
        long currentTime = System.currentTimeMillis();

        // Have we waited long enough since we last used this weapon?
        if (System.currentTimeMillis() >= (lastUsed + this.getCoolDown())) {
            // Do we have enough ammo to fire or do we have infinite ammo?
            if (ammo > 0) {
                // Use a unit of ammo.
                this.ammo--;

                // Reset the last fired time.
                this.lastUsed = currentTime;

                // We have successfully fired our weapon!
                return this.onUse(origin, angleOfUse);
            } else {
                // Reset the last fired time.
                this.lastUsed = currentTime;
            }
        }

        return projectiles;
    }

    /**
     * Reload the weapon.
     */
    public void reload() {

    }

    /**
     * Render the weapon using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the relevant animation for the weapon based on their current state.
        Animation animation = this.animations.get(this.getState());

        // Get the current animation frame for the animation.
        TextureRegion currentFrame = animation.getCurrentFrame(true);

        // Check whether we need to flip the weapon sprite based on its angle of aim.
        if (this.getAngleOfAim() < 0 || this.getAngleOfAim() > 180) {
            if (!currentFrame.isFlipY()) {
                currentFrame.flip(false, true);
            }
        } else {
            if (currentFrame.isFlipY()) {
               currentFrame.flip(false, true);
            }
        }

        // Draw the current animation frame.
        batch.draw(currentFrame, this.getPosition().getX(), this.getPosition().getY(), 0f, currentFrame.getRegionHeight() / 2f,
                currentFrame.getRegionWidth(), currentFrame.getRegionHeight(), 1f, 1f, -(this.getAngleOfAim() - 90f));
    }

    /**
     * Gets whether the weapon is automatic fire.
     * @return Whether the weapon is automatic fire.
     */
    public abstract boolean isAutomatic();

    /**
     * Gets the effective spread of the weapon.
     * @return The effective spread of the weapon.
     */
    public abstract double getSpread();
}
