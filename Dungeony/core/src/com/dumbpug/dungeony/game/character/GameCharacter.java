package com.dumbpug.dungeony.game.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
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
     * The facing direction of the character.
     */
    private FacingDirection facingDirection = FacingDirection.RIGHT;
    /**
     * The angle of view of the character.
     */
    private Float angleOfView = null;
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
    public Float getAngleOfView() {
        return angleOfView;
    }

    /**
     * Sets the angle of the characters direction of view.
     * @param angleOfView The angle of the characters direction of view.
     */
    public void setAngleOfView(Float angleOfView) {
        this.angleOfView = angleOfView;
    }

    public FacingDirection getFacingDirection() {
        return facingDirection;
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
            shadowSprite.setPosition(this.getX(), this.getY() - (this.getLengthZ() * 0.1f));
            shadowSprite.draw(batch);
        }

        // Draw the weapon of the character if they have one, but do this behind the character if they are facing right.
        if (this.getWeapon() != null && this.facingDirection == FacingDirection.RIGHT) {
            this.getWeapon().render(batch);
        }

        // Render the current animation frame of the character.
        batch.draw(currentFrame, this.getX(), this.getY(), 0, 0, this.getLengthX(), this.getLengthZ(), 1.0f, 1.0f, 0);

        // Draw the weapon of the character if they have one, but do this in front of the character if they are facing left.
        if (this.getWeapon() != null && this.facingDirection == FacingDirection.LEFT) {
            this.getWeapon().render(batch);
        }
    }

    /**
     * Make the character move, updating the state, facing direction and equipped weapon position of the character.
     * @param environment
     * @param movementAxisX
     * @param movementAxisY
     * @param delta
     */
    protected void move(InteractiveEnvironment environment, float movementAxisX, float movementAxisY, float delta) {
        // Is the player idle and not moving in any direction?
        if (movementAxisX == 0f && movementAxisY == 0f) {
            // The facing direction of the character will be determined by their current angle of view if one is set.
            if (this.angleOfView != null) {
                this.facingDirection = FacingDirection.fromAngle(this.angleOfView);
            }

            // The player should be idle and facing whatever direction they already have been.
            this.setState(this.facingDirection == FacingDirection.LEFT ? GameCharacterState.IDLE_LEFT: GameCharacterState.IDLE_RIGHT);
        } else {
            // The facing direction of the character will be determined by their current angle of view if one is
            // set, otherwise it will be determined by whatever direction the character is currently moving in.
            if (this.angleOfView != null) {
                this.facingDirection = FacingDirection.fromAngle(this.angleOfView);
            } else {
                // We are running because we are moving on either axis, but the X axis movement determines which way we face.
                if (movementAxisX < 0) {
                    this.facingDirection = FacingDirection.LEFT;
                } else if (movementAxisX > 0) {
                    this.facingDirection = FacingDirection.RIGHT;
                }
            }

            // The player should be running and facing whatever direction they already have been.
            this.setState(this.facingDirection == FacingDirection.LEFT ? GameCharacterState.RUNNING_LEFT: GameCharacterState.RUNNING_RIGHT);
        }

        // Any entity movement has to be taken care of by the level grid which handles all entity collisions.
        environment.move(this, movementAxisX, movementAxisY, delta);

        // Update the position and angle of the equipped weapon.
        if (this.getWeapon() != null) {
            // Where the weapon is positioned in the world will depend on the position of the character holding it.
            float weaponPositionY = this.getOrigin().getY() - (this.getLengthY() * 0.1f);
            float weaponPositionX = this.getOrigin().getX() + (this.getLengthX() * 0.2f);
            this.getWeapon().getPosition().set(weaponPositionX, weaponPositionY);
            this.getWeapon().setAngleOfAim(this.angleOfView == null ? this.facingDirection.getAngle() : this.angleOfView);
        }
    }
}
