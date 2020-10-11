package com.dumbpug.dungeony.game.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.inventory.Inventory;
import com.dumbpug.dungeony.game.rendering.Animation;
import com.dumbpug.dungeony.game.weapon.Weapon;
import java.util.HashMap;

/**
 * Represents an in-game character.
 */
public abstract class GameCharacter extends Entity<SpriteBatch> {
    /**
     * The current game character state.
     */
    private GameCharacterState state = GameCharacterState.IDLE_LEFT;
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
     * The game character state to animation map.
     */
    protected HashMap<GameCharacterState, Animation> animations = new HashMap<GameCharacterState, Animation>();
    /**
     * The game character shadow sprite.
     */
    protected Sprite shadowSprite;

    /**
     * Creates a new instance of the GameCharacter class.
     * @param origin The initial origin of the GameCharacter.
     */
    public GameCharacter(Position origin) {
        super(origin);
    }

    /**
     * Gets the current character state.
     * @return The current character state.
     */
    public GameCharacterState getState() {
        return state;
    }

    /**
     * Sets the current character state.
     * @param state The current character state.
     */
    public void setState(GameCharacterState state) {
        this.state = state;
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
        return EntityCollisionFlag.WALL | EntityCollisionFlag.CHARACTER | EntityCollisionFlag.OBJECT;
    }

    /**
     * Gets the character movements speed per second.
     * @return The character movements speed per second.
     */
    public abstract float getMovementSpeed();

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the relevant animation for the character based on their current state.
        Animation animation = animations.get(this.state);

        // Get the current animation frame for the animation.
        TextureRegion currentFrame = animation.getCurrentFrame(true);

        // Draw the character shadow (if they have one).
        if (this.shadowSprite != null) {
            shadowSprite.setSize(this.getLengthX(), this.getLengthZ());
            shadowSprite.setPosition(this.getX(), this.getY());
            shadowSprite.setScale(1.2f,  1.2f);
            shadowSprite.draw(batch);
        }

        // Render the current animation frame of the character.
        batch.draw(currentFrame, this.getX(), this.getY(), 0, 0, this.getLengthX(), this.getLengthZ(), 1.2f, 1.2f, 0);

        // Draw the weapon of the character if they have one.
        if (this.getWeapon() != null) {
            this.getWeapon().render(batch, this.getOrigin(), this.getAngleOfView());
        }
    }
}
