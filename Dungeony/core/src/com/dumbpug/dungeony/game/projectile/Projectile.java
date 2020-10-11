package com.dumbpug.dungeony.game.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.character.GameCharacter;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.rendering.Animation;
import com.dumbpug.dungeony.game.rendering.Resources;
import com.dumbpug.dungeony.game.tile.Tile;

/**
 * Represents an active projectile.
 */
public abstract class Projectile extends Entity<SpriteBatch> {
    /**
     * The projectile state, always starting with the GENERATED state.
     */
    private ProjectileState state = ProjectileState.GENERATED;
    /**
     * The angle at which the projectile was fired.
     */
    private float angleOfFire;
    /**
     * The projectile animation.
     */
    private Animation<ProjectileState> animation;

    /**
     * Creates a new instance of the Projectile class.
     * @param origin The initial origin of the Projectile.
     * @param angleOfFire The angle at which the projectile was fired.
     */
    public Projectile(Position origin, float angleOfFire) {
        super(origin);
        this.angleOfFire = angleOfFire;
        this.animation = Resources.getProjectileAnimation(this.state, this.getProjectileType());
    }

    /**
     * Gets the projectile state.
     * @return The projectile state.
     */
    public ProjectileState getState() {
        return state;
    }

    /**
     * Sets the projectile state.
     * @param state The projectile state.
     */
    public void setState(ProjectileState state) {
        this.state = state;
    }

    @Override
    public float getLengthX() {
        return this.getSize();
    }

    @Override
    public float getLengthY() {
        return this.getSize() * 0.8f;
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
        // Everything should collide with an object by default.
        return EntityCollisionFlag.WALL | EntityCollisionFlag.CHARACTER | EntityCollisionFlag.PICKUP | EntityCollisionFlag.PROJECTILE | EntityCollisionFlag.OBJECT;
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
        // TODO Set animation based on state.
        // TODO Update position of the projectile if active.
        // TODO Check for an entity collisions and process any by calling onCharacterCollision/onGameObjectCollision.
        // TODO Check whether the projectile has a life span and if so set the state to EXPIRED if the span is up.
        // TODO Set state to ACTIVE if state is currently GENERATED.

        // If the state associated with the current projectile animation does not match the actual projectile state then get the one that does.
        if (this.animation.getState() != this.state) {
            this.animation = Resources.getProjectileAnimation(this.state, this.getProjectileType());
        }
    }

    /**
     * Handler for a character collision.
     * @param character The character that this projectile has collided with.
     */
    public abstract void onCharacterCollision(GameCharacter character);

    /**
     * Handler for a game object collision.
     * @param object The game object that this projectile has collided with.
     */
    public abstract void onGameObjectCollision(GameObject object);

    /**
     * Handler for a tile collision.
     * @param tile The tile that this projectile has collided with.
     */
    public abstract void onTileCollision(Tile tile);
}
