package com.dumbpug.dungeony.game.projectile.projectiles;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.lights.SmallSpotLight;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.projectile.Projectile;
import com.dumbpug.dungeony.game.projectile.ProjectileType;
import com.dumbpug.dungeony.game.tile.Tile;

/**
 * A standard bullet projectile.
 */
public class Bullet extends Projectile {
    /**
     * The bullet spotlight.
     */
    private SmallSpotLight light;
    
    /**
     * Creates a new instance of the Bullet class.
     * @param origin      The initial origin of the Projectile.
     * @param angleOfFire The angle at which the projectile was fired.
     */
    public Bullet(Position origin, float angleOfFire) {
        super(origin, angleOfFire);
        light = new SmallSpotLight(this, 1f, 1f, 1f);
    }

    @Override
    public float getSize() {
        return Constants.PROJECTILE_SIZE_MEDIUM;
    }

    @Override
    public float getMovementSpeed() {
        return Constants.PROJECTILE_FAST_MOVEMENT_PS;
    }

    @Override
    public float getLifeSpan() {
        return -1;
    }

    @Override
    public ProjectileType getProjectileType() {
        return ProjectileType.BULLET;
    }

    @Override
    public void onCharacterCollision(GameCharacter character, InteractiveEnvironment environment, float delta) {
    }

    @Override
    public void onGameObjectCollision(GameObject object, InteractiveEnvironment environment, float delta) {
    }

    @Override
    public void onWallTileCollision(Tile tile, InteractiveEnvironment environment, float delta) {
    }

    @Override
    public void onCollided(InteractiveEnvironment environment, float delta) {
        // TODO Show projectile death animation.
    }


    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) {
        environment.addLight(light);
    }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) {
        environment.removeLight(this.light);
    }
}
