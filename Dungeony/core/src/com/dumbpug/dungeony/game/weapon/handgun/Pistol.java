package com.dumbpug.dungeony.game.weapon.handgun;

import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.projectile.Projectile;
import com.dumbpug.dungeony.game.weapon.AmmunitionWeapon;
import com.dumbpug.dungeony.game.weapon.WeaponCategory;
import com.dumbpug.dungeony.game.weapon.WeaponQuality;
import com.dumbpug.dungeony.game.weapon.WeaponType;
import java.util.ArrayList;

/**
 * Represents a pistol weapon.
 */
public class Pistol extends AmmunitionWeapon {
    /**
     * Creates a new instance of the Pistol class.
     * @param quality The initial weapon quality.
     */
    public Pistol(WeaponQuality quality) {
        super(quality);
    }

    @Override
    public String getName() {
        return "Pistol";
    }

    @Override
    public double getSpread() {
        return 0;
    }

    @Override
    public long getCoolDown() {
        return 0;
    }

    @Override
    public boolean isAutomatic() {
        return false;
    }

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.PISTOL;
    }

    @Override
    public WeaponCategory getWeaponCategory() {
        return WeaponCategory.HANDGUN;
    }

    @Override
    public ArrayList<Projectile> onUse(Position origin, float angleOfUse) {
        return null;
    }
}
