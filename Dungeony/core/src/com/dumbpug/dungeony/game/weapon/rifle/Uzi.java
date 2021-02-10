package com.dumbpug.dungeony.game.weapon.rifle;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.engine.utilities.GameMath;
import com.dumbpug.dungeony.game.projectile.projectiles.Bullet;
import com.dumbpug.dungeony.game.weapon.AmmunitionWeapon;
import com.dumbpug.dungeony.game.weapon.WeaponCategory;
import com.dumbpug.dungeony.game.weapon.WeaponQuality;
import com.dumbpug.dungeony.game.weapon.WeaponType;

/**
 * Represents an automatic uzi weapon.
 */
public class Uzi extends AmmunitionWeapon {
    /**
     * Creates a new instance of the Uzi class.
     * @param quality The initial weapon quality.
     */
    public Uzi(WeaponQuality quality) {
        super(quality);
        this.setAmmo(200);
    }

    @Override
    public String getName() {
        return "Uzi";
    }

    @Override
    public double getSpread() {
        return 0;
    }

    @Override
    public float getLength() {
        return 12f;
    }

    @Override
    public long getCoolDown() {
        return 120l;
    }

    @Override
    public long getRange() {
        return Constants.WEAPON_RANGE_SMALL_RIFLE;
    }

    @Override
    public boolean isAutomatic() {
        return true;
    }

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.UZI;
    }

    @Override
    public WeaponCategory getWeaponCategory() {
        return WeaponCategory.AUTO_RIFLE;
    }

    @Override
    public void onUse(InteractiveEnvironment environment, Entity user, float delta) {
        // Get the position at which projectiles will be generated.
        Position positionOfFire = GameMath.getPositionForAngle(this.getPosition().getX(), this.getPosition().getY(), this.getAngleOfAim(), this.getLength());

        // Create a new bullet projectile and add it to the environment.
        environment.addEntity(new Bullet(positionOfFire, this.getAngleOfAim(), user));

        // Shake camera on bullet fire!
        environment.shakeCamera(50, 0.6f);
    }
}
