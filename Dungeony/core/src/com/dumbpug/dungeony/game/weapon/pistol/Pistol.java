package com.dumbpug.dungeony.game.weapon.pistol;

import com.dumbpug.dungeony.game.weapon.Weapon;
import com.dumbpug.dungeony.game.weapon.WeaponCategory;
import com.dumbpug.dungeony.game.weapon.WeaponQuality;

/**
 * Represents a pistol weapon.
 */
public abstract class Pistol extends Weapon {
    /**
     * Creates a new instance of the Pistol class.
     * @param quality The initial weapon quality.
     */
    public Pistol(WeaponQuality quality) {
        super(quality);
    }

    @Override
    public boolean isAutomatic() {
        return false;
    }

    @Override
    public WeaponCategory getWeaponCategory() {
        return WeaponCategory.PISTOL;
    }
}
