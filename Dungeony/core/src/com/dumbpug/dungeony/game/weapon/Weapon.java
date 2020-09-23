package com.dumbpug.dungeony.game.weapon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.game.projectile.Projectile;
import java.util.ArrayList;

/**
 * A weapon equippable by a game character.
 */
public abstract class Weapon {
    /**
     * The weapon quality.
     */
    private WeaponQuality quality;
    /**
     * The last time the weapon was used.
     */
    protected long lastUsed = 0l;

    /**
     * Creates a new instance of the Weapon class.
     * @param quality The initial weapon quality.
     */
    public Weapon(WeaponQuality quality) {
        this.quality = quality;
    }

    /**
     * Gets the weapon quality.
     * @return The weapon quality.
     */
    public WeaponQuality getQuality() {
        return quality;
    }

    /**
     * Attempt to use the weapon.
     * @param origin The origin of the player using the weapon.
     * @param angleOfUse The angle at which the weapon was used.
     * @return Any projectiles generated
     */
    public abstract ArrayList<Projectile> use(Position origin, float angleOfUse, boolean isTriggerJustPressed);

    /**
     * Handles a use of the weapon.
     * @param origin The origin of the weapon.
     * @param angleOfUse The angle at which the weapon was used.
     * @return Any projectiles generated.
     */
    public abstract ArrayList<Projectile> onUse(Position origin, float angleOfUse);

    /**
     * Gets the name of the weapon.
     * @return The name of the weapon.
     */
    public abstract String getName();

    /**
     * Gets the cooldown time of the weapon.
     * @return The cooldown time of the weapon.
     */
    public abstract long getCoolDown();

    /**
     * Gets the type of the weapon.
     * @return The type of the weapon.
     */
    public abstract WeaponType getWeaponType();

    /**
     * Gets the category of the weapon.
     * @return The category of the weapon.
     */
    public abstract WeaponCategory getWeaponCategory();

    /**
     * Render the weapon using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     * @param origin The origin of the player using the weapon.
     * @param angle The angle at which the weapon is positioned.
     */
    public abstract void render(SpriteBatch batch, Position origin, float angle);
}
