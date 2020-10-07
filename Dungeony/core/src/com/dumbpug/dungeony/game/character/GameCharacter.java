package com.dumbpug.dungeony.game.character;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.inventory.Inventory;
import com.dumbpug.dungeony.game.weapon.Weapon;

/**
 * Represents an in-game character.
 */
public abstract class GameCharacter extends Entity<SpriteBatch> {
    /**
     * The character health.
     */
    private Health health = new Health(Constants.CHARACTER_DEFAULT_HEALTH_SLOTS);
    /**
     * The character inventory.
     */
    private Inventory inventory = new Inventory(Constants.CHARACTER_INVENTORY_SLOTS);
    /**
     * The weapon equipped by the character, or null if no weapon is equipped.
     */
    private Weapon weapon = null;
    /**
     * The angle of the characters direction of view.
     */
    private float angleOfView;

    /**
     * Creates a new instance of the GameCharacter class.
     * @param origin The initial origin of the GameCharacter.
     */
    public GameCharacter(Position origin) {
        super(origin);
    }

    /**
     * Ges the character health.
     * @return The character health.
     */
    public Health getHealth() {
        return health;
    }

    /**
     * Gets the character inventory.
     * @return The character inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the weapon equipped by the character, or null if no weapon is equipped.
     * @return The weapon equipped by the character, or null if no weapon is equipped.
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Sets the weapon equipped by the character, or null if no weapon is equipped.
     * @param weapon The weapon equipped by the character, or null if no weapon is equipped.
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Gets the angle of the characters direction of view.
     * @return The angle of the characters direction of view.
     */
    public float getAngleOfView() {
        return angleOfView;
    }

    /**
     * Sets the angle of the characters direction of view.
     * @param angleOfView The angle of the characters direction of view.
     */
    public void setAngleOfView(float angleOfView) {
        this.angleOfView = angleOfView;
    }

    @Override
    public int getCollisionLayers() {
        return EntityCollisionFlag.CHARACTER;
    }

    @Override
    public int getCollisionMask() {
        // Everything should collide with a character by default.
        return EntityCollisionFlag.WALL | EntityCollisionFlag.CHARACTER | EntityCollisionFlag.PICKUP | EntityCollisionFlag.OBJECT;
    }

    /**
     * Gets the character movements speed per second.
     * @return The character movements speed per second.
     */
    public abstract float getMovementSpeed();
}
