package com.dumbpug.dungeony.game.projectile.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.projectile.Projectile;
import com.dumbpug.dungeony.game.projectile.ProjectileState;
import com.dumbpug.dungeony.game.tile.Tile;

/**
 * A standard bullet projectile.
 */
public class Bullet extends Projectile {
    /**
     * Creates a new instance of the Bullet class.
     * @param origin      The initial origin of the Projectile.
     * @param angleOfFire The angle at which the projectile was fired.
     */
    public Bullet(Position origin, float angleOfFire) {
        super(origin, angleOfFire);
    }

    @Override
    public float getSize() {
        return Constants.PROJECTILE_SIZE_MEDIUM;
    }

    @Override
    public float getMovementSpeed() {
        return Constants.PROJECTILE_DEFAULT_MOVEMENT_PS;
    }

    @Override
    public float getLifeSpan() {
        return -1;
    }

    @Override
    public void onCharacterCollision(GameCharacter character) {
        // TODO Apply damage to character health.
        this.setState(ProjectileState.COLLIDED);
    }

    @Override
    public void onGameObjectCollision(GameObject object) {
        // TODO Apply damage to object.
        this.setState(ProjectileState.COLLIDED);
    }

    @Override
    public void onTileCollision(Tile tile) {
        this.setState(ProjectileState.COLLIDED);
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) { }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public void onDestroy() { }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // TODO Render the bullet!
    }
}
