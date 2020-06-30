package com.dumbpug.dungeony.game.weapon;

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
     * Gets the name of the weapon.
     * @return The name of the weapon.
     */
    public abstract String getName();

    /**
     * Gets the effective range of the weapon.
     * @return The effective range of the weapon.
     */
    public abstract double getRange();

    /**
     * Gets the effective spread of the weapon.
     * @return The effective spread of the weapon.
     */
    public abstract double getSpread();

    /**
     * Gets the cooldown time of the weapon.
     * @return The cooldown time of the weapon.
     */
    public abstract long getCoolDown();

    /**
     * Gets whether the weapon is automatic fire.
     * @return Whether the weapon is automatic fire.
     */
    public abstract boolean isAutomatic();

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
     * Attempt to use the weapon.
     * @return Any projectiles generated
     */
    public abstract ArrayList<Projectile> use();

    /**
     * Reload the weapon.
     */
    public abstract void reload();
}
