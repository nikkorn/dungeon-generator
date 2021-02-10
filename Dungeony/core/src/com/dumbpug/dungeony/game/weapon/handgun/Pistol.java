package com.dumbpug.dungeony.game.weapon.handgun;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.projectile.projectiles.Bullet;
import com.dumbpug.dungeony.game.weapon.AmmunitionWeapon;
import com.dumbpug.dungeony.game.weapon.WeaponCategory;
import com.dumbpug.dungeony.game.weapon.WeaponQuality;
import com.dumbpug.dungeony.game.weapon.WeaponType;

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
        this.setAmmo(100);
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
    public float getLength() {
        return 9f;
    }

    @Override
    public long getCoolDown() {
        return 100l;
    }

    @Override
    public long getRange() {
        return Constants.WEAPON_RANGE_PISTOL;
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
    public void onUse(InteractiveEnvironment environment, Entity user, float delta) {
        // Create a new bullet projectile and add it to the environment.
        environment.addEntity(new Bullet(new Position(this.getPosition()), this.getAngleOfAim(), user));

        // Shake camera on bullet fire!
        environment.shakeCamera(50, 0.6f);
    }
}
