package com.dumbpug.dungeony.game.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.rendering.Animation;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.tile.Tile;
import com.dumbpug.dungeony.game.tile.TileType;

import java.util.HashSet;

/**
 * Represents an active projectile.
 */
public abstract class Projectile extends Entity<SpriteBatch> {
    /**
     * The angle at which the projectile was fired.
     */
    private float angleOfFire;
    /**
     * The projectile animation.
     */
    private Animation animation;

    /**
     * Creates a new instance of the Projectile class.
     * @param origin The initial origin of the Projectile.
     * @param angleOfFire The angle at which the projectile was fired.
     */
    public Projectile(Position origin, float angleOfFire) {
        super(origin);
        this.angleOfFire = angleOfFire;
        this.animation   = Resources.getProjectileAnimation(this.getProjectileType());
    }

    @Override
    public float getLengthX() {
        return this.getSize();
    }

    @Override
    public float getLengthY() {
        return this.getSize();
    }

    @Override
    public float getLengthZ() {
        return this.getSize();
    }

    @Override
    public int getCollisionLayers() {
        return EntityCollisionFlag.PROJECTILE;
    }

    @Override
    public int getCollisionMask() {
        return EntityCollisionFlag.WALL | EntityCollisionFlag.CHARACTER | EntityCollisionFlag.OBJECT;
    }

    /**
     * Gets the size of the projectile.
     * @return The size of the projectile.
     */
    public abstract float getSize();

    /**
     * Gets the speed at which the projectile moves when active.
     * @return The speed at which the projectile moves when active.
     */
    public abstract float getMovementSpeed();

    /**
     * Gets the life span of the projectile in milliseconds, or -1 if there is no life span.
     * @return The life span of the projectile in milliseconds, or -1 if there is no life span.
     */
    public abstract float getLifeSpan();

    /**
     * Gets the type of the projectile.
     * @return The type of the projectile.
     */
    public abstract ProjectileType getProjectileType();

    @Override
    public void update(InteractiveEnvironment environment, float delta) {
        // TODO Check whether the projectile has a life span and if so set the state to EXPIRED if the span is up.

        // Update position of the projectile and get any entities that we collide with in the process.
        HashSet<Entity> collisions = environment.moveByAngle(this, this.angleOfFire, this.getMovementSpeed(), delta);

        // A flag that defines whether the projectile collided as part of this update and needs to be made inactive.
        boolean hasCollided = false;

        // Check for an entity collisions and process any by calling onCharacterCollision/onGameObjectCollision.
        for (Entity collision : collisions) {
            // Get the group of the colliding entity.
            String collidingEntityGroup = environment.getEntityGroup(collision);

            // TODO: If colliding with a player then do nothing atm, eventually this wont work as enemies can fire these.
            if (collidingEntityGroup == null || collidingEntityGroup.equalsIgnoreCase("player")) {
                continue;
            }

            if (collidingEntityGroup.equalsIgnoreCase("enemy")) {
                onCharacterCollision((Enemy)collision, environment, delta);
                hasCollided = true;
                continue;
            }

            if (collidingEntityGroup.equalsIgnoreCase("object")) {
                onGameObjectCollision((GameObject)collision, environment, delta);
                hasCollided = true;
                continue;
            }

            if (collidingEntityGroup.equalsIgnoreCase("tile")) {
                // Get the colliding tile.
                Tile tile = (Tile)collision;

                // We only care about tiles that the projectile can actually collide with, the walls.
                if (tile.getTileType() != TileType.WALL) {
                    continue;
                }

                onWallTileCollision(tile, environment, delta);
                hasCollided = true;
            }
        }

        // If the projectile has collided as part of this update it needs to become inactive.
        if (hasCollided) {
            this.onCollided(environment, delta);

            // The projectile is no longer active and should be removed from the environment.
            environment.removeEntity(this);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // Get the current animation frame for the animation.
        TextureRegion currentFrame = this.animation.getCurrentFrame();

        // Draw the current animation frame.
        spriteBatch.draw(currentFrame, this.getX(), this.getY(), this.getLengthX(), this.getLengthZ());
    }

    /**
     * Handler for a character collision.
     * @param character The character that this projectile has collided with.
     */
    public abstract void onCharacterCollision(GameCharacter character, InteractiveEnvironment environment, float delta);

    /**
     * Handler for a game object collision.
     * @param object The game object that this projectile has collided with.
     */
    public abstract void onGameObjectCollision(GameObject object, InteractiveEnvironment environment, float delta);

    /**
     * Handler for a wall tile collision.
     * @param tile The wall tile that this projectile has collided with.
     */
    public abstract void onWallTileCollision(Tile tile, InteractiveEnvironment environment, float delta);

    /**
     * Handler for a when a projectile has collided with any number of entities as part of an update and will be destroyed.
     */
    public abstract void onCollided(InteractiveEnvironment environment, float delta);
}
